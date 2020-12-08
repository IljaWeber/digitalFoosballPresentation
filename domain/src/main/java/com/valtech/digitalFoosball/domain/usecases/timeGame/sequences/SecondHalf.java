package com.valtech.digitalFoosball.domain.usecases.timeGame.sequences;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.usecases.timeGame.IPlayATimeGame;
import com.valtech.digitalFoosball.domain.usecases.timeGame.TimeGameRules;

import java.util.Stack;

public class SecondHalf extends PlayHalves implements IPlayATimeGame {

    public SecondHalf(Stack<Team> goalOverview,
                      Stack<Team> undoOverView,
                      TimeGameRules rules) {
        super(rules);
        super.undoOverView = undoOverView;
        super.goalOverView = goalOverview;
    }

    @Override
    protected void finishGameByScoreLimit(Team winnerTeam) {
        IPlayATimeGame endByScoreLimit = new EndByScore(this, rules, winnerTeam);
        rules.setActualTimeGameSequence(endByScoreLimit);
    }

    @Override
    public void timeRanDown() {
        IPlayATimeGame endByTime = new EndByTime(goalOverView, undoOverView);

        rules.setActualTimeGameSequence(endByTime);
    }

    @Override
    public String toString() {
        return "Second Half";
    }
}
