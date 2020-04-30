package com.valtech.digitalFoosball.service.game;

import com.valtech.digitalFoosball.constants.Team;
import com.valtech.digitalFoosball.model.GameDataModel;
import com.valtech.digitalFoosball.model.internal.TeamDataModel;
import com.valtech.digitalFoosball.service.histories.History;
import com.valtech.digitalFoosball.service.verifier.RegularGameSetWinVerifier;
import org.springframework.stereotype.Service;

import static com.valtech.digitalFoosball.constants.Team.NO_TEAM;

@Service
public class ScoreManager {
    private final RegularGameSetWinVerifier setWinVerifier;

    public ScoreManager() {
        setWinVerifier = new RegularGameSetWinVerifier();
    }

    public void countGoalFor(Team team, GameDataModel gameDataModel) {
        TeamDataModel teamDataModel = gameDataModel.getTeam(team);
        History history = gameDataModel.getHistory();

        if (setHasNoWinner(gameDataModel)) {
            teamDataModel.countGoal();
            history.rememberLastGoalFrom(team);

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
        History history = gameDataModel.getHistory();

        if (history.thereAreGoals()) {
            Team team = history.getLastScoringTeam();
            TeamDataModel lastScoringTeam = gameDataModel.getTeam(team);

            if (gameDataModel.getSetWinner() != NO_TEAM) {
                lastScoringTeam.decreaseWonSets();
                gameDataModel.setSetWinner(NO_TEAM);
            }

            lastScoringTeam.decreaseScore();

            history.rememberUndoneGoal(team);
        }
    }

    public void redoGoal(GameDataModel gameDataModel) {
        History history = gameDataModel.getHistory();

        if (history.hasUndoneGoals()) {
            Team team = history.getLastUndoneGoal();
            TeamDataModel teamDataModel = gameDataModel.getTeams().get(team);

            teamDataModel.countGoal();
            history.rememberLastGoalFrom(team);

            if (setWinVerifier.teamWon(gameDataModel.getTeams(), team)) {
                teamDataModel.increaseWonSets();
                gameDataModel.setSetWinner(team);
            }
        }
    }
}
