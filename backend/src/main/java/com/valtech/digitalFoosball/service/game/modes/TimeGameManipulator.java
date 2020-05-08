package com.valtech.digitalFoosball.service.game.modes;

import com.valtech.digitalFoosball.model.GameDataModel;
import com.valtech.digitalFoosball.model.input.InitDataModel;
import com.valtech.digitalFoosball.model.output.TeamOutput;
import com.valtech.digitalFoosball.service.game.TaskOfTimer;
import com.valtech.digitalFoosball.service.game.init.RankedInitService;
import com.valtech.digitalFoosball.service.histories.History;
import com.valtech.digitalFoosball.service.verifier.TimeGameSetWinVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Timer;

@Service
public class TimeGameManipulator extends AbstractGameManipulator {
    private final int GOAL_LIMIT = 10;
    private boolean timeIsOver = false;
    private Timer timer;
    private final History history;
    private final TimeGameSetWinVerifier timeGameSetWinVerifier;

    @Autowired
    public TimeGameManipulator(RankedInitService initService) {
        super(initService, new TimeGameSetWinVerifier());
        history = new History();
        timeGameSetWinVerifier = new TimeGameSetWinVerifier();
    }

    public void setTimer(long timeDuration) {
        timer = new Timer();
        timer.schedule(new TaskOfTimer(this), timeDuration);
    }

    @Override
    public List<TeamOutput> getAllTeamsFromDatabase() {
        return null;
    }

    @Override
    public GameDataModel initGame(InitDataModel initDataModel) {
        return null;
    }

    public void timeIsOver() {
        timeIsOver = true;
    }

    @Override
    public void changeover(GameDataModel gameDataModel) {

    }
}
