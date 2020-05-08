package com.valtech.digitalFoosball.model.internal;

public class TimeGameDataModel extends RegularGameDataModel {
    private boolean timeIsOver;

    public TimeGameDataModel() {
        timeIsOver = false;
    }

    public boolean isTimeOver() {
        return timeIsOver;
    }

    public void timeLimitReached() {
        timeIsOver = true;
        updateObservers();
    }
}
