package com.valtech.digitalFoosball.domain.ranked;

import com.valtech.digitalFoosball.domain.common.BaseGameRules;
import com.valtech.digitalFoosball.domain.common.constants.Team;

import static com.valtech.digitalFoosball.domain.common.constants.Team.NO_TEAM;

public class RankedGameRules extends BaseGameRules {

    private final RankedGameDataModel gameDataModel;

    public RankedGameRules(RankedGameDataModel gameDataModel) {
        this.gameDataModel = gameDataModel;
    }

    @Override
    public void approveWin(RankedGameDataModel gameDataModel) {
        Team winner = getTeamWithLeadOfTwo(gameDataModel);

        if (winner != NO_TEAM) {
            gameDataModel.increaseWonSetsFor(winner);
            gameDataModel.setSetWinner(winner);
        }
    }

    @Override
    public boolean winConditionsFulfilled(RankedGameDataModel gameDataModel) {
        Team setWinner = gameDataModel.getSetWinner();

        return setWinner != NO_TEAM;
    }

    @Override
    public void raiseScoreFor(Team team) {

        if (gameDataModel.getSetWinner() == NO_TEAM) {
            gameDataModel.countGoalFor(team);
        }

        approveWinOfSet();
    }

    private void approveWinOfSet() {
        Team winner = getTeamWithLeadOfTwo(gameDataModel);

        if (winner != NO_TEAM) {
            gameDataModel.increaseWonSetsFor(winner);
            gameDataModel.setSetWinner(winner);
        }
    }

    public void undoLastGoal() {
        if (gameDataModel.thereAreGoals()) {

            if (winConditionsFulfilled(gameDataModel)) {
                gameDataModel.decreaseWonSetsForRecentSetWinner();
                gameDataModel.setSetWinner(NO_TEAM);
            }

            gameDataModel.undoLastGoal();
        }
    }

    public void redoGoal() {
        if (gameDataModel.thereAreUndoneGoals()) {

            gameDataModel.redoLastUndoneGoal();

            approveWinOfSet();
        }
    }

    public void changeOver() {
        gameDataModel.changeOver();
    }

    public void resetMatch() {
        gameDataModel.resetMatch();
    }

    public RankedGameDataModel getGameData() {
        return gameDataModel;
    }
}
