package com.valtech.digitalFoosball.domain.timeGame;

import com.valtech.digitalFoosball.domain.common.constants.Team;

public interface IPlayATimeGame {
    void raiseScoreFor(Team team);

    void undoLastGoal();

    void redoLastGoal();

    void changeover();

    MatchScores getMatchScores();

    Team getMatchWinner();

    void timeRanDown();
}
