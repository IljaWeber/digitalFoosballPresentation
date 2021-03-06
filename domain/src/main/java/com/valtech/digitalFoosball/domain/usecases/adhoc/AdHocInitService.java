package com.valtech.digitalFoosball.domain.usecases.adhoc;

import com.valtech.digitalFoosball.domain.IInitializeGames;
import com.valtech.digitalFoosball.domain.common.models.GameDataModel;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import com.valtech.digitalFoosball.domain.common.models.TeamDataModel;

import java.util.Arrays;
import java.util.List;

public class AdHocInitService implements IInitializeGames {

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
