package com.valtech.digitalFoosball.domain.common;

import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import com.valtech.digitalFoosball.domain.common.models.output.team.TeamOutputModel;
import com.valtech.digitalFoosball.domain.ranked.RankedGameDataModel;

import java.util.List;

public interface InitService {
    RankedGameDataModel init(InitDataModel initDataModel);

    List<TeamOutputModel> getAllTeams();
}
