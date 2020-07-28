package com.valtech.digitalFoosball.domain.timeGame;

import com.valtech.digitalFoosball.domain.common.constants.Team;

import java.util.Collections;
import java.util.Stack;

import static com.valtech.digitalFoosball.domain.common.constants.Team.*;
import static com.valtech.digitalFoosball.domain.timeGame.GameSequence.FIRST_HALF;
import static com.valtech.digitalFoosball.domain.timeGame.GameSequence.OVER;

public class TimeGameRules {
    private final Stack<Team> goalOverView;
    private final Stack<Team> undoOverView;
    private GameSequence gameSequence;

    public TimeGameRules() {
        goalOverView = new Stack<>();
        undoOverView = new Stack<>();
        gameSequence = FIRST_HALF;
    }

    public void raiseScoreFor(Team team) {
        if (getScoreOfTeam(team) < 10 && gameSequence.isActive()) {
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

    public void startNextGameSequence() {
        gameSequence = gameSequence.getNext();
    }

    public GameSequence getGameSequence() {
        return gameSequence;
    }

    public Team getMatchWinner() {
        if (gameSequence == OVER) {
            if (isLeading(ONE)) {
                return ONE;
            }

            if (isLeading(TWO)) {
                return TWO;
            }
        }

        for (Team team : Team.values()) {
            if (Collections.frequency(goalOverView, team) >= 10) {
                return team;
            }
        }

        return NO_TEAM;
    }

    private boolean isLeading(Team team) {
        Team opponent = team.getOpponent();
        return getScoreOfTeam(team) > getScoreOfTeam(opponent);
    }
}
