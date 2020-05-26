package com.valtech.digitalFoosball.domain.gameModes.manipulators;

import com.valtech.digitalFoosball.domain.constants.Team;
import com.valtech.digitalFoosball.domain.gameModes.models.output.team.TeamOutputModel;
import com.valtech.digitalFoosball.domain.gameModes.regular.models.game.GameDataModel;
import com.valtech.digitalFoosball.domain.gameModes.winConditionApprover.GameRules;

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
