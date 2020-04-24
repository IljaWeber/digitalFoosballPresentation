package com.valtech.digitalFoosball.service.manager;

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
