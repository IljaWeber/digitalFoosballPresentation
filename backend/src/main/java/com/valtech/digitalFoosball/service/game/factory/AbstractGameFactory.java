package com.valtech.digitalFoosball.service.game.factory;

import com.valtech.digitalFoosball.constants.GameMode;
import com.valtech.digitalFoosball.service.game.Game;

public abstract class AbstractGameFactory {
    public static AbstractGameFactory getFactory(GameMode gameMode) {
        return new AdHocGameFactory();
    }

    public abstract Game createGame();
}
