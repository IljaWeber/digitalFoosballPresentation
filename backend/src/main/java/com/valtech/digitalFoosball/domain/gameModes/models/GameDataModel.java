package com.valtech.digitalFoosball.domain.gameModes.models;

import com.valtech.digitalFoosball.api.driven.notification.Observer;
import com.valtech.digitalFoosball.domain.constants.GameMode;
import com.valtech.digitalFoosball.domain.constants.Team;
import com.valtech.digitalFoosball.domain.gameModes.regular.models.TeamDataModel;

import java.util.List;
import java.util.SortedMap;

public interface GameDataModel {
    SortedMap<Team, TeamDataModel> getTeams();

    void setTeams(List<TeamDataModel> teams);

    TeamDataModel getTeam(Team team);

    boolean isEmpty();

    void changeOver();

    void setTeam(Team team, TeamDataModel teamDataModel);

    GameMode getGameMode();

    void setGameMode(GameMode gameMode);

    void countGoalFor(Team scoredTeam);

    void increaseWonSetsFor(Team team);

    boolean thereAreGoals();

    boolean setHasAWinner();

    void decreaseWonSetsForRecentSetWinner();

    Team getSetWinner();

    void setSetWinner(Team setWinner);

    void undoLastGoal();

    void redoLastUndoneGoal();

    boolean thereAreUndoneGoals();

    void addObserver(Observer observer);
}
