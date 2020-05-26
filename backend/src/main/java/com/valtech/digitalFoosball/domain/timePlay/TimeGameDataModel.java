package com.valtech.digitalFoosball.domain.timePlay;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.models.BaseGameDataModel;

public class TimeGameDataModel extends BaseGameDataModel {
    private boolean timeIsOver;

    public TimeGameDataModel() {
        timeIsOver = false;
    }

    @Override
    protected void updateObservers() {
        //not implemented yet
    }

    public void timeLimitReached() {
        timeIsOver = true;
    }

    @Override
    public void changeOver() {
        //not implemented yet
    }

    @Override
    public void increaseWonSetsFor(Team team) {

    }

    @Override
    public boolean winConditionFullFilled() {
        return timeIsOver;
    }

    @Override
    public void decreaseWonSetsForRecentSetWinner() {

    }

    @Override
    public Team getSetWinner() {
        return null;
    }

    @Override
    public void setSetWinner(Team setWinner) {

    }

    @Override
    public void resetMatch() {

    }
}
