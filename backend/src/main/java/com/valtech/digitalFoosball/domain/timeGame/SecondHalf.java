package com.valtech.digitalFoosball.domain.timeGame;

import com.valtech.digitalFoosball.domain.common.constants.Team;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import static com.valtech.digitalFoosball.domain.common.constants.Team.ONE;
import static com.valtech.digitalFoosball.domain.common.constants.Team.TWO;

public class SecondHalf implements IPlayATimeGame {
    private final Stack<Team> goalOverview;
    private final TimeGameRules rules;

    public SecondHalf(Stack<Team> goalOverview, TimeGameRules rules) {
        this.goalOverview = goalOverview;
        this.rules = rules;

    }

    @Override
    public void raiseScoreFor(Team team) {
        goalOverview.push(team);

        if (Collections.frequency(goalOverview, team) >= 10) {
            endGame();
        }
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
        Map<Team, Integer> scores = new HashMap<>();

        scores.put(ONE, Collections.frequency(goalOverview, ONE));
        scores.put(TWO, Collections.frequency(goalOverview, TWO));

        return scores;
    }

    private void endGame() {
        IPlayATimeGame endByScoreLimit = new EndByScoreLimit(this, rules);
        rules.setActualTimeGameSequence(endByScoreLimit);
    }
}
