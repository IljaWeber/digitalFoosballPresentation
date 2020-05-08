package com.valtech.digitalFoosball.service.verifier;

import com.valtech.digitalFoosball.constants.Team;
import com.valtech.digitalFoosball.model.internal.GameDataModel;
import com.valtech.digitalFoosball.model.internal.TeamDataModel;

import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

import static com.valtech.digitalFoosball.constants.Team.NO_TEAM;

public class TimeGameSetWinVerifier implements SetWinApprover {
    public final int GOAL_LIMIT = 10;

    public Team getWinner(GameDataModel gameDataModel) {

        if (isTimeOver()) {

        }
        return null;
    }

    private boolean isTimeOver() {

        return false;
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
