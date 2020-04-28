package com.valtech.digitalFoosball.service.manager;

import com.valtech.digitalFoosball.constants.Team;
import com.valtech.digitalFoosball.model.GameDataModel;
import com.valtech.digitalFoosball.model.internal.TeamDataModel;
import com.valtech.digitalFoosball.service.histories.History;

import java.util.SortedMap;
import java.util.Timer;

import static com.valtech.digitalFoosball.constants.Team.NO_TEAM;

public class TimeManager {
    private final int GOAL_LIMIT = 10;
    private boolean timeIsOver = false;
    private Timer timer;
    private final History history;

    public TimeManager() {
        history = new History();
    }

    public void setTeams(SortedMap<Team, TeamDataModel> teams) {
    }

    public void setTimer(long timeDuration) {
        timer = new Timer();
        timer.schedule(new TaskOfTimer(this), timeDuration);
    }

    public void countGoalFor(Team team, GameDataModel gameDataModel) {
        TeamDataModel teamDataModel = gameDataModel.getTeam(team);

        if (teamDataModel.getScore() < GOAL_LIMIT && !timeIsOver) {
            history.rememberLastGoalFrom(team);
            teamDataModel.countGoal();
        }
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
}
