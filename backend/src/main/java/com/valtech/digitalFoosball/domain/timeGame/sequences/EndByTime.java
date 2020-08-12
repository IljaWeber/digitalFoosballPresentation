package com.valtech.digitalFoosball.domain.timeGame.sequences;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.timeGame.IPlayATimeGame;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import static com.valtech.digitalFoosball.domain.common.constants.Team.*;

public class EndByTime extends GameOver implements IPlayATimeGame {
    private final Stack<Team> goalOverView;
    private final Stack<Team> undoOverView;

    public EndByTime(Stack<Team> goalOverview,
                     Stack<Team> undoOverView) {
        this.goalOverView = goalOverview;
        this.undoOverView = undoOverView;
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
    public Map<Team, Integer> getScoreOfTeams() {
        Map<Team, Integer> scores = new HashMap<>();

        scores.put(ONE, Collections.frequency(goalOverView, ONE));
        scores.put(TWO, Collections.frequency(goalOverView, TWO));

        return scores;
    }

    @Override
    public Team getMatchWinner() {
        Team winner = NO_TEAM;
        int scoreOfTeamOne = Collections.frequency(goalOverView, ONE);
        int scoreOfTeamTwo = Collections.frequency(goalOverView, TWO);

        if (scoreOfTeamOne > scoreOfTeamTwo) {
            winner = ONE;
        }

        if (scoreOfTeamOne < scoreOfTeamTwo) {
            winner = TWO;
        }

        return winner;
    }

    @Override
    public void timeRanDown() {
    }

    @Override
    public String toString() {
        return "End By Time";
    }
}
