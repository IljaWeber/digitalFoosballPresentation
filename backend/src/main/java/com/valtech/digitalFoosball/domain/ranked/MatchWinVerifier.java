package com.valtech.digitalFoosball.domain.ranked;

import com.valtech.digitalFoosball.domain.common.constants.Team;

import java.util.Map;
import java.util.SortedMap;

import static com.valtech.digitalFoosball.domain.common.constants.Team.NO_TEAM;

public class MatchWinVerifier {

    public Team getMatchWinner(SortedMap<Team, RankedTeamDataModel> teams) {
        Team matchWinner = NO_TEAM;
        int requiredSetWins = 2;

        for (Map.Entry<Team, RankedTeamDataModel> entry : teams.entrySet()) {
            RankedTeamDataModel team = entry.getValue();
            int actualWonSets = team.getWonSets();

            if (actualWonSets >= requiredSetWins) {
                matchWinner = entry.getKey();
            }
        }

        return matchWinner;
    }
}
