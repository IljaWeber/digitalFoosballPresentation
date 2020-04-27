package com.valtech.digitalFoosball.service.histories;

import com.valtech.digitalFoosball.constants.Team;

import java.util.Stack;

public class History {
    private Stack<Team> historyOfGoals;
    private Stack<Team> historyOfUndo;

    public History() {
        historyOfGoals = new Stack<>();
        historyOfUndo = new Stack<>();
    }

    public void rememberLastGoalFrom(Team team) {
        historyOfGoals.push(team);
    }

    public Team getLastScoringTeam() {
        return historyOfGoals.pop();
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
}
