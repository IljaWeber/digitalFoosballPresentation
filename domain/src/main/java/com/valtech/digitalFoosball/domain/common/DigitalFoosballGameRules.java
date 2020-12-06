package com.valtech.digitalFoosball.domain.common;

import com.valtech.digitalFoosball.domain.IInitializeGames;
import com.valtech.digitalFoosball.domain.IPlayAGame;
import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.models.GameDataModel;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import com.valtech.digitalFoosball.domain.common.models.output.game.ClassicGameOutputModel;
import com.valtech.digitalFoosball.domain.common.models.output.game.GameOutputModel;

public class DigitalFoosballGameRules implements IPlayAGame {
    private IInitializeGames initService;
    private GameDataModel model;

    public DigitalFoosballGameRules(IInitializeGames initService) {
        this.initService = initService;
    }

    @Override
    public void initGame(InitDataModel initDataModel) {
        model = initService.init(initDataModel);
    }

    @Override
    public void resetMatch() {
        model.resetMatch();
    }

    @Override
    public GameOutputModel getGameData() {
        return new ClassicGameOutputModel(model);
    }

    @Override
    public void setGameDataModel(GameDataModel gameDataModel) {
        this.model = gameDataModel;
    }

    @Override
    public void countGoalFor(Team team) {
        model.raiseScoreFor(team);
    }

    @Override
    public void undoGoal() {
        model.undoLastGoal();
    }

    @Override
    public void redoGoal() {
        model.redoLastGoal();
    }

    @Override
    public void changeover() {
        model.changeOver();
    }
}
