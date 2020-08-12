package com.valtech.digitalFoosball.domain.timeGame;

import com.valtech.digitalFoosball.domain.timeGame.sequences.PlayHalves;

import java.util.TimerTask;

public class TimeGameTimerTask extends TimerTask {
    private final PlayHalves game;

    public TimeGameTimerTask(PlayHalves timeGame) {
        this.game = timeGame;
    }

    @Override
    public void run() {
        game.timeRanDown();
    }
}
