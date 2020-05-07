package com.valtech.digitalFoosball.service.game;

import com.valtech.digitalFoosball.constants.Team;
import com.valtech.digitalFoosball.model.GameDataModel;
import com.valtech.digitalFoosball.service.verifier.setwin.GameSetVerifier;

import static com.valtech.digitalFoosball.constants.Team.NO_TEAM;

public class ScoreManager {

    private final GameSetVerifier gameSetWinVerifier;

    public ScoreManager(GameSetVerifier rankedGameSetWinVerifier) {
        this.gameSetWinVerifier = rankedGameSetWinVerifier;
    }

    public void countGoalFor(Team team, GameDataModel gameDataModel) {
        gameDataModel.countGoalFor(team);

        gameSetWinVerifier.approveWin(gameDataModel);
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

            gameSetWinVerifier.approveWin(gameDataModel);
        }
    }
}
