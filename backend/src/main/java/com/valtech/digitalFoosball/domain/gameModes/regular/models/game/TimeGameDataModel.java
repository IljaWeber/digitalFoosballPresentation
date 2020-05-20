package com.valtech.digitalFoosball.domain.gameModes.regular.models.game;

public class TimeGameDataModel extends RankedGameDataModel {
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
