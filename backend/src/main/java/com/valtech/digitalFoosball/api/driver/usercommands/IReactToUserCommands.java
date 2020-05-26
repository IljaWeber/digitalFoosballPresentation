package com.valtech.digitalFoosball.api.driver.usercommands;

import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import com.valtech.digitalFoosball.domain.common.models.output.game.GameOutputModel;
import com.valtech.digitalFoosball.domain.common.models.output.team.TeamOutputModel;

import java.util.List;

public interface IReactToUserCommands {
    List<TeamOutputModel> getAllTeams();

    void initGame(InitDataModel initDataModel);

    void undoGoal();

    void redoGoal();

    void changeover();

    void resetMatch();

    GameOutputModel getGameData();
}
