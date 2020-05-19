package com.valtech.digitalFoosball.api.driver.usercommands;

import com.valtech.digitalFoosball.domain.gameModes.models.BaseOutputModel;
import com.valtech.digitalFoosball.domain.gameModes.models.InitDataModel;
import com.valtech.digitalFoosball.domain.gameModes.models.TeamOutputModel;

import java.util.List;

public interface IReactToUserCommands {
    List<TeamOutputModel> getAllTeams();

    void initGame(InitDataModel initDataModel);

    void undoGoal();

    void redoGoal();

    void changeover();

    void resetMatch();

    BaseOutputModel getGameData();
}
