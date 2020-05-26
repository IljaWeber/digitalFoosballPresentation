package com.valtech.digitalFoosball.domain.adhoc;

import com.valtech.digitalFoosball.domain.common.DigitalFoosballGame;
import com.valtech.digitalFoosball.domain.common.models.GameDataModel;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import com.valtech.digitalFoosball.domain.ranked.RankedGameRules;
import com.valtech.digitalFoosball.domain.ranked.RankedTeamDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.valtech.digitalFoosball.domain.common.constants.Team.NO_TEAM;

@Service
public class AdHocGame extends DigitalFoosballGame {

    @Autowired
    public AdHocGame(AdHocInitService initService) {
        super(initService, new RankedGameRules());
    }

    @Override
    public GameDataModel initGame(InitDataModel initDataModel) {
        initDataModel = new InitDataModel();
        RankedTeamDataModel teamOne = new RankedTeamDataModel("Orange", "Goalie", "Striker");
        RankedTeamDataModel teamTwo = new RankedTeamDataModel("Green", "Goalie", "Striker");

        initDataModel.setTeamOne(teamOne);
        initDataModel.setTeamTwo(teamTwo);

        return initService.init(initDataModel);
    }

    @Override
    public void undoGoal(GameDataModel gameDataModel) {

        if (gameDataModel.thereAreGoals()) {

            if (gameDataModel.winConditionFullFilled()) {
                gameDataModel.decreaseWonSetsForRecentSetWinner();
                gameDataModel.setSetWinner(NO_TEAM);
            }

            gameDataModel.undoLastGoal();
        }
    }
}
