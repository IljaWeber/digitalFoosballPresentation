package com.valtech.digitalFoosball.domain.timeGame.iljaRefactoring;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.timeGame.TimeGameRules;

import java.util.*;

import static com.valtech.digitalFoosball.domain.common.constants.Team.*;

public abstract class PlayHalves {
    protected final Stack<Team> goalOverView;
    protected final Stack<Team> undoOverView;
    public TimeGameRules rules;
    protected Timer timer;

    public PlayHalves(
            TimeGameRules timeGameRules) {
        this.rules = timeGameRules;
        goalOverView = new Stack<>();
        undoOverView = new Stack<>();
        timer = new Timer();
    }

    public void raiseScoreFor(Team team) {
        goalOverView.push(team);

        if (Collections.frequency(goalOverView, team) >= 10) {
            finishGameByScoreLimit(team);
        }
    }

    public void undoLastGoal() {
        if (goalOverView.isEmpty()) {
            return;
        }

        undoOverView.push(goalOverView.pop());
    }

    public void redoLastGoal() {
        if (undoOverView.isEmpty()) {
            return;
        }

        raiseScoreFor(undoOverView.pop());
    }

    public void resetGame() {
        goalOverView.clear();
    }

    public Map<Team, Integer> getScoreOfTeams() {
        Map<Team, Integer> scores = new HashMap<>();

        scores.put(ONE, Collections.frequency(goalOverView, ONE));
        scores.put(TWO, Collections.frequency(goalOverView, TWO));

        return scores;
    }

    protected abstract void finishGameByScoreLimit(Team winnerTeam);

    protected abstract void nextSequenceByTime();

    public Team getMatchWinner() {
        return NO_TEAM;
    }
}
