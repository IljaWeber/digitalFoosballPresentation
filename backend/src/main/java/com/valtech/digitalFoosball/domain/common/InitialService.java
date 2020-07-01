package com.valtech.digitalFoosball.domain.common;

import com.valtech.digitalFoosball.domain.common.models.GameDataModel;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import com.valtech.digitalFoosball.domain.common.models.output.team.TeamOutputModel;

import java.util.List;

public interface InitialService {
    GameDataModel init(InitDataModel initDataModel);

    List<TeamOutputModel> getAllTeams();
}