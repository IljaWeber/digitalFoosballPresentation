package com.valtech.digitalFoosball.domain.timeGame.sequences;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.timeGame.IPlayATimeGame;
import com.valtech.digitalFoosball.domain.timeGame.TimeGameRules;

import java.util.*;

import static com.valtech.digitalFoosball.domain.common.constants.Team.*;

public abstract class PlayHalves implements IPlayATimeGame {
    protected Stack<Team> goalOverView;
    protected Stack<Team> undoOverView;
    protected TimeGameRules rules;

    public PlayHalves(TimeGameRules timeGameRules) {
        this.rules = timeGameRules;
        goalOverView = new Stack<>();
        undoOverView = new Stack<>();
    }

    public void raiseScoreFor(Team team) {
        goalOverView.push(team);

        if (getScoreOfTeam(team) >= 10) {
            finishGameByScoreLimit(team);
        }
    }

    private int getScoreOfTeam(Team team) {
        return Collections.frequency(goalOverView, team);
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

    public Map<Team, Integer> getScoreOfTeams() {
        Map<Team, Integer> scores = new HashMap<>();

        scores.put(ONE, getScoreOfTeam(ONE));
        scores.put(TWO, getScoreOfTeam(TWO));

        return scores;
    }

    public void changeover() {

    }

    protected abstract void finishGameByScoreLimit(Team winnerTeam);

    public Team getMatchWinner() {
        return NO_TEAM;
    }
}
