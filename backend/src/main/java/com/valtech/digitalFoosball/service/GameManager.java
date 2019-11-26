package com.valtech.digitalFoosball.service;

import com.valtech.digitalFoosball.exceptions.PlayerDuplicateException;
import com.valtech.digitalFoosball.exceptions.TeamDuplicateException;
import com.valtech.digitalFoosball.model.input.InitDataModel;
import com.valtech.digitalFoosball.model.internal.PlayerDataModel;
import com.valtech.digitalFoosball.model.internal.TeamDataModel;
import com.valtech.digitalFoosball.model.output.GameDataModel;
import com.valtech.digitalFoosball.model.output.TeamOutput;
import com.valtech.digitalFoosball.storage.TeamService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private Logger logger = LogManager.getLogger(GameManager.class);

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
            logger.info("Team {} signed in", team::getName);
            teamService.setUp(team);
        }
    }

    private void checkForDuplicateNames(InitDataModel initDataModel) {
        List<String> playerNames = new ArrayList<>();
        List<String> teamNames = new ArrayList<>();

        for (TeamDataModel team : initDataModel.getTeams()) {

            if (teamNames.contains(team.getName())) {

                throw new TeamDuplicateException(team.getName());

            } else teamNames.add(team.getName());

            for (PlayerDataModel player : team.getPlayers()) {

                if (playerNames.contains(player.getName())) {

                    throw new PlayerDuplicateException(player.getName());

                } else playerNames.add(player.getName());
            }
        }
    }

    public List<TeamDataModel> getTeams() {
        return teams;
    }

    public void raiseScore(int teamNo) {
        logger.info("Score was raised for {}", teamNo);

        TeamDataModel teamDataModel = teams.get(teamNo - 1);
        teamDataModel.increaseScore();
        historyOfGoals.push(teamNo - 1);
        if (getRoundWinner() != 0) {
            teamDataModel.increaseWonRounds();
        }
    }

    public GameDataModel getGameData() {
        GameDataModel currentGameData = new GameDataModel();
        List<TeamOutput> convertedTeams = new ArrayList<>();

        if (teams == null) {
            return null;
        }

        for (TeamDataModel team : teams) {
            TeamOutput teamOutput = converter.convertToTeamOutput(team);
            convertedTeams.add(teamOutput);
        }

        currentGameData.setTeams(convertedTeams);
        currentGameData.setRoundWinner(getRoundWinner());
        currentGameData.setMatchWinner(getMatchWinner());

        return currentGameData;
    }

    public void undoLastGoal() {
        if (!historyOfGoals.empty()) {
            Integer indexOfLastScoringTeam = historyOfGoals.pop();
            TeamDataModel lastScoringTeam = teams.get(indexOfLastScoringTeam);

            if (getRoundWinner() != 0) {
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

            if (getRoundWinner() != 0) {
                teamDataModel.increaseWonRounds();
            }
        }
    }

    public void resetGameValues() {
        for (TeamDataModel team : teams) {
            team.resetValues();
        }
        historyOfGoals = new Stack<>();
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

        if (actualScoreDifference >= necessaryScoreDifference) {
            return true;
        }

        return false;
    }

    public List<TeamOutput> getAllTeams() {
        List<TeamDataModel> teamDataModels = teamService.getAll();
        List<TeamOutput> teamOutputs = new ArrayList<>();

        if (teamDataModels.isEmpty()) {
            return new ArrayList<>();
        }

        for (TeamDataModel teamDataModel : teamDataModels) {
            teamOutputs.add(converter.convertToTeamOutput(teamDataModel));
        }

        return teamOutputs;
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

    public void newRound() {
        for (TeamDataModel team : teams) {
            team.resetScore();
        }

        historyOfGoals = new Stack<>();
    }
}
