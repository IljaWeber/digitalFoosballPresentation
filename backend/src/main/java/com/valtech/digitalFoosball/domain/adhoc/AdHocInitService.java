package com.valtech.digitalFoosball.domain.adhoc;

import com.valtech.digitalFoosball.domain.common.ClassicGameInitService;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import com.valtech.digitalFoosball.domain.ranked.RankedGameDataModel;
import com.valtech.digitalFoosball.domain.ranked.TeamDataModel;

import java.util.Arrays;
import java.util.List;

public class AdHocInitService extends ClassicGameInitService {

    public AdHocInitService() {
        super(null);
    }

    @Override
    public RankedGameDataModel init(InitDataModel initDataModel) {
        RankedGameDataModel rankedGameDataModel = new RankedGameDataModel();

        TeamDataModel teamOne = new TeamDataModel("Orange", "Goalie", "Striker");
        TeamDataModel teamTwo = new TeamDataModel("Green", "Goalie", "Striker");
        List<TeamDataModel> teamDataModels = Arrays.asList(teamOne, teamTwo);
        rankedGameDataModel.setTeams(teamDataModels);

        return rankedGameDataModel;
    }
}