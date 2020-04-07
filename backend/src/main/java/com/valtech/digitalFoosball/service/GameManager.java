package com.valtech.digitalFoosball.service;

import com.valtech.digitalFoosball.exceptions.NameDuplicateException;
import com.valtech.digitalFoosball.model.input.InitDataModel;
import com.valtech.digitalFoosball.model.internal.PlayerDataModel;
import com.valtech.digitalFoosball.model.internal.TeamDataModel;
import com.valtech.digitalFoosball.model.output.GameDataModel;
import com.valtech.digitalFoosball.model.output.TeamOutput;
import com.valtech.digitalFoosball.storage.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

@Service
public class GameManager {
    private List<TeamDataModel> teams;
    private TeamService teamService;
    private Stack<TeamDataModel> historyOfGoals;
    private Stack<TeamDataModel> historyOfUndo;
    private Converter converter;
    private RoundWinApprover roundWinApprover;

    @Autowired
    public GameManager(TeamService teamService) {
        this.teamService = teamService;
        historyOfGoals = new Stack<>();
        converter = new Converter();
        historyOfUndo = new Stack<>();
        roundWinApprover = new RoundWinApprover();
    }

    public void initGame(InitDataModel initDataModel) {
        historyOfGoals = new Stack<>();
        checkForDuplicateNames(initDataModel);

        teams = initDataModel.getTeams();

        for (TeamDataModel team : teams) {
            teamService.setUp(team);
        }

        roundWinApprover.init(teams);
    }

    private void checkForDuplicateNames(InitDataModel initDataModel) {
        List<String> playerNames = new ArrayList<>();
        List<String> teamNames = new ArrayList<>();

        for (TeamDataModel team : initDataModel.getTeams()) {

            if (teamNames.contains(team.getName())) {
                throw new NameDuplicateException(team.getName());
            }

            teamNames.add(team.getName());

            for (PlayerDataModel player : team.getPlayers()) {

                if (playerNames.contains(player.getName())) {
                    throw new NameDuplicateException(player.getName());
                }

                playerNames.add(player.getName());
            }
        }
    }

    public void countGoalFor(int team) {
        TeamDataModel teamDataModel = teams.get(team - 1);

        if (!roundIsOver()) {
            teamDataModel.countGoal();
            historyOfGoals.push(teamDataModel);

            if (roundIsOver()) {
                teamDataModel.increaseWonRounds();
            }
        }
    }

    private boolean roundIsOver() {
        return roundWinApprover.getSetWinner() != 0;
    }

    public void undoGoal() {
        if (!historyOfGoals.empty()) {
            TeamDataModel lastScoringTeam = historyOfGoals.pop();

            if (roundIsOver()) {
                lastScoringTeam.decreaseWonRounds();
            }

            lastScoringTeam.decreaseScore();
            historyOfUndo.push(lastScoringTeam);
        }
    }

    public void redoGoal() {
        if (!historyOfUndo.empty()) {
            TeamDataModel teamDataModel = historyOfUndo.pop();

            teamDataModel.countGoal();
            historyOfGoals.push(teamDataModel);

            if (roundIsOver()) {
                teamDataModel.increaseWonRounds();
            }
        }
    }

    public void resetMatch() {
        for (TeamDataModel team : teams) {
            team.resetValues();
        }

        resetHistories();
    }

    public void changeover() {
        for (TeamDataModel team : teams) {
            team.resetScore();
        }

        resetHistories();
    }

    public GameDataModel getGameData() {
        if (teams == null) {
            return null;
        }

        List<TeamOutput> convertedTeams = converter.convertAllToTeamOutput(teams);

        GameDataModel currentGameData = new GameDataModel(convertedTeams);
        currentGameData.setRoundWinner(roundWinApprover.getSetWinner());
        currentGameData.setMatchWinner(getMatchWinner());

        return currentGameData;
    }

    public List<TeamDataModel> getTeams() {
        return teams;
    }

    public List<TeamOutput> getAllTeams() {
        List<TeamDataModel> teamDataModels = teamService.getAll();

        if (teamDataModels.isEmpty()) {
            return new ArrayList<>();
        }

        return converter.convertAllToTeamOutput(teamDataModels);
    }

    public int getMatchWinner() {
        int matchWinner = 0;

        for (TeamDataModel team : teams) {
            if (team.getWonRounds() >= 2) {
                matchWinner = teams.indexOf(team) + 1;
            }
        }

        return matchWinner;
    }

    private void resetHistories() {
        historyOfGoals = new Stack<>();
        historyOfUndo = new Stack<>();
    }

    public void initAdHocGame() {
        historyOfGoals = new Stack<>();
        teams = new ArrayList<>();

        TeamDataModel teamDataModelOne = new TeamDataModel();
        TeamDataModel teamDataModelTwo = new TeamDataModel();

        teamDataModelOne.setName("Orange");
        teamDataModelOne.setNameOfPlayerOne("Goalie");
        teamDataModelOne.setNameOfPlayerTwo("Striker");

        teamDataModelTwo.setName("Green");
        teamDataModelTwo.setNameOfPlayerOne("Goalie");
        teamDataModelTwo.setNameOfPlayerTwo("Striker");

        teams.add(teamDataModelOne);
        teams.add(teamDataModelTwo);

        roundWinApprover.init(teams);
    }

    public void setTeams(List<TeamDataModel> teams) {
        this.teams = teams;
    }
}