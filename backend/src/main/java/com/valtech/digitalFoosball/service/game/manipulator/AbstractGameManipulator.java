package com.valtech.digitalFoosball.service.game.manipulator;

import com.valtech.digitalFoosball.constants.Team;
import com.valtech.digitalFoosball.model.input.InitDataModel;
import com.valtech.digitalFoosball.model.internal.GameDataModel;
import com.valtech.digitalFoosball.model.output.TeamOutput;
import com.valtech.digitalFoosball.service.game.init.AbstractInitService;
import com.valtech.digitalFoosball.service.verifier.SetWinApprover;

import java.util.List;

import static com.valtech.digitalFoosball.constants.Team.NO_TEAM;

public abstract class AbstractGameManipulator {
    protected final AbstractInitService initService;
    private final SetWinApprover setWinApprover;

    public AbstractGameManipulator(AbstractInitService initService,
                                   SetWinApprover setWinApprover) {
        this.initService = initService;
        this.setWinApprover = setWinApprover;
    }

    public List<TeamOutput> getAllTeamsFromDatabase() {
        return initService.getAllTeams();
    }

    public abstract GameDataModel initGame(InitDataModel initDataModel);

    public void countGoalFor(Team team, GameDataModel gameDataModel) {
        gameDataModel.countGoalFor(team);

        setWinApprover.approveWin(gameDataModel);
    }

    public void undoGoal(GameDataModel gameDataModel) {
        if (gameDataModel.thereAreGoals()) {

            if (gameDataModel.setHasAWinner()) {
                gameDataModel.decreaseWonSetsForRecentSetWinner();
                gameDataModel.setSetWinner(NO_TEAM);
            }

            gameDataModel.undoLastGoal();
        }
    }

    public void redoGoal(GameDataModel gameDataModel) {
        if (gameDataModel.thereAreUndoneGoals()) {

            gameDataModel.redoLastUndoneGoal();

            setWinApprover.approveWin(gameDataModel);
        }
    }

    public void changeover(GameDataModel gameDataModel) {
        gameDataModel.changeOver();
    }
}
