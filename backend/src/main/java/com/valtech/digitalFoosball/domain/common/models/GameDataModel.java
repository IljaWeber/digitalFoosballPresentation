package com.valtech.digitalFoosball.domain.common.models;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.ranked.RankedTeamDataModel;

import java.util.List;
import java.util.SortedMap;

public interface GameDataModel {

    SortedMap<Team, TeamDataModel> getTeams();

    void setTeams(List<RankedTeamDataModel> teams);

    TeamDataModel getTeam(Team team);

    void changeOver();

    void setTeam(Team team, RankedTeamDataModel teamDataModel);

    void countGoalFor(Team scoredTeam);

    void increaseWonSetsFor(Team team);

    boolean thereAreGoals();

    void decreaseWonSetsForRecentSetWinner();

    Team getSetWinner();

    void setSetWinner(Team setWinner);

    void undoLastGoal();

    void redoLastUndoneGoal();

    boolean thereAreUndoneGoals();

    void resetMatch();

    Team getLeadingTeam();
}
