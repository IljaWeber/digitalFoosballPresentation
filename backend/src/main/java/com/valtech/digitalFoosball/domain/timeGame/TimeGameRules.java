package com.valtech.digitalFoosball.domain.timeGame;

import com.valtech.digitalFoosball.domain.common.IKnowTheRules;
import com.valtech.digitalFoosball.domain.common.constants.Team;

import java.util.Map;

public class TimeGameRules implements IKnowTheRules {
    private IPlayATimeGame actualGameSequence;
    private String alternativeGameSequenceRepresentation;

    public TimeGameRules() {
        actualGameSequence = new FirstHalf(this);
        alternativeGameSequenceRepresentation = "First Half";
    }

    public void raiseScoreFor(Team team) {
        actualGameSequence.raiseScoreFor(team);
    }

    public void setActualTimeGameSequence(IPlayATimeGame gameSequence) {
        actualGameSequence = gameSequence;
        alternativeGameSequenceRepresentation = gameSequence.toString();
    }

    public Map<Team, Integer> getScoreOfTeams() {
        return actualGameSequence.getScoreOfTeams();
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
        return alternativeGameSequenceRepresentation;
    }
}
