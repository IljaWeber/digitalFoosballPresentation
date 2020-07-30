package com.valtech.digitalFoosball.domain.timeGame;

import com.valtech.digitalFoosball.domain.common.constants.Team;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import static com.valtech.digitalFoosball.domain.common.constants.Team.ONE;
import static com.valtech.digitalFoosball.domain.common.constants.Team.TWO;

public class EndByScoreLimit implements IPlayATimeGame {
    private final IPlayATimeGame previousTimeGameSequence;
    private final Stack<Team> goalOverview;
    private final TimeGameRules rules;

    public EndByScoreLimit(IPlayATimeGame previous,
                           TimeGameRules rules,
                           Stack<Team> goalOverView) {
        this.previousTimeGameSequence = previous;
        this.goalOverview = goalOverView;
        this.rules = rules;
    }

    @Override
    public void raiseScoreFor(Team team) {

    }

    @Override
    public void undoLastGoal() {
        previousTimeGameSequence.undoLastGoal();
        rules.setActualTimeGameSequence(previousTimeGameSequence);
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
}
