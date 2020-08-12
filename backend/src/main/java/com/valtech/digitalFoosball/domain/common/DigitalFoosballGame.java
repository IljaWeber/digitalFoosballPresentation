package com.valtech.digitalFoosball.domain.common;

import com.valtech.digitalFoosball.domain.IInitializeGames;
import com.valtech.digitalFoosball.domain.common.models.GameDataModel;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import com.valtech.digitalFoosball.domain.common.models.output.game.ClassicGameOutputModel;
import com.valtech.digitalFoosball.domain.common.models.output.game.GameOutputModel;

public class DigitalFoosballGame extends BaseGame<DigitalFoosballGameRules> {
    private final IInitializeGames initService;
    protected GameDataModel model;

    public DigitalFoosballGame(IInitializeGames initService) {
        this.initService = initService;
    }

    public void initGame(InitDataModel initDataModel) {
        gameRules = new DigitalFoosballGameRules();
        model = initService.init(initDataModel);
    }

    @Override
    public void resetMatch() {
        model.resetMatch();
        gameRules = new DigitalFoosballGameRules();
    }

    public GameOutputModel getGameData() {
        return new ClassicGameOutputModel(model, gameRules);
    }
}
