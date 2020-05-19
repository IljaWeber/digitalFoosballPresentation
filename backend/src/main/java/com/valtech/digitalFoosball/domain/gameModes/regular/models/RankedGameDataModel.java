package com.valtech.digitalFoosball.domain.gameModes.regular.models;

import com.valtech.digitalFoosball.api.driven.notification.Observer;
import com.valtech.digitalFoosball.domain.constants.GameMode;
import com.valtech.digitalFoosball.domain.constants.Team;
import com.valtech.digitalFoosball.domain.gameModes.histories.History;
import com.valtech.digitalFoosball.domain.gameModes.models.GameDataModel;
import com.valtech.digitalFoosball.domain.gameModes.models.GameOutputModel;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import static com.valtech.digitalFoosball.domain.constants.GameMode.NO_ACTIVE_GAME;
import static com.valtech.digitalFoosball.domain.constants.Team.*;

public class RankedGameDataModel implements GameDataModel {
    private final SortedMap<Team, RankedTeamDataModel> teams;
    private Team setWinner;
    private GameMode gameMode;
    private History history;
    private final List<Observer> observers;

    public RankedGameDataModel(List<RankedTeamDataModel> teamsFromDatabase) {
        teams = new TreeMap<>();
        setWinner = NO_TEAM;

        teams.put(ONE, teamsFromDatabase.get(0));
        teams.put(TWO, teamsFromDatabase.get(1));

        history = new History();
        gameMode = NO_ACTIVE_GAME;
        observers = new ArrayList<>();
    }

    public RankedGameDataModel() {
        teams = new TreeMap<>();
        setWinner = NO_TEAM;
        gameMode = NO_ACTIVE_GAME;
        history = new History();
        observers = new ArrayList<>();
    }

    @Override
    public SortedMap<Team, RankedTeamDataModel> getTeams() {
        return teams;
    }

    @Override
    public void setTeams(List<RankedTeamDataModel> teams) {
        this.teams.put(ONE, teams.get(0));
        this.teams.put(TWO, teams.get(1));
    }

    @Override
    public RankedTeamDataModel getTeam(Team team) {
        return teams.get(team);
    }

    @Override
    public boolean isEmpty() {
        return teams.isEmpty();
    }

    @Override
    public void changeOver() {
        teams.forEach((teamConstant, dataModel) -> dataModel.changeover());
        setWinner = NO_TEAM;
        history = new History();
    }

    @Override
    public void setTeam(Team team, RankedTeamDataModel teamDataModel) {
        teams.put(team, teamDataModel);
    }

    @Override
    public GameMode getGameMode() {
        return gameMode;
    }

    @Override
    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    @Override
    public void countGoalFor(Team scoredTeam) {
        if (setHasAWinner()) {
            return;
        }

        teams.get(scoredTeam).countGoal();
        history.rememberLastGoalFor(scoredTeam);
    }

    @Override
    public void increaseWonSetsFor(Team team) {
        teams.get(team).increaseWonSets();
    }

    @Override
    public boolean thereAreGoals() {
        return history.thereAreGoals();
    }

    @Override
    public boolean setHasAWinner() {
        return setWinner != NO_TEAM;
    }

    @Override
    public void decreaseWonSetsForRecentSetWinner() {
        RankedTeamDataModel setWinningTeam = teams.get(setWinner);
        setWinningTeam.decreaseWonSets();
    }

    @Override
    public Team getSetWinner() {
        return setWinner;
    }

    @Override
    public void setSetWinner(Team setWinner) {
        this.setWinner = setWinner;
    }

    @Override
    public void undoLastGoal() {
        Team undo = history.undo();
        RankedTeamDataModel teamDataModel = teams.get(undo);
        teamDataModel.decreaseScore();
    }

    @Override
    public void redoLastUndoneGoal() {
        Team redo = history.getLastUndoingTeam();
        countGoalFor(redo);
    }

    @Override
    public boolean thereAreUndoneGoals() {
        return history.thereAreUndoneGoals();
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    protected void updateObservers() {
        for (Observer observer : observers) {
            GameOutputModel gameOutputModel = new GameOutputModel(this);
            observer.update(gameOutputModel);
        }
    }
}
