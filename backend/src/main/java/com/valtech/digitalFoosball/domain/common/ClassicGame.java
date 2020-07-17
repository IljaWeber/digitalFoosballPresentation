package com.valtech.digitalFoosball.domain.common;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.ranked.RankedGameDataModel;
import com.valtech.digitalFoosball.domain.ranked.RankedGameRules;

public abstract class ClassicGame implements IPlayAGame {
    protected RankedGameRules rules;

    public void countGoalFor(Team team) {
        rules.raiseScoreFor(team);
    }

    public void undoGoal() {
        rules.undoLastGoal();
    }

    public void redoGoal() {
        rules.redoGoal();
    }

    public void changeover() {
        rules.changeOver();
    }

    @Override
    public void resetMatch() {
        rules.resetMatch();

    }

    @Override
    public RankedGameDataModel getGameData() {
        return rules.getGameData();
    }
}
