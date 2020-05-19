package com.valtech.digitalFoosball.domain.gameModes.manipulators;

import com.valtech.digitalFoosball.domain.constants.GameMode;
import com.valtech.digitalFoosball.domain.gameModes.regular.adhoc.AdHocGame;
import com.valtech.digitalFoosball.domain.gameModes.regular.ranked.RankedGame;
import com.valtech.digitalFoosball.domain.gameModes.timePlay.TimeGame;
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
