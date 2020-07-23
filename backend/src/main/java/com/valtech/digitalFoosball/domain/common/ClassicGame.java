package com.valtech.digitalFoosball.domain.common;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.models.output.game.GameOutputModel;

public abstract class ClassicGame {
    protected IModifyGames rules;

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

    public void resetMatch() {
        rules.resetMatch();
    }

    public GameOutputModel getGameData() {
        return rules.getPreparedDataForOutput();
    }
}
