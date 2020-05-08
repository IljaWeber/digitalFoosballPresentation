package com.valtech.digitalFoosball.service.game.modes;

import com.valtech.digitalFoosball.model.GameDataModel;
import com.valtech.digitalFoosball.model.input.InitDataModel;
import com.valtech.digitalFoosball.service.game.init.RankedInitService;
import com.valtech.digitalFoosball.service.verifier.RankedGameSetWinApprover;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RankedGameManipulator extends AbstractGameManipulator {
    @Autowired
    public RankedGameManipulator(RankedInitService initService) {
        super(initService, new RankedGameSetWinApprover());
    }

    @Override
    public GameDataModel initGame(InitDataModel initDataModel) {
        return initService.init(initDataModel);
    }
}