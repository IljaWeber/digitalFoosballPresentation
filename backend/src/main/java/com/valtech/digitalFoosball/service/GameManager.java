package com.valtech.digitalFoosball.service;

import com.valtech.digitalFoosball.constants.Team;
import com.valtech.digitalFoosball.exceptions.NameDuplicateException;
import com.valtech.digitalFoosball.model.input.InitDataModel;
import com.valtech.digitalFoosball.model.internal.PlayerDataModel;
import com.valtech.digitalFoosball.model.internal.TeamDataModel;
import com.valtech.digitalFoosball.model.output.GameDataModel;
import com.valtech.digitalFoosball.model.output.TeamOutput;
import com.valtech.digitalFoosball.service.verifier.WinConditionVerifier;
import com.valtech.digitalFoosball.storage.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameManager {
    private Map<Team, TeamDataModel> teams;
    private TeamService teamService;
    private Stack<Team> historyOfGoals;
    private Stack<Team> historyOfUndo;
    private Converter converter;
    private WinConditionVerifier winConditionVerifier;
    private Team setWinner;

    @Autowired
    public GameManager(TeamService teamService) {
        this.teamService = teamService;
        historyOfGoals = new Stack<>();
        converter = new Converter();
        historyOfUndo = new Stack<>();
        winConditionVerifier = new WinConditionVerifier();
        teams = new HashMap<>();
    }

    public void initGame(InitDataModel initDataModel) {
        historyOfGoals = new Stack<>();
        checkForDuplicateNames(initDataModel);

        List<TeamDataModel> teamsList = initDataModel.getTeams();
        teams.put(Team.ONE, teamsList.get(0));
        teams.put(Team.TWO, teamsList.get(1));

        for (TeamDataModel team : teamsList) {
            teamService.setUp(team);
        }

        setWinner = Team.NO_TEAM;
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

    public void countGoalFor(Team team) {
        TeamDataModel teamDataModel = teams.get(team);

        if (setWinner == Team.NO_TEAM) {
            teamDataModel.countGoal();
            historyOfGoals.push(team);

            if (winConditionVerifier.teamWon(teams, team)) {
                teamDataModel.increaseWonSets();
                setWinner = team;
            }
        }
    }

    public void undoGoal() {
        if (!historyOfGoals.empty()) {
            Team team = historyOfGoals.pop();
            TeamDataModel lastScoringTeam = teams.get(team);

            if (setWinner != Team.NO_TEAM) {
                lastScoringTeam.decreaseWonSets();
                setWinner = Team.NO_TEAM;
            }

            lastScoringTeam.decreaseScore();
            historyOfUndo.push(team);
        }
    }

    public void redoGoal() {
        if (!historyOfUndo.empty()) {
            Team team = historyOfUndo.pop();
            TeamDataModel teamDataModel = teams.get(team);

            teamDataModel.countGoal();
            historyOfGoals.push(team);

            if (winConditionVerifier.teamWon(teams, team)) {
                teamDataModel.increaseWonSets();
                setWinner = team;
            }
        }
    }

    public void resetMatch() {
        teams.forEach((k, v) -> v.resetValues());
        setWinner = Team.NO_TEAM;

        resetHistories();
    }

    public void changeover() {
        teams.forEach((k, v) -> v.resetScore());
        setWinner = Team.NO_TEAM;

        resetHistories();
    }

    public GameDataModel getGameData() {
        if (teams.isEmpty()) {
            return null;
        }

        List<TeamOutput> convertedTeams = converter.convertMapToTeamOutputs(teams);

        GameDataModel currentGameData = new GameDataModel(convertedTeams);
        int anInt = setWinner.getInt();
        currentGameData.setWinnerOfSet(anInt);
        currentGameData.setMatchWinner(getMatchWinner());

        return currentGameData;
    }

    public Map<Team, TeamDataModel> getTeams() {
        return teams;
    }

    public List<TeamOutput> getAllTeams() {
        List<TeamDataModel> teamDataModels = teamService.getAll();

        if (teamDataModels.isEmpty()) {
            return new ArrayList<>();
        }

        return converter.convertListToTeamOutputs(teamDataModels);
    }

    public int getMatchWinner() {
        int matchWinner = 0;

        ArrayList<TeamDataModel> teamsList = new ArrayList<>();
        teamsList.add(teams.get(Team.ONE));
        teamsList.add(teams.get(Team.TWO));

        for (TeamDataModel team : teamsList) {
            if (team.getWonSets() >= 2) {
                return teamsList.indexOf(team) + 1;
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

        TeamDataModel teamDataModelOne = new TeamDataModel();
        TeamDataModel teamDataModelTwo = new TeamDataModel();

        teamDataModelOne.setName("Orange");
        teamDataModelOne.setNameOfPlayerOne("Goalie");
        teamDataModelOne.setNameOfPlayerTwo("Striker");

        teamDataModelTwo.setName("Green");
        teamDataModelTwo.setNameOfPlayerOne("Goalie");
        teamDataModelTwo.setNameOfPlayerTwo("Striker");

        teams.put(Team.ONE, teamDataModelOne);
        teams.put(Team.ONE, teamDataModelTwo);

        setWinner = Team.NO_TEAM;
    }

    public Stack<Team> getHistoryOfGoals() {
        return historyOfGoals;
    }

    public Stack<Team> getHistoryOfUndo() {
        return historyOfUndo;
    }
}
