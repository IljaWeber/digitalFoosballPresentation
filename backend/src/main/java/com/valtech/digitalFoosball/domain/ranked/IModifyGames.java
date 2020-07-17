package com.valtech.digitalFoosball.domain.ranked;

import com.valtech.digitalFoosball.domain.common.constants.Team;

public interface IModifyGames {

    void raiseScoreFor(Team team);

    void undoLastGoal();

    void redoGoal();

    void changeOver();

    RankedGameDataModel getGameData();

    void resetMatch();
}
