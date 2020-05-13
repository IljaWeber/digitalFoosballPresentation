package com.valtech.digitalFoosball.domain.gameModes.regular.ranked;

import com.valtech.digitalFoosball.domain.gameModes.manipulators.AbstractGameManipulator;

public class RankedGameManipulator extends AbstractGameManipulator {

    public RankedGameManipulator() {
        super(new RankedGameSetWinApprover());
    }
}
