package com.valtech.digitalFoosball.domain.timeGame.service;

import com.valtech.digitalFoosball.domain.timeGame.TimeGame;

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
