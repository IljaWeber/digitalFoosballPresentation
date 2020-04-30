package com.valtech.digitalFoosball.service.game;

import com.valtech.digitalFoosball.constants.GameMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameModeHolder {

    private final RankedGame rankedGame;
    private final TimeGame timeGame;
    private final AdHocGame adHocGame;

    @Autowired
    public GameModeHolder(RankedGame rankedGame, TimeGame timeGame, AdHocGame adHocGame) {
        this.rankedGame = rankedGame;
        this.timeGame = timeGame;
        this.adHocGame = adHocGame;
    }

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
