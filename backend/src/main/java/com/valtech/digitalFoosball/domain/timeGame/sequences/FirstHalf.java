package com.valtech.digitalFoosball.domain.timeGame.sequences;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.timeGame.IPlayATimeGame;
import com.valtech.digitalFoosball.domain.timeGame.TimeGameRules;
import com.valtech.digitalFoosball.domain.timeGame.TimeGameTimerTask;

public class FirstHalf extends PlayHalves implements IPlayATimeGame {

    public FirstHalf(TimeGameRules timeGameRules) {
        super(timeGameRules);
        timer.schedule(new TimeGameTimerTask(this), 4200000);
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
    public String toString() {
        return "First Half";
    }
}
