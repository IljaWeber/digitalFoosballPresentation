package com.valtech.digitalFoosball.domain.common;

import com.valtech.digitalFoosball.domain.IKnowTheRules;
import com.valtech.digitalFoosball.domain.IPlayAGame;
import com.valtech.digitalFoosball.domain.common.constants.Team;

public abstract class BaseGame implements IPlayAGame {
    private IKnowTheRules gameRules;

    public void countGoalFor(Team team) {
        gameRules.raiseScoreFor(team);
    }

    public void undoGoal() {
        gameRules.undoLastGoal();
    }

    public void redoGoal() {
        gameRules.redoLastGoal();
    }

    public void changeover() {
        gameRules.changeOver();
    }

    protected void setGameRules(IKnowTheRules gameRules) {
        this.gameRules = gameRules;
    }
}
