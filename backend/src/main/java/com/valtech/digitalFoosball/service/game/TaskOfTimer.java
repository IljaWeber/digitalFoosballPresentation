package com.valtech.digitalFoosball.service.game;

import com.valtech.digitalFoosball.service.game.modes.TimeGame;

import java.util.TimerTask;

public class TaskOfTimer extends TimerTask {

    private final TimeGame timeGame;

    public TaskOfTimer(TimeGame timeGame) {
        this.timeGame = timeGame;
    }

    @Override
    public void run() {
        timeGame.timeIsOver();
    }
}
