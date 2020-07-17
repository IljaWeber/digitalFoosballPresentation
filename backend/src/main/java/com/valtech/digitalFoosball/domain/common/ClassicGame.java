package com.valtech.digitalFoosball.domain.common;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.ranked.IModifyGames;
import com.valtech.digitalFoosball.domain.ranked.RankedGameDataModel;

public abstract class ClassicGame implements IPlayAGame {
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

    public RankedGameDataModel getGameData() {
        return rules.getGameData();
    }
}
