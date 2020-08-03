package com.valtech.digitalFoosball.domain.timeGame;

import com.valtech.digitalFoosball.domain.common.IKnowTheRules;
import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.timeGame.iljaRefactoring.FirstHalf;
import com.valtech.digitalFoosball.domain.timeGame.iljaRefactoring.IPlayATimeGame;

import java.util.Collections;
import java.util.Stack;
import java.util.Timer;

import static com.valtech.digitalFoosball.domain.common.constants.Team.*;
import static com.valtech.digitalFoosball.domain.timeGame.GameState.FIRST_HALF;
import static com.valtech.digitalFoosball.domain.timeGame.GameState.OVER;

public class TimeGameRules implements IKnowTheRules {
    public static final int halftimeInMillis = 420000;
    private final Stack<Team> goalOverView;
    private final Stack<Team> undoOverView;
    private GameState gameState;
    private final Timer timer;

    private IPlayATimeGame actualGameSequence;

    public TimeGameRules() {
        goalOverView = new Stack<>();
        undoOverView = new Stack<>();
        gameState = FIRST_HALF;
        timer = new Timer();
        actualGameSequence = new FirstHalf(this);
        startTimer();
    }

    // TODO: 28.07.20 m.huber think of a way to mock the timer to test this method
    public void startTimer() {
        timer.schedule(new HalftimeTimerTask(this), halftimeInMillis);
    }

    @Override
    public void raiseScoreFor(Team team) {
        if (gameState.isActive()) {
            goalOverView.push(team);
        }

        determineWinner();
    }

    @Override
    public void undoLastGoal() {
        if (goalOverView.isEmpty()) {
            return;
        }

        Team team = goalOverView.pop();
        undoOverView.push(team);
    }

    @Override
    public void redoLastGoal() {
        if (undoOverView.isEmpty()) {
            return;
        }

        raiseScoreFor(undoOverView.pop());
    }

    @Override
    public void changeOver() {
        gameState = gameState.getNext();
        startTimer();
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

    public void timeRanDown() {
        gameState = gameState.getNext();
    }

    public void raise(Team team) {
        actualGameSequence.raiseScoreFor(team);
    }

    public void setActualTimeGameSequence(IPlayATimeGame gameSequence) {
        actualGameSequence = gameSequence;
    }

    public IPlayATimeGame getActualGameSequence() {
        return actualGameSequence;
    }

    public void undo() {
        actualGameSequence.undoLastGoal();
    }

    public void redo() {
        actualGameSequence.redoLastGoal();
    }

    public void changeover() {
        actualGameSequence.changeover();
    }

    public Team getMatchWinner() {
        return actualGameSequence.getMatchWinner();
    }
}
