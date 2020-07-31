package com.valtech.digitalFoosball.domain.timeGame;

import com.valtech.digitalFoosball.domain.adhoc.AdHocInitService;
import com.valtech.digitalFoosball.domain.common.BaseGame;
import com.valtech.digitalFoosball.domain.common.IInitializeGames;
import com.valtech.digitalFoosball.domain.common.models.GameDataModel;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TimeGame extends BaseGame {
    private final IInitializeGames initService;
    private TimeGameRules timeGameRules;
    private GameDataModel model;

    @Autowired
    public TimeGame(AdHocInitService initService) {
        timeGameRules = new TimeGameRules();
        this.initService = initService;
    }

    public void initGame(InitDataModel initDataModel) {
        model = initService.init(initDataModel);
        timeGameRules = new TimeGameRules();
        super.setGameRules(timeGameRules);
    }

    public void resetMatch() {
        model.resetMatch();
        timeGameRules = new TimeGameRules();
    }

    public TimeGameOutputModel getGameData() {
        return new TimeGameOutputModel(model, timeGameRules);
    }
}