package com.valtech.digitalFoosball.domain.common;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.ranked.RankedGameDataModel;
import com.valtech.digitalFoosball.domain.ranked.RankedTeamDataModel;

import static com.valtech.digitalFoosball.domain.common.constants.Team.*;

public abstract class ClassicGameRules {

    protected Team getTeamWithLeadOfTwo(RankedGameDataModel gameDataModel) {
        Team winner = NO_TEAM;

        if (thereIsALeadingTeam(gameDataModel)) {

            Team leadingTeam = gameDataModel.getLeadingTeam();
            RankedTeamDataModel teamDataModel = gameDataModel.getTeam(leadingTeam);

            if (enoughGoals(teamDataModel) && bigEnoughScoreDifference(gameDataModel)) {
                winner = leadingTeam;
            }

        }

        return winner;
    }

    private boolean thereIsALeadingTeam(RankedGameDataModel gameDataModel) {
        return NO_TEAM != gameDataModel.getLeadingTeam();
    }

    private boolean enoughGoals(RankedTeamDataModel team) {
        int neededGoals = 6;
        return team.getScore() >= neededGoals;
    }

    private boolean bigEnoughScoreDifference(RankedGameDataModel gameDataModel) {
        int scoreOfTeamOne = gameDataModel.getTeam(ONE).getScore();
        int scoreOfTeamTwo = gameDataModel.getTeam(TWO).getScore();

        int currentDifference = scoreOfTeamOne - scoreOfTeamTwo;
        int absoluteDifference = Math.abs(currentDifference);

        int requiredDifference = 2;
        return absoluteDifference >= requiredDifference;
    }
}
