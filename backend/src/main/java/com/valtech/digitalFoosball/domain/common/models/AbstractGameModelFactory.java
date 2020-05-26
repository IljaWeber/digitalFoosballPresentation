package com.valtech.digitalFoosball.domain.common.models;

import com.valtech.digitalFoosball.domain.common.constants.GameMode;
import com.valtech.digitalFoosball.domain.ranked.RankedGameDataModel;
import com.valtech.digitalFoosball.domain.timePlay.TimeGameDataModel;

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
