package com.valtech.digitalFoosball.domain.common.models.output.game;

import com.valtech.digitalFoosball.domain.common.constants.Team;

public class EmptyGameOutputModel extends BaseGameOutputModel implements GameOutputModel {

    @Override
    public Team getWinnerOfSet() {
        return null;
    }

}
