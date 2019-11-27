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
    private Stack<Integer> historyOfGoals;
    private Stack<Integer> historyOfUndo;
    private Converter converter;

    @Autowired
    public GameManager(TeamService teamService) {
        this.teamService = teamService;
        historyOfGoals = new Stack<>();
        converter = new Converter();
        historyOfUndo = new Stack<>();
    }

    public void initGame(InitDataModel initDataModel) {
        historyOfGoals = new Stack<>();
        checkForDuplicateNames(initDataModel);

        teams = initDataModel.getTeams();

        for (TeamDataModel team : teams) {
            teamService.setUp(team);
        }
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

    public void raiseScore(int teamNo) {
        TeamDataModel teamDataModel = teams.get(teamNo - 1);

        if (!roundIsOver()) {
            teamDataModel.increaseScore();
            historyOfGoals.push(teamNo - 1);
        }

        if (roundIsOver()) {
            teamDataModel.increaseWonRounds();
        }
    }

    private boolean roundIsOver() {
        return getRoundWinner() != 0;
    }

    public void undoLastGoal() {
        if (!historyOfGoals.empty()) {
            Integer indexOfLastScoringTeam = historyOfGoals.pop();
            TeamDataModel lastScoringTeam = teams.get(indexOfLastScoringTeam);

            if (roundIsOver()) {
                lastScoringTeam.decreaseWonRounds();
            }

            lastScoringTeam.decreaseScore();
            historyOfUndo.push(indexOfLastScoringTeam);
        }
    }

    public void redoLastGoal() {
        if (!historyOfUndo.empty()) {
            Integer lastUndo = historyOfUndo.pop();
            TeamDataModel teamDataModel = teams.get(lastUndo);

            teamDataModel.increaseScore();
            historyOfGoals.push(lastUndo);

            if (roundIsOver()) {
                teamDataModel.increaseWonRounds();
            }
        }
    }

    public void resetGameValues() {
        for (TeamDataModel team : teams) {
            team.resetValues();
        }

        resetStacks();
    }

    private void resetStacks() {
        historyOfGoals = new Stack<>();
        historyOfUndo = new Stack<>();
    }

    public void newRound() {
        for (TeamDataModel team : teams) {
            team.resetScore();
        }

        resetStacks();
    }

    public GameDataModel getGameData() {
        if (teams == null) {
            return null;
        }

        List<TeamOutput> convertedTeams = converter.convertAllToTeamOutput(teams);

        GameDataModel currentGameData = new GameDataModel(convertedTeams);
        currentGameData.setRoundWinner(getRoundWinner());
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
        for (int teamNo = 0; teamNo < teams.size(); teamNo++) {
            TeamDataModel team = teams.get(teamNo);

            if (team.getWonRounds() >= 2) {
                return teamNo + 1;
            }
        }

        return 0;
    }

    public int getRoundWinner() {
        for (int teamNo = 0; teamNo < teams.size(); teamNo++) {
            if (scoreGreaterOrEqualSixOfTeam(teamNo) && scoreDifferenceGreaterOrEqualTwo()) {
                return teamNo + 1;
            }
        }

        return 0;
    }

    private boolean scoreGreaterOrEqualSixOfTeam(int teamNo) {
        return teams.get(teamNo).getScore() >= 6;
    }

    private boolean scoreDifferenceGreaterOrEqualTwo() {
        final int necessaryScoreDifference = 2;

        int actualScoreDifference = Math.abs(teams.get(0).getScore() - teams.get(1).getScore());

        return actualScoreDifference >= necessaryScoreDifference;
    }
}

