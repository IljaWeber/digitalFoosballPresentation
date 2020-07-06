package com.valtech.digitalFoosball.domain.cases.adhoc;

import com.valtech.digitalFoosball.api.driven.notification.Observer;
import com.valtech.digitalFoosball.domain.common.constants.GameMode;
import com.valtech.digitalFoosball.domain.common.constants.Team;

import java.util.List;
import java.util.SortedMap;

public interface AdHocGameDataModel {

    SortedMap<Team, AdHocTeamModel> getTeams();

    void setTeams(List<AdHocTeamModel> teams);

    AdHocTeamModel getTeam(Team team);

    void changeOver();

    void setTeam(Team team, AdHocTeamModel teamDataModel);

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

    Team getLeadingTeam();
}
