package com.valtech.digitalFoosball.domain.adhoc;

import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import com.valtech.digitalFoosball.domain.common.models.output.team.TeamOutputModel;
import com.valtech.digitalFoosball.domain.ranked.RankedTeamDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdHocGame extends ClassicGame {
    private final AdHocInitService initService;

    @Autowired
    public AdHocGame(AdHocInitService initService) {
        this.initService = initService;
    }

    @Override
    public List<TeamOutputModel> getAllTeamsFromDatabase() {
        return null;
    }

    @Override
    public void initGame(InitDataModel initDataModel) {
        initDataModel = new InitDataModel();
        RankedTeamDataModel teamOne = new RankedTeamDataModel("Orange", "Goalie", "Striker");
        RankedTeamDataModel teamTwo = new RankedTeamDataModel("Green", "Goalie", "Striker");

        initDataModel.setTeamOne(teamOne);
        initDataModel.setTeamTwo(teamTwo);

        super.gameDataModel = initService.init(initDataModel);
    }
}
