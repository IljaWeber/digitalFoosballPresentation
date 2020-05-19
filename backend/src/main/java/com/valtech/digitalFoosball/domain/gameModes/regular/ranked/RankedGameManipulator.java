package com.valtech.digitalFoosball.domain.gameModes.regular.ranked;

import com.valtech.digitalFoosball.domain.gameModes.manipulators.AbstractGameManipulator;
import com.valtech.digitalFoosball.domain.gameModes.models.GameDataModel;
import com.valtech.digitalFoosball.domain.gameModes.models.InitDataModel;
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
