package com.valtech.digitalFoosball.domain.adhoc;

import com.valtech.digitalFoosball.domain.common.IPlayAGame;
import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.models.GameDataModel;
import com.valtech.digitalFoosball.domain.ranked.RankedGameDataModel;
import com.valtech.digitalFoosball.domain.ranked.RankedGameRules;

import static com.valtech.digitalFoosball.domain.common.constants.Team.NO_TEAM;

public abstract class ClassicGame implements IPlayAGame {
    protected RankedGameDataModel gameDataModel;
    private RankedGameRules rules;

    public ClassicGame() {
        rules = new RankedGameRules();
    }

    public void countGoalFor(Team team) {
        Team winner = rules.getTeamWithLeadOfTwo(gameDataModel);

        if (winner != NO_TEAM) {
            return;
        }

        gameDataModel.countGoalFor(team);

        rules.approveWin(gameDataModel);
    }

    protected void setGameDataModel(RankedGameDataModel gameDataModel) {
        this.gameDataModel = gameDataModel;
    }

    public void undoGoal() {

        if (gameDataModel.thereAreGoals()) {

            if (rules.winConditionsFulfilled(gameDataModel)) {
                gameDataModel.decreaseWonSetsForRecentSetWinner();
                gameDataModel.setSetWinner(NO_TEAM);
            }

            gameDataModel.undoLastGoal();
        }
    }

    public void redoGoal() {
        if (gameDataModel.thereAreUndoneGoals()) {

            gameDataModel.redoLastUndoneGoal();

            rules.approveWin(gameDataModel);
        }
    }

    public void changeover() {
        gameDataModel.changeOver();
    }

    @Override
    public void resetMatch() {
        gameDataModel.resetMatch();
    }

    @Override
    public GameDataModel getGameData() {
        return gameDataModel;
    }
}
