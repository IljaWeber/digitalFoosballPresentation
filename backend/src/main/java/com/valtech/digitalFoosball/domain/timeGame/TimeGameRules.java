package com.valtech.digitalFoosball.domain.timeGame;

import com.valtech.digitalFoosball.domain.common.constants.Team;

import java.util.Collections;
import java.util.Stack;

import static com.valtech.digitalFoosball.domain.common.constants.Team.*;
import static com.valtech.digitalFoosball.domain.timeGame.GameState.FIRST_HALF;
import static com.valtech.digitalFoosball.domain.timeGame.GameState.OVER;

public class TimeGameRules {
    private final Stack<Team> goalOverView;
    private final Stack<Team> undoOverView;
    private GameState gameState;

    public TimeGameRules() {
        goalOverView = new Stack<>();
        undoOverView = new Stack<>();
        gameState = FIRST_HALF;
    }

    public void raiseScoreFor(Team team) {
        if (gameState.isActive()) {
            goalOverView.push(team);
        }

        determineWinner();
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
        gameState = gameState.getNext();
    }

    public GameState getGameState() {
        return gameState;
    }

    public Team determineWinner() {
        Team leadingTeam = getLeadingTeam();
        Team winner = NO_TEAM;

        if (gameState == OVER) {
            winner = leadingTeam;
        }

        if (getScoreOfTeam(leadingTeam) >= 10) {
            gameState = OVER;
            winner = leadingTeam;
        }

        return winner;
    }

    private Team getLeadingTeam() {
        if (isLeading(ONE)) {
            return ONE;
        }

        if (isLeading(TWO)) {
            return TWO;
        }

        return NO_TEAM;
    }

    private boolean isLeading(Team team) {
        Team opponent = team.getOpponent();
        return getScoreOfTeam(team) > getScoreOfTeam(opponent);
    }

    public int getScoreOfTeam(Team team) {
        if (goalOverView.isEmpty()) {
            return 0;
        }

        return Collections.frequency(goalOverView, team);
    }
}
