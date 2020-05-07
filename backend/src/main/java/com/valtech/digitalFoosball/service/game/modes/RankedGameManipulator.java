package com.valtech.digitalFoosball.service.game.modes;

import com.valtech.digitalFoosball.model.GameDataModel;
import com.valtech.digitalFoosball.model.input.InitDataModel;
import com.valtech.digitalFoosball.service.game.init.RankedInitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RankedGameManipulator extends AbstractGameManipulator {
    private final RankedInitService initService;

    @Autowired
    public RankedGameManipulator(RankedInitService initService) {
        super(initService);
        this.initService = initService;
    }

    @Override
    public GameDataModel initGame(InitDataModel initDataModel) {
        return initService.init(initDataModel);
    }
}