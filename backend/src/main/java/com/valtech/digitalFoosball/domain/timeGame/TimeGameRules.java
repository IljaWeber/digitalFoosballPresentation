package com.valtech.digitalFoosball.domain.timeGame;

import com.valtech.digitalFoosball.domain.IKnowTheRules;
import com.valtech.digitalFoosball.domain.common.constants.Team;

import java.util.Map;

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
        return actualGameSequence.toString();
    }
}
