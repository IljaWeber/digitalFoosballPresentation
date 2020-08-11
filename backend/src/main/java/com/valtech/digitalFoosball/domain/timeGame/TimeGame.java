package com.valtech.digitalFoosball.domain.timeGame;

import com.valtech.digitalFoosball.api.INotifyAboutStateChanges;
import com.valtech.digitalFoosball.domain.IInitializeGames;
import com.valtech.digitalFoosball.domain.adhoc.AdHocInitService;
import com.valtech.digitalFoosball.domain.common.BaseGame;
import com.valtech.digitalFoosball.domain.common.models.GameDataModel;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TimeGame extends BaseGame {
    private final IInitializeGames initService;
    private final INotifyAboutStateChanges publisher;
    private TimeGameRules timeGameRules;
    private GameDataModel model;

    @Autowired
    public TimeGame(AdHocInitService initService, INotifyAboutStateChanges publisher) {
        this.initService = initService;
        this.publisher = publisher;
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

    public void gameSequenceChanged() {
        publisher.notifyAboutStateChange(getGameData());
    }
}