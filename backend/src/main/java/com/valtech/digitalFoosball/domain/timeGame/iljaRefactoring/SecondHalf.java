package com.valtech.digitalFoosball.domain.timeGame.iljaRefactoring;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.timeGame.TimeGameRules;

public class SecondHalf extends PlayHalves implements IPlayATimeGame {

    public SecondHalf(TimeGameRules rules) {
        super(rules);
        timer.schedule(new TimeGameTimerTask(this), 420000);

    }

    @Override
    public void changeover() {

    }

    @Override
    public void resetGame() {
        goalOverView.clear();
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
}
