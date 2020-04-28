package com.valtech.digitalFoosball.service.game.factory;

import com.valtech.digitalFoosball.constants.GameMode;
import com.valtech.digitalFoosball.service.game.Game;

public abstract class AbstractGameFactory {
    public static AbstractGameFactory getFactory(GameMode gameMode) {
        switch (gameMode) {
            case RANKED:
                return new RankedGameFactory();
            case TIME_GAME:
                return new TimeGameFactory();
            default:
                return new AdHocGameFactory();
        }
    }

    public abstract Game createGame();
}
