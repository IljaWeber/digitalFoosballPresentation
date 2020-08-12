package com.valtech.digitalFoosball.domain.timeGame.sequences;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.timeGame.IPlayATimeGame;
import com.valtech.digitalFoosball.domain.timeGame.TimeGameRules;
import com.valtech.digitalFoosball.domain.timeGame.TimeGameTimerTask;

import java.util.Stack;

public class SecondHalf extends PlayHalves implements IPlayATimeGame {

    public SecondHalf(Stack<Team> goalOverview,
                      TimeGameRules rules) {
        super(rules);
        super.goalOverView = goalOverview;
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
