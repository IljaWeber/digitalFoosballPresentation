package com.valtech.digitalFoosball.domain.timeGame;

import com.valtech.digitalFoosball.domain.common.IKnowTheRules;
import com.valtech.digitalFoosball.domain.common.constants.Team;
import org.assertj.core.util.Objects;

import java.util.Map;

import static com.valtech.digitalFoosball.domain.timeGame.GameState.*;

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

    Map<Team, Integer> getScoreOfTeams() {
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

    public GameState prepareActualGameSequence() {
        GameState actualGameState = FIRST_HALF;

        if (Objects.areEqual(actualGameSequence.getClass(), HalfTime.class)) {
            actualGameState = HALFTIME;
        }

        if (Objects.areEqual(actualGameSequence.getClass(), SecondHalf.class)) {
            actualGameState = SECOND_HALF;
        }

        if (Objects.areEqual(actualGameSequence.getClass(), EndByScoreLimit.class)) {
            actualGameState = OVER_BY_SCORE;
        }

        if (Objects.areEqual(actualGameSequence.getClass(), EndByTime.class)) {
            actualGameState = OVER_BY_TIME;
        }

        return actualGameState;
    }
}
