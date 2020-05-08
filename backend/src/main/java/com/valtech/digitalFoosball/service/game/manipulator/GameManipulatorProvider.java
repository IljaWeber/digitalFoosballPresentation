package com.valtech.digitalFoosball.service.game.manipulator;

import com.valtech.digitalFoosball.constants.GameMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameManipulatorProvider {

    private final RankedGameManipulator rankedGame;
    private final TimeGameManipulator timeGame;
    private final AdHocGameManipulator adHocGame;

    @Autowired
    public GameManipulatorProvider(RankedGameManipulator rankedGame,
                                   TimeGameManipulator timeGame,
                                   AdHocGameManipulator adHocGame) {
        this.rankedGame = rankedGame;
        this.timeGame = timeGame;
        this.adHocGame = adHocGame;
    }

    public AbstractGameManipulator getGameManipulator(GameMode gameMode) {
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
