package com.valtech.digitalFoosball.service.game.modes;

import com.valtech.digitalFoosball.constants.Team;
import com.valtech.digitalFoosball.model.GameDataModel;
import com.valtech.digitalFoosball.model.input.InitDataModel;
import com.valtech.digitalFoosball.model.output.TeamOutput;
import com.valtech.digitalFoosball.service.game.ScoreManager;
import com.valtech.digitalFoosball.service.game.init.AbstractInitService;

import java.util.List;

public abstract class AbstractGameManipulator {
    protected final AbstractInitService initService;
    private final ScoreManager scoreManager;

    public AbstractGameManipulator(AbstractInitService initService) {
        scoreManager = new ScoreManager();
        this.initService = initService;
    }

    public List<TeamOutput> getAllTeamsFromDatabase() {
        return initService.getAllTeams();
    }

    public abstract GameDataModel initGame(InitDataModel initDataModel);

    public void countGoalFor(Team team, GameDataModel gameDataModel) {
        scoreManager.countGoalFor(team, gameDataModel);
    }

    public void undoGoal(GameDataModel gameDataModel) {
        scoreManager.undoGoal(gameDataModel);
    }

    public void redoGoal(GameDataModel gameDataModel) {
        scoreManager.redoGoal(gameDataModel);
    }

    public void changeover(GameDataModel gameDataModel) {
        gameDataModel.changeOver();
    }
}
