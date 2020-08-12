package com.valtech.digitalFoosball.domain.timeGame;

import com.valtech.digitalFoosball.domain.IKnowTheRules;
import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.timeGame.sequences.FirstHalf;

import java.util.Map;

public class TimeGameRules implements IKnowTheRules {
    private IPlayATimeGame actualGameSequence;
    private TimeGame game;

    public TimeGameRules() {
        actualGameSequence = new FirstHalf(this);
    }

    public void raiseScoreFor(Team team) {
        actualGameSequence.raiseScoreFor(team);
    }

    public void setActualTimeGameSequence(IPlayATimeGame gameSequence) {
        actualGameSequence = gameSequence;

        game.gameSequenceChanged();
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

    public void setGame(TimeGame game) {
        this.game = game;
    }
}
