package com.valtech.digitalFoosball.domain.timeGame;

import java.util.TimerTask;

public class MyTimerTask extends TimerTask {

    private TimeGame game;

    public MyTimerTask(TimeGame game) {
        this.game = game;
    }

    @Override
    public void run() {
        game.timeRanDown();
    }
}
