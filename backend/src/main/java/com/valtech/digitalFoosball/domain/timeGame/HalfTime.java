package com.valtech.digitalFoosball.domain.timeGame;

import com.valtech.digitalFoosball.domain.common.constants.Team;

import java.util.Map;

public class HalfTime implements IPlayATimeGame {
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
        return null;
    }
}
