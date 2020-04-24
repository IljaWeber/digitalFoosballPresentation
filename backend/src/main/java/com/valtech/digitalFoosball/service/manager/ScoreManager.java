package com.valtech.digitalFoosball.service.manager;

import com.valtech.digitalFoosball.constants.Team;
import com.valtech.digitalFoosball.model.GameDataModel;
import com.valtech.digitalFoosball.model.internal.TeamDataModel;
import com.valtech.digitalFoosball.service.verifier.SetWinVerifier;
import org.springframework.stereotype.Service;

import static com.valtech.digitalFoosball.constants.Team.NO_TEAM;

@Service
public class ScoreManager {
    private final SetWinVerifier setWinVerifier;

    public ScoreManager() {
        setWinVerifier = new SetWinVerifier();
    }

    public void countGoalFor(Team team, GameDataModel gameDataModel) {
        TeamDataModel teamDataModel = gameDataModel.getTeams().get(team);

        if (setHasNoWinner(gameDataModel)) {
            teamDataModel.countGoal();
            gameDataModel.rememberLastGoalFrom(team);

            if (setWinVerifier.teamWon(gameDataModel.getTeams(), team)) {
                teamDataModel.increaseWonSets();
                gameDataModel.setSetWinner(team);
            }
        }
    }

    private boolean setHasNoWinner(GameDataModel gameDataModel) {
        Team setWinner = gameDataModel.getSetWinner();
        return setWinner == NO_TEAM;
    }

    public void undoGoal(GameDataModel gameDataModel) {
        if (gameDataModel.thereAreGoals()) {
            Team team = gameDataModel.getLastScoringTeam();
            TeamDataModel lastScoringTeam = gameDataModel.getTeam(team);

            if (gameDataModel.getSetWinner() != NO_TEAM) {
                lastScoringTeam.decreaseWonSets();
                gameDataModel.setSetWinner(NO_TEAM);
            }

            lastScoringTeam.decreaseScore();

            gameDataModel.rememberUndoneGoal(team);
        }
    }

    public void redoGoal(GameDataModel gameDataModel) {
        if (gameDataModel.hasUndoneGoals()) {
            Team team = gameDataModel.getLastUndoneGoal();
            TeamDataModel teamDataModel = gameDataModel.getTeams().get(team);

            teamDataModel.countGoal();
            gameDataModel.rememberLastGoalFrom(team);

            if (setWinVerifier.teamWon(gameDataModel.getTeams(), team)) {
                teamDataModel.increaseWonSets();
                gameDataModel.setSetWinner(team);
            }
        }
    }
}
