package com.valtech.digitalFoosball.domain.common.models;

import com.valtech.digitalFoosball.api.driven.notification.Observer;
import com.valtech.digitalFoosball.domain.common.constants.GameMode;
import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.ranked.RankedTeamDataModel;

import java.util.List;
import java.util.SortedMap;

public interface GameDataModel {

    SortedMap<Team, RankedTeamDataModel> getTeams();

    void setTeams(List<RankedTeamDataModel> teams);

    RankedTeamDataModel getTeam(Team team);

    void changeOver();

    void setTeam(Team team, RankedTeamDataModel teamDataModel);

    GameMode getGameMode();

    void setGameMode(GameMode gameMode);

    void countGoalFor(Team scoredTeam);

    void increaseWonSetsFor(Team team);

    boolean thereAreGoals();

    boolean winConditionFullFilled();

    void decreaseWonSetsForRecentSetWinner();

    Team getSetWinner();

    void setSetWinner(Team setWinner);

    void undoLastGoal();

    void redoLastUndoneGoal();

    boolean thereAreUndoneGoals();

    void addObserver(Observer observer);

    void resetMatch();
}
