package com.valtech.digitalFoosball.service.verifier;

import com.valtech.digitalFoosball.constants.Team;
import com.valtech.digitalFoosball.model.GameDataModel;
import com.valtech.digitalFoosball.model.internal.TeamDataModel;

import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

import static com.valtech.digitalFoosball.constants.Team.NO_TEAM;

public class TimeGameSetWinVerifier implements SetWinApprover {

    public static final int GOAL_LIMIT = 10;

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

            return NO_TEAM;
        }
    }

    @Override
    public void approveWin(GameDataModel gameDataModel) {
        Team winningTeam = getWinnerOfWinConditionOfGoalLimit(gameDataModel);

        if (winningTeam != NO_TEAM) {
            gameDataModel.increaseWonSetsFor(winningTeam);
            gameDataModel.setSetWinner(winningTeam);
        }
    }

    private Team getWinnerOfWinConditionOfGoalLimit(GameDataModel gameDataModel) {
        SortedMap<Team, TeamDataModel> teams = gameDataModel.getTeams();
        Set<Map.Entry<Team, TeamDataModel>> entries = teams.entrySet();

        for (Map.Entry<Team, TeamDataModel> entry : entries) {
            if (entry.getValue().getScore() >= GOAL_LIMIT) {
                return entry.getKey();
            }
        }

        return NO_TEAM;
    }
}
