package com.valtech.digitalFoosball.domain.timeGame;

import com.valtech.digitalFoosball.domain.common.constants.Team;

import java.util.Map;
import java.util.Stack;

public class EndByScoreLimit implements IPlayATimeGame {
    private final IPlayATimeGame previousTimeGameSequence;
    private final TimeGameRules rules;
    private final Stack<Team> goalOverview;

    public EndByScoreLimit(IPlayATimeGame previous, TimeGameRules rules, Stack<Team> goalOverView) {
        this.previousTimeGameSequence = previous;
        this.rules = rules;
        this.goalOverview = goalOverView;
    }

    @Override
    public void raiseScoreFor(Team team) {

    }

    @Override
    public void undoLastGoal() {

    }

    @Override
    public void redoLastGoal() {

    }

    @Override
    public void changeover() {

    }

    @Override
    public void resetGame() {

    }

    @Override
    public Map<Team, Integer> getScoresOfTeams() {
        return null;
    }
}
