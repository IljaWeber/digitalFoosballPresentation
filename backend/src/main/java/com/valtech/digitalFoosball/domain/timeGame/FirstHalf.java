package com.valtech.digitalFoosball.domain.timeGame;

import com.valtech.digitalFoosball.domain.common.constants.Team;

import java.util.*;

import static com.valtech.digitalFoosball.domain.common.constants.Team.ONE;
import static com.valtech.digitalFoosball.domain.common.constants.Team.TWO;

public class FirstHalf implements IPlayATimeGame {
    public TimeGameRules rules;
    private final Stack<Team> goalOverView;
    private final Stack<Team> undoOverView;
    private Timer timer;

    public FirstHalf(TimeGameRules timeGameRules) {
        this.rules = timeGameRules;
        goalOverView = new Stack<>();
        undoOverView = new Stack<>();
        timer = new Timer();
        timer.schedule(new TimeGameTimerTask(this), 420000);
    }

    @Override
    public void raiseScoreFor(Team team) {
        goalOverView.push(team);

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

    private void endGame() {
        IPlayATimeGame endByScoreLimit = new EndByScoreLimit(this, rules);
        rules.setActualTimeGameSequence(endByScoreLimit);
    }

    public void nextSequenceByTime() {
        IPlayATimeGame halfTime = new HalfTime(goalOverView, rules);

        rules.setActualTimeGameSequence(halfTime);
    }
}
