package com.valtech.digitalFoosball.domain;

import com.valtech.digitalFoosball.domain.gameModes.models.InitDataModel;
import com.valtech.digitalFoosball.domain.gameModes.models.output.team.TeamOutputModel;
import com.valtech.digitalFoosball.domain.gameModes.regular.models.game.GameDataModel;

import java.util.List;

public interface InitialService {
    GameDataModel init(InitDataModel initDataModel);

    List<TeamOutputModel> getAllTeams();
}
