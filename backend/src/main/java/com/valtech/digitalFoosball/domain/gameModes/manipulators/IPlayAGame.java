package com.valtech.digitalFoosball.domain.gameModes.manipulators;

import com.valtech.digitalFoosball.domain.constants.Team;
import com.valtech.digitalFoosball.domain.gameModes.models.GameDataModel;
import com.valtech.digitalFoosball.domain.gameModes.models.InitDataModel;
import com.valtech.digitalFoosball.domain.gameModes.models.output.team.TeamOutputModel;

import java.util.List;

public interface IPlayAGame {
    List<TeamOutputModel> getAllTeamsFromDatabase();

    GameDataModel initGame(InitDataModel initDataModel);

    void countGoalFor(Team team, GameDataModel gameDataModel);

    void undoGoal(GameDataModel gameDataModel);

    void redoGoal(GameDataModel gameDataModel);

    void changeover(GameDataModel gameDataModel);
}
