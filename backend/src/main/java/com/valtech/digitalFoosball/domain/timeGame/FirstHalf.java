package com.valtech.digitalFoosball.domain.timeGame;

import com.valtech.digitalFoosball.domain.common.constants.Team;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import static com.valtech.digitalFoosball.domain.common.constants.Team.ONE;
import static com.valtech.digitalFoosball.domain.common.constants.Team.TWO;

public class FirstHalf implements IPlayATimeGame {
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

        if (Collections.frequency(goalOverView, team) < 10) {
            goalOverView.push(team);
        }

        if (Collections.frequency(goalOverView, team) >= 10) {
            endGame();
        }

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
        if (undoOverView.isEmpty()) {
            return;
        }

        raiseScoreFor(undoOverView.pop());
    }

    @Override
    public void changeover() {

    }

    @Override
    public void resetGame() {

    }

    @Override
    public Map<Team, Integer> getScoreOfTeams() {
        Map<Team, Integer> scores = new HashMap<>();

        scores.put(ONE, Collections.frequency(goalOverView, ONE));
        scores.put(TWO, Collections.frequency(goalOverView, TWO));

        return scores;
    }

    public void changeTimeGameSequence(IPlayATimeGame sequence) {
        rules.setActualTimeGameSequence(sequence);
    }

    private void endGame() {
        IPlayATimeGame endByScoreLimit = new EndByScoreLimit(this, rules, goalOverView);
        rules.setActualTimeGameSequence(endByScoreLimit);
    }
}
