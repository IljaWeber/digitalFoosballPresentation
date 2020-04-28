package com.valtech.digitalFoosball.service.game.factory;

import com.valtech.digitalFoosball.service.game.Game;
import com.valtech.digitalFoosball.service.game.RankedGame;

public class RankedGameFactory extends AbstractGameFactory {
    @Override
    public Game createGame() {
        return new RankedGame();
    }
}
