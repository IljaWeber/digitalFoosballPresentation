package com.valtech.digitalFoosball.domain.gameModes.regular.ranked;

import com.valtech.digitalFoosball.domain.gameModes.manipulators.DigitalFoosballGame;
import com.valtech.digitalFoosball.domain.gameModes.models.InitDataModel;
import com.valtech.digitalFoosball.domain.gameModes.regular.models.game.GameDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RankedGame extends DigitalFoosballGame {

    @Autowired
    public RankedGame(RankedInitService initService) {
        super(initService, new RankedGameRules());
    }

    @Override
    public GameDataModel initGame(InitDataModel initDataModel) {
        return initService.init(initDataModel);
    }
}
