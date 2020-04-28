package com.valtech.digitalFoosball.service.game.factory;

import com.valtech.digitalFoosball.service.game.AdHocGame;
import com.valtech.digitalFoosball.service.game.Game;

public class AdHocGameFactory extends AbstractGameFactory {
    @Override
    public Game createGame() {
        return new AdHocGame();
    }
}
