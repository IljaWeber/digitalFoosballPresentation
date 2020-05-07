package com.valtech.digitalFoosball.model;

import com.valtech.digitalFoosball.constants.GameMode;
import com.valtech.digitalFoosball.constants.Team;
import com.valtech.digitalFoosball.model.internal.TeamDataModel;
import com.valtech.digitalFoosball.service.histories.History;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import static com.valtech.digitalFoosball.constants.GameMode.NO_ACTIVE_GAME;
import static com.valtech.digitalFoosball.constants.Team.*;

public class GameDataModel {
    private final SortedMap<Team, TeamDataModel> teams;
    private Team setWinner;
    private GameMode gameMode;
    private History history;

    public GameDataModel(List<TeamDataModel> teamsFromDatabase) {
        teams = new TreeMap<>();
        setWinner = NO_TEAM;

        teams.put(ONE, teamsFromDatabase.get(0));
        teams.put(TWO, teamsFromDatabase.get(1));

        history = new History();
        gameMode = NO_ACTIVE_GAME;
    }

    public GameDataModel() {
        teams = new TreeMap<>();
        setWinner = NO_TEAM;
        gameMode = NO_ACTIVE_GAME;
        history = new History();
    }

    public SortedMap<Team, TeamDataModel> getTeams() {
        return teams;
    }

    public TeamDataModel getTeam(Team team) {
        return teams.get(team);
    }

    public boolean isEmpty() {
        return teams.isEmpty();
    }

    public void changeOver() {
        teams.forEach((teamConstant, dataModel) -> dataModel.changeover());
        setWinner = NO_TEAM;
        history = new History();
    }

    public Team getSetWinner() {
        return setWinner;
    }

    public void setSetWinner(Team setWinner) {
        this.setWinner = setWinner;
    }

    public void setTeam(Team team, TeamDataModel teamDataModel) {
        teams.put(team, teamDataModel);
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public History getHistory() {
        return history;
    }

    public void setTeams(List<TeamDataModel> teams) {
        this.teams.put(ONE, teams.get(0));
        this.teams.put(TWO, teams.get(1));
    }

    public void countGoalFor(Team scoredTeam) {
        for (Team team : teams.keySet()) {
            if (team == scoredTeam) {
                teams.get(team).countGoal();
                history.rememberLastGoalFor(team);
            }
        }
    }

    public void increaseWonSetsFor(Team wonSetOfTeam) {
        for (Team team : teams.keySet()) {
            if (team == wonSetOfTeam) {
                teams.get(team).increaseWonSets();
            }
        }
    }

    public boolean checkForExistingGoals() {
        return history.thereAreGoals();
    }

    public void decreaseScoreForLastScoredTeam() {
        history.removeLastScoringTeam(teams);
    }

    public void decreaseWonSetsForRecentSetWinner() {
        for (Team team : teams.keySet()) {
            if (team == setWinner) {
                teams.get(team).decreaseWonSets();
            }
        }
    }

    public boolean setHasAWinner() {
        return setWinner != NO_TEAM;
    }
}
