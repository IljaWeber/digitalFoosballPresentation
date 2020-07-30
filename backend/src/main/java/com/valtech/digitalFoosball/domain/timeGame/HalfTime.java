package com.valtech.digitalFoosball.domain.timeGame;

import com.valtech.digitalFoosball.domain.common.constants.Team;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import static com.valtech.digitalFoosball.domain.common.constants.Team.ONE;
import static com.valtech.digitalFoosball.domain.common.constants.Team.TWO;

public class HalfTime implements IPlayATimeGame {
    private final Stack<Team> goalOverview;
    private final TimeGameRules rules;

    public HalfTime(Stack<Team> goalOverView, TimeGameRules rules) {
        this.goalOverview = goalOverView;
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
        IPlayATimeGame secondHalf = new SecondHalf(goalOverview, rules);
        rules.setActualTimeGameSequence(secondHalf);
    }

    @Override
    public void resetGame() {

    }

    @Override
    public Map<Team, Integer> getScoreOfTeams() {
        Map<Team, Integer> scores = new HashMap<>();

        scores.put(ONE, Collections.frequency(goalOverview, ONE));
        scores.put(TWO, Collections.frequency(goalOverview, TWO));

        return scores;
    }
}
