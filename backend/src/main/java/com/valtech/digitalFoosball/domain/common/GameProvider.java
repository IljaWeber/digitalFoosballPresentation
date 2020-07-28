package com.valtech.digitalFoosball.domain.common;

import com.valtech.digitalFoosball.domain.adhoc.AdHocInitService;
import com.valtech.digitalFoosball.domain.common.constants.GameMode;
import com.valtech.digitalFoosball.domain.ranked.RankedInitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameProvider {

    private final RankedInitService rankedInitService;

    @Autowired
    public GameProvider(RankedInitService rankedInitService) {
        this.rankedInitService = rankedInitService;
    }

    public IPlayAGame getGameManipulator(GameMode gameMode) {
        if (gameMode == GameMode.RANKED) {
            return new ClassicGame(rankedInitService);
        }

        return new ClassicGame(new AdHocInitService());
    }
}
