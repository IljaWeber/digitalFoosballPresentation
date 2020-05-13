package com.valtech.digitalFoosball.domain.gameModes.timePlay;

import com.valtech.digitalFoosball.domain.gameModes.manipulators.AbstractGameManipulator;
import com.valtech.digitalFoosball.domain.gameModes.models.GameDataModel;

import java.util.Timer;

public class TimeGameManipulator extends AbstractGameManipulator {
    private boolean timeIsOver = false;
    private final TimeGameWinApprover timeGameWinApprover;

    public TimeGameManipulator() {
        super(new TimeGameWinApprover());
        timeGameWinApprover = new TimeGameWinApprover();
    }

    public void setTimer(long timeDuration, GameDataModel dataModel) {
        Timer timer = new Timer();
        timer.schedule(new TaskOfTimer(this, dataModel), timeDuration);
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
