package com.valtech.digitalFoosball.domain.timeGame.iljaRefactoring;

import com.valtech.digitalFoosball.domain.timeGame.TimeGameRules;

public class FirstHalf extends PlayHalves implements IPlayATimeGame {

    public FirstHalf(TimeGameRules timeGameRules) {
        super(timeGameRules);
        timer.schedule(new TimeGameTimerTask(this), 420000);
    }

    @Override
    protected void finishGameByScoreLimit() {
        IPlayATimeGame endByScoreLimit = new EndByScoreLimit(this, rules);
        rules.setActualTimeGameSequence(endByScoreLimit);
    }

    @Override
    public void nextSequenceByTime() {
        IPlayATimeGame halfTime = new HalfTime(goalOverView, rules);

        rules.setActualTimeGameSequence(halfTime);
    }

    @Override
    public void changeover() {

    }

}
