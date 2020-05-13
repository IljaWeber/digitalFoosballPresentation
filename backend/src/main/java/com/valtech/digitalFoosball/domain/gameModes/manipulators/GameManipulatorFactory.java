package com.valtech.digitalFoosball.domain.gameModes.manipulators;

import com.valtech.digitalFoosball.domain.constants.GameMode;
import com.valtech.digitalFoosball.domain.gameModes.regular.adhoc.AdHocGameManipulator;
import com.valtech.digitalFoosball.domain.gameModes.regular.ranked.RankedGameManipulator;
import com.valtech.digitalFoosball.domain.gameModes.timePlay.TimeGameManipulator;

public abstract class GameManipulatorFactory {
    public static AbstractGameManipulator createManipulatorFor(GameMode gameMode) {
        switch (gameMode) {
            case RANKED:
                return new RankedGameManipulator();
            case TIME_GAME:
                return new TimeGameManipulator();
            default:
                return new AdHocGameManipulator();
        }
    }
}
