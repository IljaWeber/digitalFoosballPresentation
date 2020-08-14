package com.valtech.digitalFoosball.domain.timeGame;

import com.valtech.digitalFoosball.domain.IKnowTheRules;
import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.timeGame.sequences.FirstHalf;

public class TimeGameRules implements IKnowTheRules {
    private IPlayATimeGame actualGameSequence;

    public TimeGameRules() {
        actualGameSequence = new FirstHalf(this);
    }

    public void raiseScoreFor(Team team) {
        actualGameSequence.raiseScoreFor(team);
    }

    public void setActualTimeGameSequence(IPlayATimeGame gameSequence) {
        actualGameSequence = gameSequence;
    }

    public MatchScores getMatchScores() {
        return actualGameSequence.getMatchScores();
    }

    public IPlayATimeGame getActualGameSequence() {
        return actualGameSequence;
    }

    public void undoLastGoal() {
        actualGameSequence.undoLastGoal();
    }

    public void redoLastGoal() {
        actualGameSequence.redoLastGoal();
    }

    public void changeOver() {
        actualGameSequence.changeover();
    }

    public Team getMatchWinner() {
        return actualGameSequence.getMatchWinner();
    }

    public String getAlternativeGameSequenceRepresentation() {
        return actualGameSequence.toString();
    }

    public void timeRanDown() {
        actualGameSequence.timeRanDown();
    }
}
