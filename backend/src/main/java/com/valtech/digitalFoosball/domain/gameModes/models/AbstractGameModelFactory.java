package com.valtech.digitalFoosball.domain.gameModes.models;

import com.valtech.digitalFoosball.domain.constants.GameMode;
import com.valtech.digitalFoosball.domain.gameModes.regular.models.game.GameDataModel;
import com.valtech.digitalFoosball.domain.gameModes.regular.models.game.RankedGameDataModel;
import com.valtech.digitalFoosball.domain.gameModes.regular.models.game.TimeGameDataModel;

public abstract class AbstractGameModelFactory {
    public static GameDataModel createGameDataModel(GameMode mode) {
        GameDataModel gameDataModel;
        if (mode == GameMode.TIME_GAME) {
            gameDataModel = new TimeGameDataModel();
        } else {
            gameDataModel = new RankedGameDataModel();
        }
        return gameDataModel;
    }
}
