package com.valtech.digitalFoosball.service.game.modes;

import com.valtech.digitalFoosball.constants.Team;
import com.valtech.digitalFoosball.model.GameDataModel;
import com.valtech.digitalFoosball.model.input.InitDataModel;
import com.valtech.digitalFoosball.model.internal.TeamDataModel;
import com.valtech.digitalFoosball.model.output.TeamOutput;
import com.valtech.digitalFoosball.service.game.TaskOfTimer;
import com.valtech.digitalFoosball.service.histories.History;
import com.valtech.digitalFoosball.service.verifier.TimeGameSetWinVerifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Timer;

import static com.valtech.digitalFoosball.constants.Team.NO_TEAM;

@Service
public class TimeGame implements Game {
    private final int GOAL_LIMIT = 10;
    private boolean timeIsOver = false;
    private Timer timer;
    private final History history;
    private final TimeGameSetWinVerifier timeGameSetWinVerifier;

    public TimeGame() {
        history = new History();
        timeGameSetWinVerifier = new TimeGameSetWinVerifier();
    }

    public void setTimer(long timeDuration) {
        timer = new Timer();
        timer.schedule(new TaskOfTimer(this), timeDuration);
    }

    @Override
    public List<TeamOutput> getAllTeamsFromDatabase() {
        return null;
    }

    @Override
    public GameDataModel initGame(InitDataModel initDataModel) {
        return null;
    }

    public void countGoalFor(Team team, GameDataModel gameDataModel) {
        TeamDataModel teamDataModel = gameDataModel.getTeam(team);

        if (timeIsOver || teamDataModel.getScore() >= GOAL_LIMIT) {
            return;
        }

        history.rememberLastGoalFrom(team);
        teamDataModel.countGoal();
    }

    public void timeIsOver() {
        timeIsOver = true;
    }

    public void undoGoal(GameDataModel gameDataModel) {
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
        if (history.hasUndoneGoals()) {
            Team team = history.getLastUndoneGoal();
            TeamDataModel teamDataModel = gameDataModel.getTeams().get(team);

            teamDataModel.countGoal();
            history.rememberLastGoalFrom(team);

            if (timeGameSetWinVerifier.teamWon(gameDataModel.getTeams(), team)) {
                teamDataModel.increaseWonSets();
                gameDataModel.setSetWinner(team);
            }
        }
    }

    @Override
    public void changeover(GameDataModel gameDataModel) {

    }
}
