package com.valtech.digitalFoosball.domain.common;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.models.GameDataModel;
import com.valtech.digitalFoosball.domain.common.models.output.team.TeamOutputModel;

import java.util.List;

public abstract class DigitalFoosballGame implements IPlayAGame {
    protected final AbstractInitService initService;
    private final GameRules gameRules;

    public DigitalFoosballGame(AbstractInitService initService,
                               GameRules gameRules) {
        this.initService = initService;
        this.gameRules = gameRules;
    }

    @Override
    public List<TeamOutputModel> getAllTeamsFromDatabase() {
        return initService.getAllTeams();
    }

    @Override
    public void countGoalFor(Team team, GameDataModel gameDataModel) {
        if (gameDataModel.setHasAWinner()) {
            return;
        }

        gameDataModel.countGoalFor(team);

        gameRules.approveWin(gameDataModel);
    }

    @Override
    public void redoGoal(GameDataModel gameDataModel) {
        if (gameDataModel.thereAreUndoneGoals()) {

            gameDataModel.redoLastUndoneGoal();

            gameRules.approveWin(gameDataModel);
        }
    }

    @Override
    public void changeover(GameDataModel gameDataModel) {
        gameDataModel.changeOver();
    }
}
