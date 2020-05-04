package com.valtech.digitalFoosball.service.game;

import com.valtech.digitalFoosball.service.game.modes.TimeGameManipulator;

import java.util.TimerTask;

public class TaskOfTimer extends TimerTask {

    private final TimeGameManipulator timeGame;

    public TaskOfTimer(TimeGameManipulator timeGame) {
        this.timeGame = timeGame;
    }

    @Override
    public void run() {
        timeGame.timeIsOver();
    }
}
