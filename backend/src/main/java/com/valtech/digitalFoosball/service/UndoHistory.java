package com.valtech.digitalFoosball.service;

import com.valtech.digitalFoosball.constants.Team;

import java.util.Stack;

public class UndoHistory {
    private Stack<Team> historyOfUndo;

    public UndoHistory() {
        historyOfUndo = new Stack<>();
    }

    Team rememberUndoneGoal(Team team) {
        return historyOfUndo.push(team);
    }

    Team removeUndoneGoal() {
        return historyOfUndo.pop();
    }

    boolean hasUndoneGoals() {
        return !historyOfUndo.empty();
    }

    public Stack<Team> getHistoryOfUndo() {
        return historyOfUndo;
    }
}