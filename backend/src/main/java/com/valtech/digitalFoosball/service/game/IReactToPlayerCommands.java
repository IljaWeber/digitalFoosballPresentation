package com.valtech.digitalFoosball.service.game;

import com.valtech.digitalFoosball.model.input.InitDataModel;
import com.valtech.digitalFoosball.model.output.GameOutputModel;
import com.valtech.digitalFoosball.model.output.TeamOutput;

import java.util.List;

public interface IReactToPlayerCommands {
    List<TeamOutput> getAllTeams();

    void initGame(InitDataModel initDataModel);

    void undoGoal();

    void redoGoal();

    void changeover();

    void resetMatch();

    GameOutputModel getGameData();
}
