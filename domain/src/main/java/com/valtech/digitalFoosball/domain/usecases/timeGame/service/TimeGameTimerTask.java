package com.valtech.digitalFoosball.domain.usecases.timeGame.service;

import com.valtech.digitalFoosball.domain.usecases.timeGame.TimeGame;

import java.util.TimerTask;

public class TimeGameTimerTask extends TimerTask {

    private TimeGame game;

    public TimeGameTimerTask(TimeGame game) {
        this.game = game;
    }

    @Override
    public void run() {
        game.timeRanDown();
    }
}
