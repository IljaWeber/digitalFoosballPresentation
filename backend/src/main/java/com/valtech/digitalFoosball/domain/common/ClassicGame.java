package com.valtech.digitalFoosball.domain.common;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.ranked.RankedGameDataModel;
import com.valtech.digitalFoosball.domain.ranked.RankedGameRules;

import static com.valtech.digitalFoosball.domain.common.constants.Team.NO_TEAM;

public abstract class ClassicGame implements IPlayAGame {
    protected RankedGameDataModel gameDataModel;
    private final RankedGameRules rules;

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
    public RankedGameDataModel getGameData() {
        return gameDataModel;
    }
}
