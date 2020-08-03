package com.valtech.digitalFoosball.domain.timeGame;

import com.valtech.digitalFoosball.domain.common.constants.Team;

public class SecondHalf extends PlayHalves implements IPlayATimeGame {

    public SecondHalf(TimeGameRules rules) {
        super(rules);
        timer.schedule(new TimeGameTimerTask(this), 420000);

    }

    @Override
    protected void finishGameByScoreLimit(Team winnerTeam) {
        IPlayATimeGame endByScoreLimit = new EndByScoreLimit(this, rules, winnerTeam);
        rules.setActualTimeGameSequence(endByScoreLimit);
    }

    public void nextSequenceByTime() {
        IPlayATimeGame endByTime = new EndByTime(goalOverView);

        rules.setActualTimeGameSequence(endByTime);
    }

    @Override
    public String toString() {
        return "Second Half";
    }
}
