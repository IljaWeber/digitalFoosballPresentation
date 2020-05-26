package com.valtech.digitalFoosball.domain.common.models.output.game;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.ranked.RankedGameDataModel;

public class EmptyGameOutputModel extends BaseGameOutputModel {

    public EmptyGameOutputModel() {
        super(new RankedGameDataModel());
    }

    @Override
    public Team getWinnerOfSet() {
        return null;
    }

}
