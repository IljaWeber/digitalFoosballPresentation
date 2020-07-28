package com.valtech.digitalFoosball.domain.common;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.models.output.game.ClassicGameOutputModel;
import com.valtech.digitalFoosball.domain.common.models.output.game.GameOutputModel;
import com.valtech.digitalFoosball.domain.ranked.GameDataModel;
import com.valtech.digitalFoosball.domain.ranked.RankedGameRules;

public abstract class ClassicGame {
    protected RankedGameRules rankedGameRules;
    protected GameDataModel model;

    public void countGoalFor(Team team) {
        rankedGameRules.raiseScoreFor(team);
    }

    public void undoGoal() {
        rankedGameRules.undoLastGoal();
    }

    public void redoGoal() {
        rankedGameRules.redoLastGoal();
    }

    public void changeover() {
        rankedGameRules.changeOver();
    }

    public void resetMatch() {
        model.resetMatch();
        rankedGameRules = new RankedGameRules();
    }

    public GameOutputModel getGameData() {
        return new ClassicGameOutputModel(model, rankedGameRules);
    }

}
