package com.valtech.digitalFoosball.domain.common;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.ranked.RankedGameDataModel;

public interface GameRules {
    void approveWin(RankedGameDataModel gameDataModel);

    Team getTeamWithLeadOfTwo(RankedGameDataModel gameDataModel);

    boolean winConditionsFulfilled(RankedGameDataModel gameDataModel);

}
