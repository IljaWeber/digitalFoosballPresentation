package com.valtech.digitalFoosball.service.game.factory;

import com.valtech.digitalFoosball.constants.GameMode;
import com.valtech.digitalFoosball.service.game.AdHocGame;
import com.valtech.digitalFoosball.service.game.Game;
import com.valtech.digitalFoosball.service.game.RankedGame;
import com.valtech.digitalFoosball.service.game.TimeGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameFactory {

    @Autowired
    private RankedGame rankedGame;

    @Autowired
    private TimeGame timeGame;

    @Autowired
    private AdHocGame adHocGame;

    public Game getGame(GameMode gameMode) {
        switch (gameMode) {
            case RANKED:
                return rankedGame;
            case TIME_GAME:
                return timeGame;
            default:
                return adHocGame;
        }
    }
}
