package com.valtech.digitalFoosball.domain.ranked;

import com.valtech.digitalFoosball.domain.common.IModifyGames;
import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.histories.RankedScoreManager;
import com.valtech.digitalFoosball.domain.common.models.output.game.ClassicGameOutputModel;
import com.valtech.digitalFoosball.domain.common.models.output.game.GameOutputModel;

public class RankedGameRules implements IModifyGames {
    private final GameDataModel model;
    private RankedScoreManager rankedScoreManager;

    public RankedGameRules(RankedGameDataModel gameDataModel) {
        this.model = gameDataModel;
        rankedScoreManager = new RankedScoreManager();
    }

    public void raiseScoreFor(Team team) {
        rankedScoreManager.raiseScoreFor(team);
    }

    public void undoLastGoal() {
        rankedScoreManager.undoLastGoal();
    }

    public void redoGoal() {
        rankedScoreManager.redoLastGoal();
    }

    public void changeOver() {
        rankedScoreManager.changeOver();
    }

    public void resetMatch() {
        model.resetMatch();
        rankedScoreManager = new RankedScoreManager();
    }

    @Override
    public GameOutputModel getPreparedDataForOutput() {
        return new ClassicGameOutputModel(model, rankedScoreManager);
    }
}
