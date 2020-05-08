package com.valtech.digitalFoosball.service.game.manipulator;

import com.valtech.digitalFoosball.model.input.InitDataModel;
import com.valtech.digitalFoosball.model.internal.GameDataModel;
import com.valtech.digitalFoosball.model.internal.TeamDataModel;
import com.valtech.digitalFoosball.service.game.init.AdHocInitService;
import com.valtech.digitalFoosball.service.verifier.RankedGameSetWinApprover;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdHocGameManipulator extends AbstractGameManipulator {

    @Autowired
    public AdHocGameManipulator(AdHocInitService initService) {
        super(initService, new RankedGameSetWinApprover());
    }

    @Override
    public GameDataModel initGame(InitDataModel initDataModel) {
        initDataModel = new InitDataModel();
        TeamDataModel teamOne = new TeamDataModel("Orange", "Goalie", "Striker");
        TeamDataModel teamTwo = new TeamDataModel("Green", "Goalie", "Striker");

        initDataModel.setTeamOne(teamOne);
        initDataModel.setTeamTwo(teamTwo);

        return initService.init(initDataModel);
    }
}
