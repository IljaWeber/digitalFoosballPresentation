package com.valtech.digitalFoosball.domain.gameModes.winConditionApprover;

import com.valtech.digitalFoosball.domain.gameModes.regular.models.game.GameDataModel;

public interface GameRules {
    void approveWin(GameDataModel gameDataModel);
}
