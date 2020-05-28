package com.valtech.digitalFoosball.domain.common;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.models.GameDataModel;
import com.valtech.digitalFoosball.domain.common.models.output.team.TeamOutputModel;

import java.util.List;

import static com.valtech.digitalFoosball.domain.common.constants.Team.NO_TEAM;

public abstract class DigitalFoosballGame implements IPlayAGame {
    protected final AbstractInitService initService;
    protected final GameRules gameRules;

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
        Team winner = gameRules.getTeamWithLeadOfTwo(gameDataModel);

        if (winner != NO_TEAM) {
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
