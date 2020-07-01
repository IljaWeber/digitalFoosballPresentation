package com.valtech.digitalFoosball.domain.common.models.output.game;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.models.GameDataModel;

public class RegularGameOutputModel extends BaseGameOutputModel {
    private final Team winnerOfSet;

    public RegularGameOutputModel(GameDataModel gameDataModel) {
        super(gameDataModel);
        this.winnerOfSet = gameDataModel.getSetWinner();
    }

    @Override
    public Team getWinnerOfSet() {
        return winnerOfSet;
    }

}