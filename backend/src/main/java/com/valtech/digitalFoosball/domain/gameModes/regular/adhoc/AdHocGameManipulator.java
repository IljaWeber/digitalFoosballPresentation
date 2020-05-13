package com.valtech.digitalFoosball.domain.gameModes.regular.adhoc;

import com.valtech.digitalFoosball.domain.gameModes.manipulators.AbstractGameManipulator;
import com.valtech.digitalFoosball.domain.gameModes.regular.ranked.RankedGameSetWinApprover;

public class AdHocGameManipulator extends AbstractGameManipulator {
    public AdHocGameManipulator() {
        super(new RankedGameSetWinApprover());
    }
}
