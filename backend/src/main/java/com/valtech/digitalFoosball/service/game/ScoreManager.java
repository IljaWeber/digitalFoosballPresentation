package com.valtech.digitalFoosball.service.game;

import com.valtech.digitalFoosball.constants.Team;
import com.valtech.digitalFoosball.model.GameDataModel;
import com.valtech.digitalFoosball.service.verifier.setwin.WonSetVerifier;

import static com.valtech.digitalFoosball.constants.Team.NO_TEAM;

public class ScoreManager {
    private WonSetVerifier verifier;

    public ScoreManager() {
    }

    public void countGoalFor(Team team, GameDataModel gameDataModel) {
        //if setHasWinner == true -> zyklomatische Komplexität reduzieren
        if (setHasNoWinner(gameDataModel)) {
            gameDataModel.countGoalFor(team);

            if (gameDataModel.hasWonSet()) {
                gameDataModel.increaseWonSetsFor(team);
                gameDataModel.setSetWinner(team);
            }
        }
    }

    private boolean setHasNoWinner(GameDataModel gameDataModel) {
        Team setWinner = gameDataModel.getSetWinner();
        return setWinner == NO_TEAM;
    }

    public void undoGoal(GameDataModel gameDataModel) {
        if (gameDataModel.checkForExistingGoals()) {
            gameDataModel.decreaseScoreForLastScoredTeam();
            // extract to method
            if (gameDataModel.isThereASetWinner()) {
                gameDataModel.decreaseWonSetsForRecentSetWinner();
                gameDataModel.setSetWinner(NO_TEAM);
            }
        }
    }

    //substitution of redo and undo
    //solve feature envy -> operations on gdm and tdm
    public void redoGoal(GameDataModel gameDataModel) {
        if (gameDataModel.checkForUndoneGoals()) {
            gameDataModel.increaseScoreForLastUndoneTeam();

            if (gameDataModel.hasWonSet()) {
                gameDataModel.setWonSetWithRecentUndoneTeam();
            }
        }
    }
}
