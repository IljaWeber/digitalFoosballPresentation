package com.valtech.digitalFoosball.domain.timeGame.iljaRefactoring;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.timeGame.TimeGameRules;

import java.util.Map;

public class EndByScoreLimit implements IPlayATimeGame {
    private final IPlayATimeGame previousTimeGameSequence;
    private final TimeGameRules rules;

    public EndByScoreLimit(IPlayATimeGame previous,
                           TimeGameRules rules) {
        this.previousTimeGameSequence = previous;
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

        return previousTimeGameSequence.getScoreOfTeams();
    }
}
