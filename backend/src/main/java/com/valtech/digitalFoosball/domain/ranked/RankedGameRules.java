package com.valtech.digitalFoosball.domain.ranked;

import com.valtech.digitalFoosball.domain.common.BaseGameRules;
import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.models.GameDataModel;

import static com.valtech.digitalFoosball.domain.common.constants.Team.*;

public class RankedGameRules extends BaseGameRules {

    @Override
    public void approveWin(GameDataModel gameDataModel) {
        Team winner = getWinner(gameDataModel);

        if (winner != NO_TEAM) {
            gameDataModel.increaseWonSetsFor(winner);
            gameDataModel.setSetWinner(winner);
        }
    }

    private Team getWinner(GameDataModel gameDataModel) {
        Team winner = NO_TEAM;

        if (thereIsALeadingTeam(gameDataModel)) {

            Team leadingTeam = getLeadingTeam(gameDataModel);
            RankedTeamDataModel teamDataModel = gameDataModel.getTeam(leadingTeam);

            if (enoughGoals(teamDataModel) && bigEnoughScoreDifference(gameDataModel)) {
                winner = leadingTeam;
            }

        }

        return winner;
    }

    private boolean enoughGoals(RankedTeamDataModel team) {
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
