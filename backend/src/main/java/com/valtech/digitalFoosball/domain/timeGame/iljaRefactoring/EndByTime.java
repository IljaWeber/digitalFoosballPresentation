package com.valtech.digitalFoosball.domain.timeGame.iljaRefactoring;

import com.valtech.digitalFoosball.domain.common.constants.Team;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import static com.valtech.digitalFoosball.domain.common.constants.Team.*;

public class EndByTime implements IPlayATimeGame {
    private final Stack<Team> finalScore;

    public EndByTime(Stack<Team> goalOverview) {
        this.finalScore = goalOverview;
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
        Map<Team, Integer> scores = new HashMap<>();

        scores.put(ONE, Collections.frequency(finalScore, ONE));
        scores.put(TWO, Collections.frequency(finalScore, TWO));

        return scores;
    }

    @Override
    public Team getMatchWinner() {
        Team winner = NO_TEAM;
        int scoreOfTeamOne = Collections.frequency(finalScore, ONE);
        int scoreOfTeamTwo = Collections.frequency(finalScore, TWO);

        if (scoreOfTeamOne > scoreOfTeamTwo) {
            winner = ONE;
        }

        if (scoreOfTeamOne < scoreOfTeamTwo) {
            winner = TWO;
        }

        return winner;
    }
}
