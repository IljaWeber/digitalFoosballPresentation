package com.valtech.digitalFoosball.domain.common;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.ranked.RankedGameDataModel;

public interface IModifyGames {

    void raiseScoreFor(Team team);

    void undoLastGoal();

    void redoGoal();

    void changeOver();

    RankedGameDataModel getGameData();

    void resetMatch();
}
