package com.valtech.digitalFoosball.domain.common.histories;

import com.valtech.digitalFoosball.domain.common.constants.Team;

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

    public Team undo() {
        Team teamToUndo = historyOfGoals.pop();
        historyOfUndo.push(teamToUndo);
        return teamToUndo;
    }

    public Team getLastUndoingTeam() {
        return historyOfUndo.pop();
    }

    public boolean thereAreGoals() {
        return !historyOfGoals.empty();
    }

    public boolean thereAreUndoneGoals() {
        return !historyOfUndo.empty();
    }
}
