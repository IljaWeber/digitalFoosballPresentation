package com.valtech.digitalFoosball.domain.timeGame.sequences;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.timeGame.IPlayATimeGame;
import com.valtech.digitalFoosball.domain.timeGame.TimeGameRules;

import java.util.Stack;

public class SecondHalf extends PlayHalves implements IPlayATimeGame {

    public SecondHalf(Stack<Team> goalOverview,
                      TimeGameRules rules) {
        super(rules);
        super.goalOverView = goalOverview;
    }

    @Override
    protected void finishGameByScoreLimit(Team winnerTeam) {
        IPlayATimeGame endByScoreLimit = new EndByScoreLimit(this, rules, winnerTeam);
        rules.setActualTimeGameSequence(endByScoreLimit);
    }

    public void timeRanDown() {
        IPlayATimeGame endByTime = new EndByTime(goalOverView);

        rules.setActualTimeGameSequence(endByTime);
    }

    @Override
    public String toString() {
        return "Second Half";
    }
}
