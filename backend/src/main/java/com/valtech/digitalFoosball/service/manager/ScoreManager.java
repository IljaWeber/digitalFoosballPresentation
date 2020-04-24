package com.valtech.digitalFoosball.service.manager;

import com.valtech.digitalFoosball.constants.Team;
import com.valtech.digitalFoosball.model.GameDataModel;
import com.valtech.digitalFoosball.model.internal.TeamDataModel;
import com.valtech.digitalFoosball.service.verifier.SetWinVerifier;
import org.springframework.stereotype.Service;

@Service
public class ScoreManager {
    private final SetWinVerifier setWinVerifier;
    private Team setWinner;

    public ScoreManager() {
        setWinVerifier = new SetWinVerifier();
        setWinner = Team.NO_TEAM;
    }

    void resetOldGameValues() {
        setWinner = Team.NO_TEAM;
    }

    public void countGoalFor(Team team, GameDataModel gameDataModel) {
        TeamDataModel teamDataModel = gameDataModel.getTeams().get(team);

        if (setHasNoWinner()) {
            teamDataModel.countGoal();
            gameDataModel.getGoalHistory().rememberLastGoalFrom(team);

            if (setWinVerifier.teamWon(gameDataModel.getTeams(), team)) {
                teamDataModel.increaseWonSets();
                setWinner = team;
            }
        }
    }

    private boolean setHasNoWinner() {
        return setWinner == Team.NO_TEAM;
    }

    public void undoGoal(GameDataModel gameDataModel) {
        if (gameDataModel.getGoalHistory().thereAreGoals()) {
            Team team = gameDataModel.getGoalHistory().removeOneGoalFromHistory();
            TeamDataModel lastScoringTeam = gameDataModel.getTeams().get(team);

            if (setWinner != Team.NO_TEAM) {
                lastScoringTeam.decreaseWonSets();
                setWinner = Team.NO_TEAM;
            }

            lastScoringTeam.decreaseScore();

            gameDataModel.getUndoHistory().rememberUndoneGoal(team);
        }
    }

    public void redoGoal(GameDataModel gameDataModel) {
        if (gameDataModel.getUndoHistory().hasUndoneGoals()) {
            Team team = gameDataModel.getUndoHistory().removeUndoneGoal();
            TeamDataModel teamDataModel = gameDataModel.getTeams().get(team);

            teamDataModel.countGoal();
            gameDataModel.getGoalHistory().rememberLastGoalFrom(team);

            if (setWinVerifier.teamWon(gameDataModel.getTeams(), team)) {
                teamDataModel.increaseWonSets();
                setWinner = team;
            }
        }
    }

    public Team getSetWinner() {
        return setWinner;
    }
}
