package com.valtech.digitalFoosball.domain.ranked;

import com.valtech.digitalFoosball.domain.common.constants.Team;

import java.util.SortedMap;

public interface GameDataModel {
    SortedMap<Team, TeamDataModel> getTeams();

    void resetMatch();
}
