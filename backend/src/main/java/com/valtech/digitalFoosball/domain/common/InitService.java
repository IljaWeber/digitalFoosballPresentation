package com.valtech.digitalFoosball.domain.common;

import com.valtech.digitalFoosball.domain.common.models.GameDataModel;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import com.valtech.digitalFoosball.domain.common.models.output.team.TeamOutputModel;

import java.util.List;

public interface InitService {
    GameDataModel init(InitDataModel initDataModel);

    // TODO: 28.07.20 m.huber remove this method and move in separate class
    List<TeamOutputModel> getAllTeams();
}
