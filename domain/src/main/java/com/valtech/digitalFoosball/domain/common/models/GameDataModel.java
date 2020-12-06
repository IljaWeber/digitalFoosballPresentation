package com.valtech.digitalFoosball.domain.common.models;

import com.valtech.digitalFoosball.domain.common.constants.Team;

import java.util.*;

public class GameDataModel {
    private final SortedMap<Team, TeamDataModel> teams;
    private final Stack<Team> goalOverView;
    private final Stack<Team> undoOverView;
    private final Stack<Team> winOverview;
    private Team currentSetWinner = Team.NO_TEAM;

    public GameDataModel() {
        teams = new TreeMap<>();
        goalOverView = new Stack<>();
        undoOverView = new Stack<>();
        winOverview = new Stack<>();
    }

    public SortedMap<Team, TeamDataModel> getTeams() {
        return teams;
    }

    public void setTeams(List<TeamDataModel> teams) {
        this.teams.put(Team.ONE, teams.get(0));
        this.teams.put(Team.TWO, teams.get(1));
    }

    public TeamDataModel getTeam(Team team) {
        return teams.get(team);
    }

    public Team getCurrentSetWinner() {
        return currentSetWinner;
    }

    public void raiseScoreFor(Team team) {

        if (currentSetWinner != Team.NO_TEAM) {
            return;
        }

        goalOverView.push(team);

        checkForWin();

        if (currentSetWinner != Team.NO_TEAM) {
            winOverview.push(currentSetWinner);
        }
    }

    public int getScoreOfTeam(Team team) {
        if (goalOverView.isEmpty()) {
            return 0;
        }

        return Collections.frequency(goalOverView, team);
    }

    public Team getMatchWinner() {
        for (Team team : Team.getTeams()) {
            if (Collections.frequency(winOverview, team) >= 2) {
                return team;
            }
        }

        return Team.NO_TEAM;
    }

    public void undoLastGoal() {
        if (goalOverView.isEmpty()) {
            return;
        }

        if (currentSetWinner != Team.NO_TEAM) {
            winOverview.pop();
            currentSetWinner = Team.NO_TEAM;
        }

        Team team = goalOverView.pop();
        undoOverView.push(team);
    }

    public void redoLastGoal() {
        if (undoOverView.isEmpty()) {
            return;
        }

        raiseScoreFor(undoOverView.pop());
    }

    public void changeOver() {
        goalOverView.clear();
        undoOverView.clear();
        currentSetWinner = Team.NO_TEAM;
    }

    private void checkForWin() {
        int neededGoals = 6;
        int scoreOfTeamOne = Collections.frequency(goalOverView, Team.ONE);
        int scoreOfTeamTwo = Collections.frequency(goalOverView, Team.TWO);

        if (scoreOfTeamOne >= neededGoals) {
            if (scoreOfTeamOne - scoreOfTeamTwo >= 2) {
                currentSetWinner = Team.ONE;
            }
        }

        if (scoreOfTeamTwo >= neededGoals) {
            if (scoreOfTeamTwo - scoreOfTeamOne >= 2) {
                currentSetWinner = Team.TWO;
            }
        }
    }

    public void resetMatch() {
        teams.clear();
    }
}
