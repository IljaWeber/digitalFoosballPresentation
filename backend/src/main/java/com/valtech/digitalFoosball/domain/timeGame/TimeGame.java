package com.valtech.digitalFoosball.domain.timeGame;

import com.valtech.digitalFoosball.api.INotifyAboutStateChanges;
import com.valtech.digitalFoosball.domain.IInitializeGames;
import com.valtech.digitalFoosball.domain.adhoc.AdHocInitService;
import com.valtech.digitalFoosball.domain.common.BaseGame;
import com.valtech.digitalFoosball.domain.common.models.GameDataModel;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Timer;

@Service
public class TimeGame extends BaseGame<TimeGameRules> {
    private final IInitializeGames initService;
    private final INotifyAboutStateChanges publisher;
    protected Timer timer;
    private GameDataModel model;

    @Autowired
    public TimeGame(AdHocInitService initService, INotifyAboutStateChanges publisher) {
        this.initService = initService;
        this.publisher = publisher;
        gameRules = new TimeGameRules();
        timer = new Timer();
    }

    public void initGame(InitDataModel initDataModel) {
        model = initService.init(initDataModel);
        gameRules = new TimeGameRules();
        gameRules.setGame(this);
    }

    public void resetMatch() {
        model.resetMatch();
        gameRules = new TimeGameRules();
    }

    public void timeRanDown() {
        gameRules.timeRanDown();
    }

    public TimeGameOutputModel getGameData() {
        return new TimeGameOutputModel(model, gameRules);
    }

    public void gameSequenceChanged() {
        publisher.notifyAboutStateChange(getGameData());
    }
}
