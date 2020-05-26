package com.valtech.digitalFoosball.domain.common;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.models.GameDataModel;

public interface GameRules {
    void approveWin(GameDataModel gameDataModel);

    Team getWinner(GameDataModel gameDataModel);
}
