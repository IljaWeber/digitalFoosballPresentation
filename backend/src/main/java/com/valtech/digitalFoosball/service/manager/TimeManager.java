package com.valtech.digitalFoosball.service.manager;

import com.valtech.digitalFoosball.constants.Team;
import com.valtech.digitalFoosball.model.internal.TeamDataModel;

import java.util.SortedMap;
import java.util.Timer;

public class TimeManager {
    private final int GOAL_LIMIT = 10;
    private boolean timeIsOver = false;
    private Timer timer;
    private SortedMap<Team, TeamDataModel> teams;

    public void setTeams(SortedMap<Team, TeamDataModel> teams) {
        this.teams = teams;
    }

    public void setTimer(long timeDuration) {
        timer = new Timer();
        timer.schedule(new TaskOfTimer(this), timeDuration);
    }

    public void countGoalFor(Team team) {
        TeamDataModel teamDataModel = teams.get(team);

        if (teamDataModel.getScore() < GOAL_LIMIT && !timeIsOver) {
            teamDataModel.countGoal();
        }
    }

    public void timeIsOver() {
        timeIsOver = true;
    }
}
