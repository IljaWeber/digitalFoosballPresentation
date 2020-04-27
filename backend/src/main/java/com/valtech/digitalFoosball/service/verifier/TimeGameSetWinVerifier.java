package com.valtech.digitalFoosball.service.verifier;

import com.valtech.digitalFoosball.constants.Team;
import com.valtech.digitalFoosball.model.internal.TeamDataModel;

import java.util.Map;

public class TimeGameSetWinVerifier implements SetWinVerifier {

    public static final int GOAL_LIMIT = 10;

    @Override
    public boolean teamWon(Map<Team, TeamDataModel> teams, Team scoringTeam) {
        TeamDataModel scoringTeamDataModel = teams.get(scoringTeam);

        int score = scoringTeamDataModel.getScore();
        return score >= GOAL_LIMIT;
    }
}
