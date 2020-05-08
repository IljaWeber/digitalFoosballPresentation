package com.valtech.digitalFoosball.service.game.manipulator;

import com.valtech.digitalFoosball.model.input.InitDataModel;
import com.valtech.digitalFoosball.model.internal.GameDataModel;
import com.valtech.digitalFoosball.model.output.TeamOutput;
import com.valtech.digitalFoosball.service.game.TaskOfTimer;
import com.valtech.digitalFoosball.service.game.init.RankedInitService;
import com.valtech.digitalFoosball.service.verifier.TimeGameSetWinVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Timer;

@Service
public class TimeGameManipulator extends AbstractGameManipulator {
    private boolean timeIsOver = false;
    private final TimeGameSetWinVerifier timeGameSetWinVerifier;

    @Autowired
    public TimeGameManipulator(RankedInitService initService) {
        super(initService, new TimeGameSetWinVerifier());
        timeGameSetWinVerifier = new TimeGameSetWinVerifier();
    }

    public void setTimer(long timeDuration, GameDataModel dataModel) {
        Timer timer = new Timer();
        timer.schedule(new TaskOfTimer(this, dataModel), timeDuration);
    }

    @Override
    public List<TeamOutput> getAllTeamsFromDatabase() {
        return null;
    }

    @Override
    public GameDataModel initGame(InitDataModel initDataModel) {
        int MAX_TIME = 420000;
        GameDataModel dataModel = initService.init(initDataModel);

        setTimer(MAX_TIME, dataModel);

        return dataModel;
    }

    public void timeIsOver() {
        timeIsOver = true;
    }

    @Override
    public void changeover(GameDataModel gameDataModel) {
    }

    public boolean isTimeOver() {
        return timeIsOver;
    }
}
