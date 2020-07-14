package com.valtech.digitalFoosball.domain.ranked;

import com.valtech.digitalFoosball.domain.common.BaseGameRules;
import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.models.GameDataModel;

import static com.valtech.digitalFoosball.domain.common.constants.Team.NO_TEAM;

public class RankedGameRules extends BaseGameRules {

    @Override
    public void approveWin(GameDataModel gameDataModel) {
        Team winner = getTeamWithLeadOfTwo(gameDataModel);

        if (winner != NO_TEAM) {
            gameDataModel.increaseWonSetsFor(winner);
            gameDataModel.setSetWinner(winner);
        }
    }

    @Override
    public boolean winConditionsFulfilled(GameDataModel gameDataModel) {
        Team setWinner = gameDataModel.getSetWinner();

        return setWinner != NO_TEAM;
    }

}
