package com.valtech.digitalFoosball.domain.ranked;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.models.TeamDataModel;

import java.util.Map;
import java.util.SortedMap;

import static com.valtech.digitalFoosball.domain.common.constants.Team.NO_TEAM;

public class MatchWinVerifier {

    public Team getMatchWinner(SortedMap<Team, TeamDataModel> teams) {
        Team matchWinner = NO_TEAM;
        int requiredSetWins = 2;

        for (Map.Entry<Team, TeamDataModel> entry : teams.entrySet()) {
            if (entry.getValue().getWonSets() >= requiredSetWins) {
                matchWinner = entry.getKey();
            }
        }

        return matchWinner;
    }
}
