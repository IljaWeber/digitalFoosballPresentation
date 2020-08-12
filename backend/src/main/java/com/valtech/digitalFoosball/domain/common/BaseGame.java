package com.valtech.digitalFoosball.domain.common;

import com.valtech.digitalFoosball.domain.IKnowTheRules;
import com.valtech.digitalFoosball.domain.IPlayAGame;
import com.valtech.digitalFoosball.domain.common.constants.Team;

public abstract class BaseGame<T extends IKnowTheRules> implements IPlayAGame {
    protected T gameRules;

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

    public void setGameRules(T gameRules) {
        this.gameRules = gameRules;
    }
}