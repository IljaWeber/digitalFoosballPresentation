package com.valtech.digitalFoosball.domain.timeGame.sequences;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.timeGame.IPlayATimeGame;
import com.valtech.digitalFoosball.domain.timeGame.MatchScores;
import com.valtech.digitalFoosball.domain.timeGame.TimeGameRules;

public class EndByScore extends GameOver implements IPlayATimeGame {
    private final IPlayATimeGame previousTimeGameSequence;
    private final TimeGameRules rules;
    private final Team winner;
    private boolean timeRanDown = false;

    public EndByScore(IPlayATimeGame previous,
                      TimeGameRules rules, Team winnerTeam) {
        this.previousTimeGameSequence = previous;
        this.rules = rules;
        this.winner = winnerTeam;
    }

    @Override
    public void undoLastGoal() {
        previousTimeGameSequence.undoLastGoal();

        if (timeRanDown) {
            previousTimeGameSequence.timeRanDown();
        } else {
            rules.setActualTimeGameSequence(previousTimeGameSequence);
        }
    }

    @Override
    public MatchScores getMatchScores() {
        return previousTimeGameSequence.getMatchScores();
    }

    @Override
    public Team getMatchWinner() {
        return winner;
    }

    @Override
    public void timeRanDown() {
        timeRanDown = true;
    }

    @Override
    public String toString() {
        return "End By Score";
    }

}
