package com.valtech.digitalFoosball.service.histories;

import com.valtech.digitalFoosball.constants.Team;

import java.util.Stack;

public class GoalHistory {

    private Stack<Team> historyOfGoals;

    public GoalHistory() {
        historyOfGoals = new Stack<>();
    }

    public void rememberLastGoalFrom(Team team) {
        historyOfGoals.push(team);
    }

    public Team removeOneGoalFromHistory() {
        return historyOfGoals.pop();
    }

    public boolean thereAreGoals() {
        return !historyOfGoals.empty();
    }

    public Stack<Team> getHistoryOfGoals() {
        return historyOfGoals;
    }
}