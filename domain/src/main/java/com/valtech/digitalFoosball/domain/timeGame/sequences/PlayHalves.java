package com.valtech.digitalFoosball.domain.timeGame.sequences;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.timeGame.IPlayATimeGame;
import com.valtech.digitalFoosball.domain.timeGame.TimeGameRules;
import com.valtech.digitalFoosball.domain.timeGame.service.MatchScores;
import com.valtech.digitalFoosball.domain.timeGame.service.ScoreConverter;

import java.util.Collections;
import java.util.Stack;

import static com.valtech.digitalFoosball.domain.common.constants.Team.NO_TEAM;

public abstract class PlayHalves implements IPlayATimeGame {
    private final int GOAL_LIMIT;
    protected Stack<Team> goalOverView;
    protected Stack<Team> undoOverView;
    protected TimeGameRules rules;

    public PlayHalves(TimeGameRules timeGameRules) {
        this.rules = timeGameRules;
        goalOverView = new Stack<>();
        undoOverView = new Stack<>();
        GOAL_LIMIT = 10;
    }

    public void raiseScoreFor(Team team) {
        goalOverView.push(team);

        if (getScoreOfTeam(team) >= GOAL_LIMIT) {
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

    public MatchScores getMatchScores() {
        return ScoreConverter.convert(goalOverView);
    }

    public void changeover() {

    }

    protected abstract void finishGameByScoreLimit(Team winnerTeam);

    public Team getMatchWinner() {
        return NO_TEAM;
    }
}
