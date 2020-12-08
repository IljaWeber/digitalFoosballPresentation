package com.valtech.digitalFoosball.domain.usecases.timeGame;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.usecases.timeGame.service.MatchScores;

public interface IPlayATimeGame {
    void raiseScoreFor(Team team);

    void undoLastGoal();

    void redoLastGoal();

    void changeover();

    MatchScores getMatchScores();

    Team getMatchWinner();

    void timeRanDown();
}
