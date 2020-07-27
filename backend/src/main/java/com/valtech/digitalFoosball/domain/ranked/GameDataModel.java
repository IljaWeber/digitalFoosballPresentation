package com.valtech.digitalFoosball.domain.ranked;

import com.valtech.digitalFoosball.domain.common.constants.Team;

import java.util.SortedMap;

public interface GameDataModel {
    void countGoalFor(Team team);

    SortedMap<Team, RankedTeamDataModel> getTeams();

    void setWinnerOfAGame(Team team);

    void undoLastGoalFor(Team team);

    void changeOver();

    void resetMatch();

    Team getWinner();

}
