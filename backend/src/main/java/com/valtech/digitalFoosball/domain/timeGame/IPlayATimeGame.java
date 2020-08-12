package com.valtech.digitalFoosball.domain.timeGame;

import com.valtech.digitalFoosball.domain.common.constants.Team;

import java.util.Map;

public interface IPlayATimeGame {
    void raiseScoreFor(Team team);

    void undoLastGoal();

    void redoLastGoal();

    void changeover();

    Map<Team, Integer> getScoreOfTeams();

    Team getMatchWinner();

    void timeRanDown();
}
