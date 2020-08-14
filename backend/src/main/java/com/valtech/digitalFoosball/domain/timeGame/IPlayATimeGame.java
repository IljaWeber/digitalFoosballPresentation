package com.valtech.digitalFoosball.domain.timeGame;

import com.valtech.digitalFoosball.domain.common.constants.Team;

import java.util.Map;

public interface IPlayATimeGame {
    void raiseScoreFor(Team team);

    void undoLastGoal();

    void redoLastGoal();

    void changeover();

    // todo: created on 14.08.20 by iljaweber: map to object to hide intern details  
    Map<Team, Integer> getScoreOfTeams();

    Team getMatchWinner();

    void timeRanDown();
}
