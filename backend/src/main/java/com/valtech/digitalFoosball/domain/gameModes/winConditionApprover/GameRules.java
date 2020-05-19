package com.valtech.digitalFoosball.domain.gameModes.winConditionApprover;

import com.valtech.digitalFoosball.domain.gameModes.models.GameDataModel;

public interface GameRules {
    void approveWin(GameDataModel gameDataModel);
}
