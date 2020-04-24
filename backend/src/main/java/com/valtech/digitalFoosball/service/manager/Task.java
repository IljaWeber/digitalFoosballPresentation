package com.valtech.digitalFoosball.service.manager;

import java.util.TimerTask;

public class Task extends TimerTask {

    private final TimeManager timeManager;

    public Task(TimeManager timeManager) {
        this.timeManager = timeManager;
    }

    @Override
    public void run() {
        timeManager.timeIsOver();
    }
}
