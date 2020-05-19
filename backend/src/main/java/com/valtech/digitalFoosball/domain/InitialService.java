package com.valtech.digitalFoosball.domain;

import com.valtech.digitalFoosball.domain.gameModes.models.GameDataModel;
import com.valtech.digitalFoosball.domain.gameModes.models.InitDataModel;
import com.valtech.digitalFoosball.domain.gameModes.models.TeamOutputModel;

import java.util.List;

public interface InitialService {
    GameDataModel init(InitDataModel initDataModel);

    List<TeamOutputModel> getAllTeams();
}
