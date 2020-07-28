package com.valtech.digitalFoosball.domain.timeGame;

import com.valtech.digitalFoosball.domain.common.constants.Team;

import java.util.Collections;
import java.util.Stack;

import static com.valtech.digitalFoosball.domain.timeGame.GameStatus.FIRST_HALF;

public class TimeGameRules {
    private final Stack<Team> goalOverView;
    private final Stack<Team> undoOverView;
    private GameStatus gameStatus;

    public TimeGameRules() {
        goalOverView = new Stack<>();
        undoOverView = new Stack<>();
        gameStatus = FIRST_HALF;
    }

    public void raiseScoreFor(Team team) {
        if (getScoreOfTeam(team) < 10 && gameStatus.isActive()) {
            goalOverView.push(team);
        }
    }

    public int getScoreOfTeam(Team team) {
        if (goalOverView.isEmpty()) {
            return 0;
        }

        return Collections.frequency(goalOverView, team);
    }

    public void undoLastGoal() {
        if (goalOverView.isEmpty()) {
            return;
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

    public void timeExpired() {
        gameStatus = gameStatus.getNext();
    }
}
