package com.valtech.digitalFoosball.domain.common;

import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import com.valtech.digitalFoosball.domain.common.models.output.team.TeamOutputModel;

import java.util.List;

public interface InitService {
    GameDataModel init(InitDataModel initDataModel);

    List<TeamOutputModel> getAllTeams();
}
