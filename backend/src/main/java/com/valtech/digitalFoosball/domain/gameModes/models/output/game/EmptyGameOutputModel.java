package com.valtech.digitalFoosball.domain.gameModes.models.output.game;

import com.valtech.digitalFoosball.domain.constants.Team;
import com.valtech.digitalFoosball.domain.gameModes.regular.models.game.RankedGameDataModel;

public class EmptyGameOutputModel extends BaseGameOutputModel {

    public EmptyGameOutputModel() {
        super(new RankedGameDataModel());
    }

    @Override
    public Team getWinnerOfSet() {
        return null;
    }

}
