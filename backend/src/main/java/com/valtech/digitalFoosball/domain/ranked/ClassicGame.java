package com.valtech.digitalFoosball.domain.ranked;

import com.valtech.digitalFoosball.domain.common.BaseGame;
import com.valtech.digitalFoosball.domain.common.IInitializeGames;
import com.valtech.digitalFoosball.domain.common.models.GameDataModel;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import com.valtech.digitalFoosball.domain.common.models.output.game.ClassicGameOutputModel;
import com.valtech.digitalFoosball.domain.common.models.output.game.GameOutputModel;

public class ClassicGame extends BaseGame {
    private final IInitializeGames initService;
    protected GameDataModel model;
    private ClassicGameRules gameRules;

    public ClassicGame(IInitializeGames initService) {
        this.initService = initService;
    }

    public void initGame(InitDataModel initDataModel) {
        gameRules = new ClassicGameRules();
        model = initService.init(initDataModel);
        super.setGameRules(gameRules);
    }

    @Override
    public void resetMatch() {
        model.resetMatch();
        gameRules = new ClassicGameRules();
    }

    public GameOutputModel getGameData() {
        return new ClassicGameOutputModel(model, gameRules);
    }
}