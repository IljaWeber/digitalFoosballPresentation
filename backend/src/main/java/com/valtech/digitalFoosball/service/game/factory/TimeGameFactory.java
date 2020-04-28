package com.valtech.digitalFoosball.service.game.factory;

import com.valtech.digitalFoosball.service.game.Game;
import com.valtech.digitalFoosball.service.game.TimeGame;

public class TimeGameFactory extends AbstractGameFactory {
    @Override
    public Game createGame() {
        return new TimeGame();
    }
}
