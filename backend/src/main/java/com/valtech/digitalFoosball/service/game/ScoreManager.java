package com.valtech.digitalFoosball.service.game;

import com.valtech.digitalFoosball.constants.Team;
import com.valtech.digitalFoosball.model.GameDataModel;
import com.valtech.digitalFoosball.model.internal.TeamDataModel;
import com.valtech.digitalFoosball.service.histories.History;
import com.valtech.digitalFoosball.service.verifier.RegularGameSetWinVerifier;

import static com.valtech.digitalFoosball.constants.Team.NO_TEAM;

public class ScoreManager {

    private final RegularGameSetWinVerifier setWinVerifier;

    public ScoreManager() {
        setWinVerifier = new RegularGameSetWinVerifier();
    }

    public void countGoalFor(Team team, GameDataModel gameDataModel) {

        //if setHasWinner == true -> zyklomatische Komplexität reduzieren
        if (setHasNoWinner(gameDataModel)) {
            gameDataModel.countGoalFor(team);

            if (setWinVerifier.teamWon(gameDataModel.getTeams(), team)) {
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
            //feature envy!
            if (gameDataModel.setHasAWinner()) {
                gameDataModel.decreaseWonSetsForRecentSetWinner();
                gameDataModel.setSetWinner(NO_TEAM);
            }
        }
    }

    //substitution of redo and undo
    //solve feature envy -> operations on gdm and tdm
    public void redoGoal(GameDataModel gameDataModel) {
        History history = gameDataModel.getHistory();

        if (history.hasUndoneGoals()) {
            Team team = history.getLastUndoneGoal();
            TeamDataModel teamDataModel = gameDataModel.getTeams().get(team);

            teamDataModel.countGoal();
            history.rememberLastGoalFor(team);

            if (setWinVerifier.teamWon(gameDataModel.getTeams(), team)) {
                teamDataModel.increaseWonSets();
                gameDataModel.setSetWinner(team);
            }
        }
    }
}
