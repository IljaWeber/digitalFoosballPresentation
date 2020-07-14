package com.valtech.digitalFoosball.domain.common;

import com.valtech.digitalFoosball.domain.adhoc.AdHocGame;
import com.valtech.digitalFoosball.domain.common.constants.GameMode;
import com.valtech.digitalFoosball.domain.ranked.RankedGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameManipulatorProvider {

    private final RankedGame rankedGame;
    private final AdHocGame adHocGame;

    @Autowired
    public GameManipulatorProvider(RankedGame rankedGame,
                                   AdHocGame adHocGame) {
        this.rankedGame = rankedGame;
        this.adHocGame = adHocGame;
    }

    public IPlayAGame getGameManipulator(GameMode gameMode) {
        switch (gameMode) {
            case RANKED:
                return rankedGame;
            default:
                return adHocGame;
        }
    }
}
