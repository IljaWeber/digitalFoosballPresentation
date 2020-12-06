package com.valtech.digitalFoosball.domain.timeGame;

import com.valtech.digitalFoosball.domain.IInitializeGames;
import com.valtech.digitalFoosball.domain.IPlayAGame;
import com.valtech.digitalFoosball.domain.adhoc.AdHocInitService;
import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.models.GameDataModel;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import com.valtech.digitalFoosball.domain.ports.INotifyAboutStateChanges;
import com.valtech.digitalFoosball.domain.timeGame.service.TimeGameTimerTask;

import java.util.Timer;

public class TimeGame implements IPlayAGame {
    private final IInitializeGames initService;
    private final INotifyAboutStateChanges publisher;
    private final Timer timer;
    protected TimeGameRules gameRules;
    private GameDataModel model;

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

    @Override
    public void setGameDataModel(GameDataModel gameDataModel) {
        this.model = gameDataModel;
    }

    public void informClients() {
        publisher.notifyAboutStateChange(getGameData());
    }

    @Override
    public void changeover() {
        gameRules.changeOver();
        startTimer();
    }

    public void countGoalFor(Team team) {
        gameRules.raiseScoreFor(team);
    }

    public void undoGoal() {
        gameRules.undoLastGoal();
    }

    public void redoGoal() {
        gameRules.redoLastGoal();
    }

    public void setGameRules(TimeGameRules gameRules) {
        this.gameRules = gameRules;
    }
}
