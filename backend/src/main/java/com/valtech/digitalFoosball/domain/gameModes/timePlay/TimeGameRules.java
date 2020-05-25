package com.valtech.digitalFoosball.domain.gameModes.timePlay;

import com.valtech.digitalFoosball.domain.constants.Team;
import com.valtech.digitalFoosball.domain.gameModes.regular.models.game.GameDataModel;
import com.valtech.digitalFoosball.domain.gameModes.regular.models.game.TimeGameDataModel;
import com.valtech.digitalFoosball.domain.gameModes.winConditionApprover.BaseGameRules;

import static com.valtech.digitalFoosball.domain.constants.Team.NO_TEAM;

public class TimeGameRules extends BaseGameRules {
    private final int GOAL_LIMIT = 10;

    public Team getWinner(TimeGameDataModel gameDataModel) {
        Team winner = NO_TEAM;
        Team leadingTeam = getLeadingTeam(gameDataModel);

        if (leadingTeam == NO_TEAM) {
            return NO_TEAM;
        }

        int score = getScoreOfTeam(leadingTeam, gameDataModel);

        if (goalLimitReached(score)) {
            winner = leadingTeam;
        }

        if (gameDataModel.isTimeOver()) {
            winner = leadingTeam;
        }

        return winner;
    }

    private boolean goalLimitReached(int score) {
        return score >= GOAL_LIMIT;
    }

    @Override
    public void approveWin(GameDataModel gameDataModel) {
        Team winningTeam = NO_TEAM;

        if (winningTeam != NO_TEAM) {
            gameDataModel.increaseWonSetsFor(winningTeam);
            gameDataModel.setSetWinner(winningTeam);
        }
    }
}
