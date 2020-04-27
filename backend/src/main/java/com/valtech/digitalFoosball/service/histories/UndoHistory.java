package com.valtech.digitalFoosball.service.histories;

import com.valtech.digitalFoosball.constants.Team;

import java.util.Stack;

public class UndoHistory {
    private Stack<Team> historyOfUndo;

    public UndoHistory() {
        historyOfUndo = new Stack<>();
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
