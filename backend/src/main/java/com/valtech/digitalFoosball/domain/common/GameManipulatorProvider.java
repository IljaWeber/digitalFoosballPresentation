package com.valtech.digitalFoosball.domain.common;

import com.valtech.digitalFoosball.domain.adhoc.AdHocGame;
import com.valtech.digitalFoosball.domain.common.constants.GameMode;
import com.valtech.digitalFoosball.domain.ranked.RankedGame;
import com.valtech.digitalFoosball.domain.timePlay.TimeGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameManipulatorProvider {

    private final RankedGame rankedGame;
    private final TimeGame timeGame;
    private final AdHocGame adHocGame;

    @Autowired
    public GameManipulatorProvider(RankedGame rankedGame,
                                   TimeGame timeGame,
                                   AdHocGame adHocGame) {
        this.rankedGame = rankedGame;
        this.timeGame = timeGame;
        this.adHocGame = adHocGame;
    }

    public IPlayAGame getGameManipulator(GameMode gameMode) {
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
