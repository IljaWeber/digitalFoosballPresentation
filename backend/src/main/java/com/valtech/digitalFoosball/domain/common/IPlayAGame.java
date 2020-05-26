package com.valtech.digitalFoosball.domain.common;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.models.GameDataModel;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import com.valtech.digitalFoosball.domain.common.models.output.team.TeamOutputModel;

import java.util.List;

public interface IPlayAGame {
    List<TeamOutputModel> getAllTeamsFromDatabase();

    GameDataModel initGame(InitDataModel initDataModel);

    void countGoalFor(Team team, GameDataModel gameDataModel);

    void undoGoal(GameDataModel gameDataModel);

    void redoGoal(GameDataModel gameDataModel);

    void changeover(GameDataModel gameDataModel);
}
