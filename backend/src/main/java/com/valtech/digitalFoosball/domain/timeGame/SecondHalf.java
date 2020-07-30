package com.valtech.digitalFoosball.domain.timeGame;

import com.valtech.digitalFoosball.domain.common.constants.Team;

import java.util.Map;
import java.util.Stack;

public class SecondHalf implements IPlayATimeGame {
    private final Stack<Team> goalOverview;
    private final TimeGameRules rules;

    public SecondHalf(Stack<Team> goalOverview, TimeGameRules rules) {
        this.goalOverview = goalOverview;
        this.rules = rules;

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
    public Map<Team, Integer> getScoreOfTeams() {
        return null;
    }
}
