package com.valtech.digitalFoosball.domain.common.histories;

import com.valtech.digitalFoosball.domain.common.constants.Team;

import java.util.Stack;

public class ScoreOverView {
    private final Stack<Team> goalOverView;
    private final Stack<Team> undoOverView;

    public ScoreOverView() {
        goalOverView = new Stack<>();
        undoOverView = new Stack<>();
    }

    public void rememberLastGoalFor(Team team) {
        goalOverView.push(team);
    }

    public Team getLastScoredTeam() {
        Team teamToUndo = goalOverView.pop();
        undoOverView.push(teamToUndo);

        return teamToUndo;
    }

    public Team getLastUndoingTeam() {
        return undoOverView.pop();
    }

    public boolean thereAreGoals() {
        return !goalOverView.empty();
    }

    public boolean thereAreUndoneGoals() {
        return !undoOverView.empty();
    }
}
