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
    private final Timer timer;
    private GameDataModel model;

    @Autowired
    public TimeGame(AdHocInitService initService, INotifyAboutStateChanges publisher) {
        this.initService = initService;
        this.publisher = publisher;
        timer = new Timer();
    }

    public void initGame(InitDataModel initDataModel) {
        model = initService.init(initDataModel);
        gameRules = new TimeGameRules();
        startTimer();
    }

    private void startTimer() {
        TimeGameTimerTask task = new TimeGameTimerTask(this);
        timer.schedule(task, 420000);
    }

    public void resetMatch() {
        model.resetMatch();
        gameRules = new TimeGameRules();
    }

    public void timeRanDown() {
        gameRules.timeRanDown();
        informClients();
    }

    public TimeGameOutputModel getGameData() {
        return new TimeGameOutputModel(model, gameRules);
    }

    public void informClients() {
        publisher.notifyAboutStateChange(getGameData());
    }

    @Override
    public void changeover() {
        super.changeover();
        startTimer();
    }
}
