package com.valtech.digitalFoosball.domain.timeGame;

import com.valtech.digitalFoosball.domain.adhoc.AdHocInitService;
import com.valtech.digitalFoosball.domain.common.IPlayAGame;
import com.valtech.digitalFoosball.domain.common.InitService;
import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.models.GameDataModel;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TimeGame implements IPlayAGame {
    private final InitService initService;
    private TimeGameRules timeGameRules;
    private GameDataModel model;

    @Autowired
    public TimeGame(AdHocInitService initService) {
        timeGameRules = new TimeGameRules();
        this.initService = initService;
    }

    // TODO: 31.07.20 m.huber ask erwin how to test line 28
    public void initGame(InitDataModel initDataModel) {
        model = initService.init(initDataModel);
        timeGameRules = new TimeGameRules();
        timeGameRules.startTimer();
    }

    public void countGoalFor(Team team) {
        timeGameRules.raiseScoreFor(team);
    }

    public void undoGoal() {
        timeGameRules.undoLastGoal();
    }

    public void redoGoal() {
        timeGameRules.redoLastGoal();
    }

    public void changeover() {
        timeGameRules.changeOver();
    }

    public void resetMatch() {
        model.resetMatch();
        timeGameRules = new TimeGameRules();
    }

    public TimeGameOutputModel getGameData() {
        return new TimeGameOutputModel(model, timeGameRules);
    }
}