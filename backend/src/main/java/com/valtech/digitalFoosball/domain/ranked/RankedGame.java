package com.valtech.digitalFoosball.domain.ranked;

import com.valtech.digitalFoosball.domain.common.DigitalFoosballGame;
import com.valtech.digitalFoosball.domain.common.models.GameDataModel;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.valtech.digitalFoosball.domain.common.constants.Team.NO_TEAM;

@Service
public class RankedGame extends DigitalFoosballGame {

    @Autowired
    public RankedGame(RankedInitService initService) {
        super(initService, new RankedGameRules());
    }

    @Override
    public GameDataModel initGame(InitDataModel initDataModel) {
        return initService.init(initDataModel);
    }

    @Override
    public void undoGoal(GameDataModel gameDataModel) {
        RankedGameDataModel castedModel = (RankedGameDataModel) gameDataModel;

        if (castedModel.thereAreGoals()) {

            if (castedModel.winConditionFullFilled()) {
                castedModel.decreaseWonSetsForRecentSetWinner();
                castedModel.setSetWinner(NO_TEAM);
            }

            castedModel.undoLastGoal();
        }
    }
}
