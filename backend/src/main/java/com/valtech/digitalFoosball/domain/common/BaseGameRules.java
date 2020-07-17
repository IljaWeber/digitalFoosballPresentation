package com.valtech.digitalFoosball.domain.common;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.ranked.RankedGameDataModel;
import com.valtech.digitalFoosball.domain.ranked.RankedTeamDataModel;

import static com.valtech.digitalFoosball.domain.common.constants.Team.*;

public abstract class BaseGameRules implements GameRules {

    protected int getScoreOfTeam(Team team, RankedGameDataModel gameDataModel) {
        RankedTeamDataModel teamDataModel = gameDataModel.getTeam(team);
        return teamDataModel.getScore();
    }

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
        int scoreTeamOne = getScoreOfTeam(ONE, gameDataModel);
        int scoreTeamTwo = getScoreOfTeam(TWO, gameDataModel);

        int currentDifference = scoreTeamOne - scoreTeamTwo;
        int absoluteDifference = Math.abs(currentDifference);

        int requiredDifference = 2;
        return absoluteDifference >= requiredDifference;
    }

    public abstract void raiseScoreFor(Team team);
}
