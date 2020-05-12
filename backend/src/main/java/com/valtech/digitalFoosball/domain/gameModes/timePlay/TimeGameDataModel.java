package com.valtech.digitalFoosball.domain.gameModes.timePlay;

import com.valtech.digitalFoosball.domain.gameModes.regular.models.RegularGameDataModel;

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
