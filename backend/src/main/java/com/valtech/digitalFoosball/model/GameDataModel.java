package com.valtech.digitalFoosball.model;

import com.valtech.digitalFoosball.constants.Team;
import com.valtech.digitalFoosball.model.internal.TeamDataModel;
import com.valtech.digitalFoosball.service.histories.GoalHistory;
import com.valtech.digitalFoosball.service.histories.UndoHistory;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import static com.valtech.digitalFoosball.constants.Team.*;

public class GameDataModel {
    private final SortedMap<Team, TeamDataModel> teams;
    private GoalHistory goalHistory;
    private UndoHistory undoHistory;
    private Team setWinner;

    public GameDataModel(List<TeamDataModel> teamsFromDatabase) {
        teams = new TreeMap<>();
        goalHistory = new GoalHistory();
        undoHistory = new UndoHistory();
        setWinner = NO_TEAM;

        teams.put(ONE, teamsFromDatabase.get(0));
        teams.put(TWO, teamsFromDatabase.get(1));
    }

    public GameDataModel() {
        teams = new TreeMap<>();
        goalHistory = new GoalHistory();
        undoHistory = new UndoHistory();
        setWinner = NO_TEAM;
    }

    public SortedMap<Team, TeamDataModel> getTeams() {
        return teams;
    }

    public TeamDataModel getTeam(Team team) {
        return teams.get(team);
    }

    public boolean isEmpty() {
        return teams.isEmpty();
    }

    public void changeOver() {
        goalHistory = new GoalHistory();
        undoHistory = new UndoHistory();
        setWinner = NO_TEAM;
    }

    public Team getSetWinner() {
        return setWinner;
    }

    public void setSetWinner(Team setWinner) {
        this.setWinner = setWinner;
    }

    public void setTeam(Team team, TeamDataModel teamDataModel) {
        teams.put(team, teamDataModel);
    }

    public void rememberLastGoalFrom(Team team) {
        goalHistory.rememberLastGoalFrom(team);
    }

    public boolean thereAreGoals() {
        return goalHistory.thereAreGoals();
    }

    public Team getLastScoringTeam() {
        return goalHistory.removeOneGoalFromHistory();
    }

    public void rememberUndoneGoal(Team team) {
        undoHistory.rememberUndoneGoal(team);
    }

    public boolean hasUndoneGoals() {
        return undoHistory.hasUndoneGoals();
    }

    public Team getLastUndoneGoal() {
        return undoHistory.removeUndoneGoal();
    }
}