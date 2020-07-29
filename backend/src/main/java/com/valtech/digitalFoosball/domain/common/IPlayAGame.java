package com.valtech.digitalFoosball.domain.common;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import com.valtech.digitalFoosball.domain.common.models.output.game.GameOutputModel;

public interface IPlayAGame {
    void initGame(InitDataModel initDataModel);

    void countGoalFor(Team team);

    void undoGoal();

    void redoGoal();

    void changeover();

    void resetMatch();

    GameOutputModel getGameData();
}
