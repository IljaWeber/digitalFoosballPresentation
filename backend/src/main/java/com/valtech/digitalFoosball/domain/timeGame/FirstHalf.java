package com.valtech.digitalFoosball.domain.timeGame;

import com.valtech.digitalFoosball.domain.common.constants.Team;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import static com.valtech.digitalFoosball.domain.common.constants.Team.ONE;
import static com.valtech.digitalFoosball.domain.common.constants.Team.TWO;

public class FirstHalf implements IPlayATimeGame {
    private IPlayATimeGame endByScoreLimit;
    private IPlayATimeGame halftime;
    private final TimeGameRules rules;
    private final Stack<Team> goalOverView;
    private final Stack<Team> undoOverView;

    public FirstHalf(TimeGameRules timeGameRules) {
        this.rules = timeGameRules;
        goalOverView = new Stack<>();
        undoOverView = new Stack<>();
    }

    @Override
    public void raiseScoreFor(Team team) {
        goalOverView.push(team);

    }

    @Override
    public void undoLastGoal() {
        if (goalOverView.isEmpty()) {
            return;
        }

        undoOverView.push(goalOverView.pop());
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
        Map<Team, Integer> scores = new HashMap<>();

        scores.put(ONE, Collections.frequency(goalOverView, ONE));
        scores.put(TWO, Collections.frequency(goalOverView, TWO));

        return scores;
    }

    private void next() {
        rules.setActualTimeGameSequence(halftime);
    }
}
