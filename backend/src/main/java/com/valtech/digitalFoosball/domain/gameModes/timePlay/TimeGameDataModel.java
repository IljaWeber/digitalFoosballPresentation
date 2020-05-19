package com.valtech.digitalFoosball.domain.gameModes.timePlay;

import com.valtech.digitalFoosball.domain.gameModes.regular.models.game.BaseGameDataModel;

public class TimeGameDataModel extends BaseGameDataModel {
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
