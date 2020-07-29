package com.valtech.digitalFoosball.api.driver.usercommands;

import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import com.valtech.digitalFoosball.domain.common.models.output.game.GameOutputModel;

public interface IReactToUserCommands {
    void initGame(InitDataModel initDataModel);

    void undoGoal();

    void redoGoal();

    void changeover();

    void resetMatch();

    GameOutputModel getGameData();
}
