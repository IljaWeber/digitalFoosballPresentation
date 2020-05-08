package com.valtech.digitalFoosball.model.internal;

import com.valtech.digitalFoosball.api.Observer;
import com.valtech.digitalFoosball.constants.GameMode;
import com.valtech.digitalFoosball.constants.Team;
import com.valtech.digitalFoosball.model.output.GameOutputModel;
import com.valtech.digitalFoosball.service.histories.History;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import static com.valtech.digitalFoosball.constants.GameMode.NO_ACTIVE_GAME;
import static com.valtech.digitalFoosball.constants.Team.*;

public class RegularGameDataModel implements GameDataModel {
    private final SortedMap<Team, TeamDataModel> teams;
    private Team setWinner;
    private GameMode gameMode;
    private History history;
    private final List<Observer> observers;

    public RegularGameDataModel(List<TeamDataModel> teamsFromDatabase) {
        teams = new TreeMap<>();
        setWinner = NO_TEAM;

        teams.put(ONE, teamsFromDatabase.get(0));
        teams.put(TWO, teamsFromDatabase.get(1));

        history = new History();
        gameMode = NO_ACTIVE_GAME;
        observers = new ArrayList<>();
    }

    public RegularGameDataModel() {
        teams = new TreeMap<>();
        setWinner = NO_TEAM;
        gameMode = NO_ACTIVE_GAME;
        history = new History();
        observers = new ArrayList<>();
    }

    @Override
    public SortedMap<Team, TeamDataModel> getTeams() {
        return teams;
    }

    @Override
    public void setTeams(List<TeamDataModel> teams) {
        this.teams.put(ONE, teams.get(0));
        this.teams.put(TWO, teams.get(1));
    }

    @Override
    public TeamDataModel getTeam(Team team) {
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
    public void setTeam(Team team, TeamDataModel teamDataModel) {
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
        TeamDataModel setWinningTeam = teams.get(setWinner);
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
        TeamDataModel teamDataModel = teams.get(undo);
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
