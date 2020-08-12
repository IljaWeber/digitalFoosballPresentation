package com.valtech.digitalFoosball.domain.timeGame.sequences;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.timeGame.IPlayATimeGame;
import com.valtech.digitalFoosball.domain.timeGame.TimeGameRules;

import java.util.Map;

public class EndByScore extends GameOver implements IPlayATimeGame {
    private final IPlayATimeGame previousTimeGameSequence;
    private final TimeGameRules rules;
    private final Team winner;

    public EndByScore(IPlayATimeGame previous,
                      TimeGameRules rules, Team winnerTeam) {
        this.previousTimeGameSequence = previous;
        this.rules = rules;
        this.winner = winnerTeam;
    }

    @Override
    public void undoLastGoal() {
        previousTimeGameSequence.undoLastGoal();
        rules.setActualTimeGameSequence(previousTimeGameSequence);
    }

    @Override
    public Map<Team, Integer> getScoreOfTeams() {
        return previousTimeGameSequence.getScoreOfTeams();
    }

    @Override
    public Team getMatchWinner() {
        return winner;
    }

    @Override
    public void timeRanDown() {
    }

    @Override
    public String toString() {
        return "End By Score";
    }

}