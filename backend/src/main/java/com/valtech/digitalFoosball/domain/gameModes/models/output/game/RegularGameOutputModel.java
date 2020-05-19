package com.valtech.digitalFoosball.domain.gameModes.models.output.game;

import com.valtech.digitalFoosball.domain.constants.Team;
import com.valtech.digitalFoosball.domain.gameModes.models.GameDataModel;

public class RegularGameOutputModel extends BaseGameOutputModel {
    private Team winnerOfSet;

    public RegularGameOutputModel(GameDataModel gameDataModel) {
        super(gameDataModel);
        this.winnerOfSet = gameDataModel.getSetWinner();
    }

    @Override
    public Team getWinnerOfSet() {
        return winnerOfSet;
    }

    @Override
    public void setWinnerOfSet(Team winner) {
        this.winnerOfSet = winner;
    }
}
