package com.valtech.digitalFoosball.domain.common.models.output.game;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.ranked.GameDataModel;

public class RegularGameOutputModel extends BaseGameOutputModel {
    private final Team winnerOfSet;

    public RegularGameOutputModel(GameDataModel gameDataModel) {
        super(gameDataModel);
        this.winnerOfSet = gameDataModel.getWinner();
    }

    @Override
    public Team getWinnerOfSet() {
        return winnerOfSet;
    }

}
