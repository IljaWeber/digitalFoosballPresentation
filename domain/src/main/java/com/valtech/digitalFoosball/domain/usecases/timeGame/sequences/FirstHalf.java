package com.valtech.digitalFoosball.domain.usecases.timeGame.sequences;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.usecases.timeGame.IPlayATimeGame;
import com.valtech.digitalFoosball.domain.usecases.timeGame.TimeGameRules;

public class FirstHalf extends PlayHalves {

    public FirstHalf(TimeGameRules timeGameRules) {
        super(timeGameRules);
    }

    @Override
    protected void finishGameByScoreLimit(Team winnerTeam) {
        IPlayATimeGame endByScoreLimit = new EndByScore(this, rules, winnerTeam);
        rules.setActualTimeGameSequence(endByScoreLimit);
    }

    @Override
    public void timeRanDown() {
        IPlayATimeGame halfTime = new HalfTime(goalOverView, undoOverView, rules);

        rules.setActualTimeGameSequence(halfTime);
    }

    @Override
    public String toString() {
        return "First Half";
    }
}
