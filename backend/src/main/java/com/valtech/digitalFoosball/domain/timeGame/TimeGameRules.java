package com.valtech.digitalFoosball.domain.timeGame;

import com.valtech.digitalFoosball.domain.common.constants.Team;

import java.util.Collections;
import java.util.Stack;

public class TimeGameRules {
    private final Stack<Team> goalOverView;

    public TimeGameRules() {
        goalOverView = new Stack<>();
    }

    public void raiseScoreFor(Team team) {
        if (getScoreOfTeam(team) < 10) {
            goalOverView.push(team);
        }
    }

    public int getScoreOfTeam(Team team) {
        if (goalOverView.isEmpty()) {
            return 0;
        }

        return Collections.frequency(goalOverView, team);
    }
}
