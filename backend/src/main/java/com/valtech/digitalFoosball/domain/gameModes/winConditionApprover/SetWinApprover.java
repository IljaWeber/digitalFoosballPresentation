package com.valtech.digitalFoosball.domain.gameModes.winConditionApprover;

import com.valtech.digitalFoosball.domain.gameModes.models.GameDataModel;

public interface SetWinApprover {
    void approveWin(GameDataModel gameDataModel);
}
