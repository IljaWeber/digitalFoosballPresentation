package com.valtech.digitalFoosball.domain.common;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.models.output.game.GameOutputModel;

public interface IModifyGames {
    void raiseScoreFor(Team team);

    void undoLastGoal();

    void redoGoal();

    void changeOver();

    void resetMatch();

    GameOutputModel getPreparedDataForOutput();
}
