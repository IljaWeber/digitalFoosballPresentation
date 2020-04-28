package com.valtech.digitalFoosball.service.game;

import java.util.TimerTask;

public class TaskOfTimer extends TimerTask {

    private final TimeManager timeManager;

    public TaskOfTimer(TimeManager timeManager) {
        this.timeManager = timeManager;
    }

    @Override
    public void run() {
        timeManager.timeIsOver();
    }
}