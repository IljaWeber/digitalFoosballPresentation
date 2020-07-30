package com.valtech.digitalFoosball.domain.timeGame;

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
    protected void finishGameByScoreLimit() {
        IPlayATimeGame endByScoreLimit = new EndByScoreLimit(this, rules);
        rules.setActualTimeGameSequence(endByScoreLimit);
    }

    public void nextSequenceByTime() {
        IPlayATimeGame endByTime = new EndByTime(goalOverView);

        rules.setActualTimeGameSequence(endByTime);
    }
}
