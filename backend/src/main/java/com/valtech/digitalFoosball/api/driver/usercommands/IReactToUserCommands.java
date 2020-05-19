package com.valtech.digitalFoosball.api.driver.usercommands;

import com.valtech.digitalFoosball.domain.gameModes.models.InitDataModel;
import com.valtech.digitalFoosball.domain.gameModes.models.output.game.GameOutputModel;
import com.valtech.digitalFoosball.domain.gameModes.models.output.team.TeamOutputModel;

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
