package com.valtech.digitalFoosball.domain.common;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import com.valtech.digitalFoosball.domain.common.models.output.team.TeamOutputModel;
import com.valtech.digitalFoosball.domain.ranked.RankedGameDataModel;

import java.util.List;

public interface IPlayAGame {
    List<TeamOutputModel> getAllTeamsFromDatabase();

    void initGame(InitDataModel initDataModel);

    void countGoalFor(Team team);

    void undoGoal();

    void redoGoal();

    void changeover();

    void resetMatch();

    // TODO: 15.07.20 m.huber return Output-Model instead
    RankedGameDataModel getGameData();
}
