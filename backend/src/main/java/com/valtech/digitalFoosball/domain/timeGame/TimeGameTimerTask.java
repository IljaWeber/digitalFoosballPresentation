package com.valtech.digitalFoosball.domain.timeGame;

import java.util.TimerTask;

public class TimeGameTimerTask extends TimerTask {
    private final FirstHalf game;

    public TimeGameTimerTask(FirstHalf timeGame) {
        this.game = timeGame;
    }

    @Override
    public void run() {
        game.nextSequenceByTime();
    }
}
