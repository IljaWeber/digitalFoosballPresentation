package com.valtech.digitalFoosball.domain.common;

import com.valtech.digitalFoosball.domain.ranked.RankedGameDataModel;

public interface GameRules {
    void approveWin(RankedGameDataModel gameDataModel);

    boolean winConditionsFulfilled(RankedGameDataModel gameDataModel);

}
