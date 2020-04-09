package com.valtech.digitalFoosball.helper.extractor;

import com.valtech.digitalFoosball.constants.Team;
import com.valtech.digitalFoosball.model.internal.TeamDataModel;
import com.valtech.digitalFoosball.service.GameManager;

import java.util.Map;

public class GameDataExtractor {
    GameManager gameManager;

    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    public int extractScoreOf(Team team) {
        Map<Team, TeamDataModel> teams = gameManager.getTeams();
        TeamDataModel teamDataModel = teams.get(team);

        int score = teamDataModel.getScore();

        return score;
    }

    public int extractNumberOfWonSetsOf(Team team) {
        Map<Team, TeamDataModel> teams = gameManager.getTeams();
        TeamDataModel teamDataModel = teams.get(team);

        int setWins = teamDataModel.getWonSets();

        return setWins;
    }

}
