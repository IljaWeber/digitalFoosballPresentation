package com.valtech.digitalFoosball.service;

import com.valtech.digitalFoosball.model.output.GameDataModel;
import com.valtech.digitalFoosball.model.output.TeamOutput;

import java.util.List;

public class GameDataExtractor {
    GameManager gameManager;

    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public int extractScoreOf(int teamNumber){
        GameDataModel gameData = gameManager.getGameData();
        List<TeamOutput> teams = gameData.getTeams();
        TeamOutput team = teams.get(teamNumber - 1);
        int score = team.getScore();

        return score;
    }

    public int extractNumberOfWonSetsOf(int teamNumber){
        GameDataModel gameData = gameManager.getGameData();
        List<TeamOutput> teams = gameData.getTeams();
        TeamOutput team = teams.get(teamNumber - 1);
        int setWins = team.getSetWins();

        return setWins;
    }

}
