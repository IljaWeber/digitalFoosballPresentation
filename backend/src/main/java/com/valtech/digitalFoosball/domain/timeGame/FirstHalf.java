package com.valtech.digitalFoosball.domain.timeGame;

import com.valtech.digitalFoosball.domain.common.constants.Team;

public class FirstHalf extends PlayHalves implements IPlayATimeGame {

    public FirstHalf(TimeGameRules timeGameRules) {
        super(timeGameRules);
        timer.schedule(new TimeGameTimerTask(this), 420000);
    }

    @Override
    protected void finishGameByScoreLimit(Team winnerTeam) {
        IPlayATimeGame endByScoreLimit = new EndByScoreLimit(this, rules, winnerTeam);
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
