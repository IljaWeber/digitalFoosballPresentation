package com.valtech.digitalFoosball.service.histories;

import com.valtech.digitalFoosball.constants.Team;
import com.valtech.digitalFoosball.model.internal.TeamDataModel;

import java.util.SortedMap;
import java.util.Stack;

public class History {
    private final Stack<Team> historyOfGoals;
    private final Stack<Team> historyOfUndo;

    public History() {
        historyOfGoals = new Stack<>();
        historyOfUndo = new Stack<>();
    }

    public void rememberLastGoalFor(Team team) {
        historyOfGoals.push(team);
    }

    public Team getLastScoringTeam() {
        return historyOfGoals.pop();
    }

    private void rememberUndoneGoalFor(Team lastScoredTeam) {
        historyOfUndo.push(lastScoredTeam);
    }

    public boolean thereAreGoals() {
        return !historyOfGoals.empty();
    }

    public void rememberUndoneGoal(Team team) {
        historyOfUndo.push(team);
    }

    public Team getLastUndoneGoal() {
        return historyOfUndo.pop();
    }

    public boolean hasUndoneGoals() {
        return !historyOfUndo.empty();
    }

    public void removeLastScoringTeam(SortedMap<Team, TeamDataModel> teams) {
        Team lastScoredTeam = historyOfGoals.pop();

        rememberUndoneGoalFor(lastScoredTeam);

        for (Team team : teams.keySet()) {
            if (team == lastScoredTeam) {
                teams.get(team).decreaseScore();
            }
        }
    }
}
