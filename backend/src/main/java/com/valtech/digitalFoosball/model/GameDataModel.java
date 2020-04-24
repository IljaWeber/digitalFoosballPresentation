package com.valtech.digitalFoosball.model;

import com.valtech.digitalFoosball.constants.Team;
import com.valtech.digitalFoosball.model.internal.TeamDataModel;
import com.valtech.digitalFoosball.service.histories.GoalHistory;
import com.valtech.digitalFoosball.service.histories.UndoHistory;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import static com.valtech.digitalFoosball.constants.Team.ONE;
import static com.valtech.digitalFoosball.constants.Team.TWO;

public class GameDataModel {
    private final SortedMap<Team, TeamDataModel> teams;
    private GoalHistory goalHistory;
    private UndoHistory undoHistory;

    public GameDataModel(List<TeamDataModel> teamsFromDatabase) {
        teams = new TreeMap<>();
        goalHistory = new GoalHistory();
        undoHistory = new UndoHistory();

        teams.put(ONE, teamsFromDatabase.get(0));
        teams.put(TWO, teamsFromDatabase.get(1));
    }

    public GameDataModel() {
        teams = new TreeMap<>();
        goalHistory = new GoalHistory();
        undoHistory = new UndoHistory();
    }

    public SortedMap<Team, TeamDataModel> getTeams() {
        return teams;
    }

    public GoalHistory getGoalHistory() {
        return goalHistory;
    }

    public UndoHistory getUndoHistory() {
        return undoHistory;
    }

    public TeamDataModel getTeam(Team team) {
        return teams.get(team);
    }

    public boolean isEmpty() {
        return teams.isEmpty();
    }

    public void resetMatch() {
        goalHistory = new GoalHistory();
        undoHistory = new UndoHistory();
    }
}