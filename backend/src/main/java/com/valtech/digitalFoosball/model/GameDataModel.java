package com.valtech.digitalFoosball.model;

import com.valtech.digitalFoosball.constants.GameMode;
import com.valtech.digitalFoosball.constants.Team;
import com.valtech.digitalFoosball.model.internal.TeamDataModel;
import com.valtech.digitalFoosball.service.histories.History;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import static com.valtech.digitalFoosball.constants.GameMode.NO_ACTIVE_GAME;
import static com.valtech.digitalFoosball.constants.Team.*;

public class GameDataModel {
    private final SortedMap<Team, TeamDataModel> teams;
    private Team setWinner;
    private GameMode gameMode;
    private History history;

    public GameDataModel(List<TeamDataModel> teamsFromDatabase) {
        teams = new TreeMap<>();
        setWinner = NO_TEAM;

        teams.put(ONE, teamsFromDatabase.get(0));
        teams.put(TWO, teamsFromDatabase.get(1));

        history = new History();
        gameMode = NO_ACTIVE_GAME;
    }

    public GameDataModel() {
        teams = new TreeMap<>();
        setWinner = NO_TEAM;
        gameMode = NO_ACTIVE_GAME;
        history = new History();
    }

    public SortedMap<Team, TeamDataModel> getTeams() {
        return teams;
    }

    public void setTeams(List<TeamDataModel> teams) {
        this.teams.put(ONE, teams.get(0));
        this.teams.put(TWO, teams.get(1));
    }

    public TeamDataModel getTeam(Team team) {
        return teams.get(team);
    }

    public boolean isEmpty() {
        return teams.isEmpty();
    }

    public void changeOver() {
        teams.forEach((teamConstant, dataModel) -> dataModel.changeover());
        setWinner = NO_TEAM;
        history = new History();
    }

    public void setTeam(Team team, TeamDataModel teamDataModel) {
        teams.put(team, teamDataModel);
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public void countGoalFor(Team scoredTeam) {
        if (setHasAWinner()) {
            return;
        }

        teams.get(scoredTeam).countGoal();
        history.rememberLastGoalFor(scoredTeam);
    }

    public void increaseWonSetsFor(Team team) {
        teams.get(team).increaseWonSets();
    }

    public boolean thereAreGoals() {
        return history.thereAreGoals();
    }

    public boolean setHasAWinner() {
        return setWinner != NO_TEAM;
    }

    public void decreaseWonSetsForRecentSetWinner() {
        TeamDataModel setWinningTeam = teams.get(setWinner);
        setWinningTeam.decreaseWonSets();
    }

    public Team getSetWinner() {
        return setWinner;
    }

    public void setSetWinner(Team setWinner) {
        this.setWinner = setWinner;
    }

    public void undoLastGoal() {
        Team undo = history.undo();
        TeamDataModel teamDataModel = teams.get(undo);
        teamDataModel.decreaseScore();
    }

    public void redoLastUndoneGoal() {
        Team redo = history.getLastUndoingTeam();
        countGoalFor(redo);
    }

    public boolean thereAreUndoneGoals() {
        return history.thereAreUndoneGoals();
    }
}
