package com.valtech.digitalFoosball.domain.common;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.models.GameDataModel;
import com.valtech.digitalFoosball.domain.common.models.TeamDataModel;

import static com.valtech.digitalFoosball.domain.common.constants.Team.*;

public abstract class BaseGameRules implements GameRules {

    protected int getScoreOfTeam(Team team, GameDataModel gameDataModel) {
        TeamDataModel teamDataModel = gameDataModel.getTeam(team);
        return teamDataModel.getScore();
    }

    @Override
    public Team getTeamWithLeadOfTwo(GameDataModel gameDataModel) {
        Team winner = NO_TEAM;

        if (thereIsALeadingTeam(gameDataModel)) {

            Team leadingTeam = gameDataModel.getLeadingTeam();
            TeamDataModel teamDataModel = gameDataModel.getTeam(leadingTeam);

            if (enoughGoals(teamDataModel) && bigEnoughScoreDifference(gameDataModel)) {
                winner = leadingTeam;
            }

        }

        return winner;
    }

    private boolean thereIsALeadingTeam(GameDataModel gameDataModel) {
        return gameDataModel.getLeadingTeam() != NO_TEAM;
    }

    private boolean enoughGoals(TeamDataModel team) {
        int neededGoals = 6;
        return team.getScore() >= neededGoals;
    }

    private boolean bigEnoughScoreDifference(GameDataModel gameDataModel) {
        int scoreTeamOne = getScoreOfTeam(ONE, gameDataModel);
        int scoreTeamTwo = getScoreOfTeam(TWO, gameDataModel);

        int currentDifference = scoreTeamOne - scoreTeamTwo;
        int absoluteDifference = Math.abs(currentDifference);

        int requiredDifference = 2;
        return absoluteDifference >= requiredDifference;
    }
}
