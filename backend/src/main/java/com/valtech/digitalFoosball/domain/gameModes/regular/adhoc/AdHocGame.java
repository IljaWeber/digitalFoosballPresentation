package com.valtech.digitalFoosball.domain.gameModes.regular.adhoc;

import com.valtech.digitalFoosball.domain.gameModes.manipulators.DigitalFoosballGame;
import com.valtech.digitalFoosball.domain.gameModes.models.InitDataModel;
import com.valtech.digitalFoosball.domain.gameModes.regular.models.game.GameDataModel;
import com.valtech.digitalFoosball.domain.gameModes.regular.models.game.RankedGameDataModel;
import com.valtech.digitalFoosball.domain.gameModes.regular.models.team.RankedTeamDataModel;
import com.valtech.digitalFoosball.domain.gameModes.winConditionApprover.RankedGameRules;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.valtech.digitalFoosball.domain.constants.Team.NO_TEAM;

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
        RankedGameDataModel castedModel = (RankedGameDataModel) gameDataModel;

        if (castedModel.thereAreGoals()) {

            if (castedModel.setHasAWinner()) {
                castedModel.decreaseWonSetsForRecentSetWinner();
                castedModel.setSetWinner(NO_TEAM);
            }

            castedModel.undoLastGoal();
        }
    }
}
