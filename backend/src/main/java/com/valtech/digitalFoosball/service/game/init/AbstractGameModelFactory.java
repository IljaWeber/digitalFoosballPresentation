package com.valtech.digitalFoosball.service.game.init;

import com.valtech.digitalFoosball.constants.GameMode;
import com.valtech.digitalFoosball.model.internal.GameDataModel;
import com.valtech.digitalFoosball.model.internal.RegularGameDataModel;
import com.valtech.digitalFoosball.model.internal.TimeGameDataModel;

public abstract class AbstractGameModelFactory {
    public AbstractGameModelFactory() {
    }

    public static GameDataModel createGameDataModel(GameMode mode) {
        GameDataModel gameDataModel;
        if (mode == GameMode.TIME_GAME) {
            gameDataModel = new TimeGameDataModel();
        } else {
            gameDataModel = new RegularGameDataModel();
        }
        return gameDataModel;
    }
}
