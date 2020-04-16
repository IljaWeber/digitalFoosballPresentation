package com.valtech.digitalFoosball.service.histories;

import com.valtech.digitalFoosball.constants.Team;

import java.util.Stack;

public class UndoHistory {
    private Stack<Team> historyOfUndo;

    public UndoHistory() {
        historyOfUndo = new Stack<>();
    }

    public Team rememberUndoneGoal(Team team) {
        return historyOfUndo.push(team);
    }

    public Team removeUndoneGoal() {
        return historyOfUndo.pop();
    }

    public boolean hasUndoneGoals() {
        return !historyOfUndo.empty();
    }

    public Stack<Team> getHistoryOfUndo() {
        return historyOfUndo;
    }
}