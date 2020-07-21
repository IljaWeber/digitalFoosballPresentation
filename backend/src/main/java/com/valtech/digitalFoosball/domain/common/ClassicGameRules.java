package com.valtech.digitalFoosball.domain.common;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.ranked.RankedTeamDataModel;

import java.util.SortedMap;

import static com.valtech.digitalFoosball.domain.common.constants.Team.*;

public abstract class ClassicGameRules {

    protected Team checkForWin(SortedMap<Team, RankedTeamDataModel> teams) {
        Team leadingTeam = NO_TEAM;
        Team winner = NO_TEAM;

        int scoreOfTeamOne = teams.get(ONE).getScore();
        int scoreOfTeamTwo = teams.get(TWO).getScore();

        if (scoreOfTeamOne > scoreOfTeamTwo) {
            leadingTeam = ONE;
        }

        if (scoreOfTeamTwo > scoreOfTeamOne) {
            leadingTeam = TWO;
        }

        if (leadingTeam != NO_TEAM) {
            RankedTeamDataModel leading = teams.get(leadingTeam);

            if (enoughGoals(leading) && bigEnoughScoreDifference(scoreOfTeamOne, scoreOfTeamTwo)) {
                winner = leadingTeam;
            }
        }

        return winner;
    }

    private boolean enoughGoals(RankedTeamDataModel team) {
        int neededGoals = 6;
        return team.getScore() >= neededGoals;
    }

    private boolean bigEnoughScoreDifference(int scoreOfTeamOne, int scoreOfTeamTwo) {
        int currentDifference = scoreOfTeamOne - scoreOfTeamTwo;
        int absoluteDifference = Math.abs(currentDifference);

        int requiredDifference = 2;
        return absoluteDifference >= requiredDifference;
    }
}
