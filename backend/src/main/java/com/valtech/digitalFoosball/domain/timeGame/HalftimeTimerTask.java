package com.valtech.digitalFoosball.domain.timeGame;

import java.util.TimerTask;

public class HalftimeTimerTask extends TimerTask {

    private final TimeGameRules timeGame;

    public HalftimeTimerTask(TimeGameRules timeGame) {
        this.timeGame = timeGame;
    }

    @Override
    public void run() {
        timeGame.timeRanDown();
    }
}
