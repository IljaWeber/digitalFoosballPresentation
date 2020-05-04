package com.valtech.digitalFoosball.service.game.modes;

import com.valtech.digitalFoosball.constants.Team;
import com.valtech.digitalFoosball.model.GameDataModel;
import com.valtech.digitalFoosball.model.input.InitDataModel;
import com.valtech.digitalFoosball.model.output.TeamOutput;

import java.util.List;

public interface Game {
    List<TeamOutput> getAllTeamsFromDatabase();

    GameDataModel initGame(InitDataModel initDataModel);

    void countGoalFor(Team team, GameDataModel gameDataModel);

    void undoGoal(GameDataModel gameDataModel);

    void redoGoal(GameDataModel gameDataModel);

    void changeover(GameDataModel gameDataModel);
}
