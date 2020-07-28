package com.valtech.digitalFoosball.domain.adhoc;

import com.valtech.digitalFoosball.domain.common.ClassicGameInitService;
import com.valtech.digitalFoosball.domain.common.GameDataModel;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import com.valtech.digitalFoosball.domain.ranked.TeamDataModel;

import java.util.Arrays;
import java.util.List;

public class AdHocInitService extends ClassicGameInitService {

    public AdHocInitService() {
        super(null);
    }

    @Override
    public GameDataModel init(InitDataModel initDataModel) {
        GameDataModel gameDataModel = new GameDataModel();

        TeamDataModel teamOne = new TeamDataModel("Orange", "Goalie", "Striker");
        TeamDataModel teamTwo = new TeamDataModel("Green", "Goalie", "Striker");
        List<TeamDataModel> teamDataModels = Arrays.asList(teamOne, teamTwo);
        gameDataModel.setTeams(teamDataModels);

        return gameDataModel;
    }
}