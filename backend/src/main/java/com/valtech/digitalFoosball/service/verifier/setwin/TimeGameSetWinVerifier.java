package com.valtech.digitalFoosball.service.verifier;

import com.valtech.digitalFoosball.constants.Team;
import com.valtech.digitalFoosball.model.GameDataModel;
import com.valtech.digitalFoosball.model.internal.TeamDataModel;

import java.util.Map;

public class TimeGameSetWinVerifier implements GameSetVerifier {

    public static final int GOAL_LIMIT = 10;

    public boolean teamWon(Map<Team, TeamDataModel> teams, Team scoringTeam) {
        TeamDataModel scoringTeamDataModel = teams.get(scoringTeam);

        int score = scoringTeamDataModel.getScore();
        return score >= GOAL_LIMIT;
    }

    public boolean teamWon(GameDataModel teams) {
        return false;
    }

    public Team getWinner(GameDataModel gameDataModel, boolean timeIsOver) {
        int scoreOfTeamOne = gameDataModel.getTeam(Team.ONE).getScore();
        int scoreOfTeamTwo = gameDataModel.getTeam(Team.TWO).getScore();

        if (timeIsOver) {
            if (scoreOfTeamOne > scoreOfTeamTwo) {
                return Team.ONE;
            } else {
                return Team.TWO;
            }
        } else {
            if (scoreOfTeamOne >= 10) {
                return Team.ONE;
            }

            if (scoreOfTeamTwo >= 10) {
                return Team.TWO;
            }

            return Team.NO_TEAM;
        }
    }

    @Override
    public void approveWin(GameDataModel gameDataModel) {

    }

    @Override
    public Team getWinner(GameDataModel gameDataModel) {
        return null;
    }
}
