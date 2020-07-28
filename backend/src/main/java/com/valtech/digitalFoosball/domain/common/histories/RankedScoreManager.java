package com.valtech.digitalFoosball.domain.common.histories;

import com.valtech.digitalFoosball.domain.common.ClassicScoreManager;
import com.valtech.digitalFoosball.domain.common.constants.Team;

import java.util.Collections;
import java.util.Stack;

import static com.valtech.digitalFoosball.domain.common.constants.Team.NO_TEAM;
import static com.valtech.digitalFoosball.domain.common.constants.Team.values;

public class RankedScoreManager extends ClassicScoreManager {
    private Stack<Team> goalOverView;
    private Stack<Team> undoOverView;
    private Stack<Team> winOverview;
    private Team actualWinner = NO_TEAM;

    public RankedScoreManager() {
        goalOverView = new Stack<>();
        undoOverView = new Stack<>();
        winOverview = new Stack<>();
    }

    public Team getActualWinner() {
        return actualWinner;
    }

    // OPTIMIZE: created on 28.07.20 by iljaweber: reconsider implementation of raiseScoreFor

    public void raiseScoreFor(Team team) {

        if (actualWinner != NO_TEAM) {
            return;
        }

        goalOverView.push(team);
        actualWinner = super.checkForWin(goalOverView);

        if (actualWinner != NO_TEAM) {
            winOverview.push(actualWinner);
        }
    }

    public int getScoreOfTeam(Team team) {
        if (goalOverView.isEmpty()) {
            return 0;
        }

        return Collections.frequency(goalOverView, team);
    }

    public Team getMatchWinner() {
        for (Team team : values()) {
            if (Collections.frequency(winOverview, team) >= 2) {
                return team;
            }
        }

        return NO_TEAM;
    }

    public void undoLastGoal() {
        if (goalOverView.isEmpty()) {
            return;
        }

        if (actualWinner != NO_TEAM) {
            winOverview.pop();
            actualWinner = NO_TEAM;
        }

        Team team = goalOverView.pop();
        undoOverView.push(team);
    }

    public void redoLastGoal() {
        if (undoOverView.isEmpty()) {
            return;
        }

        raiseScoreFor(undoOverView.pop());
    }

    public void changeOver() {
        goalOverView = new Stack<>();
        undoOverView = new Stack<>();
        actualWinner = NO_TEAM;
    }
}
