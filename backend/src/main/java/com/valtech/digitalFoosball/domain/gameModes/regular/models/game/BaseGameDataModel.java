package com.valtech.digitalFoosball.domain.gameModes.regular.models.game;

import com.valtech.digitalFoosball.api.driven.notification.Observer;
import com.valtech.digitalFoosball.domain.constants.GameMode;
import com.valtech.digitalFoosball.domain.constants.Team;
import com.valtech.digitalFoosball.domain.gameModes.histories.History;
import com.valtech.digitalFoosball.domain.gameModes.regular.models.team.RankedTeamDataModel;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import static com.valtech.digitalFoosball.domain.constants.Team.ONE;
import static com.valtech.digitalFoosball.domain.constants.Team.TWO;

public abstract class BaseGameDataModel implements GameDataModel {
    protected SortedMap<Team, RankedTeamDataModel> teams;
    protected List<Observer> observers;
    protected GameMode gameMode;
    protected History history;

    public BaseGameDataModel() {
        teams = new TreeMap<>();
        history = new History();
        observers = new ArrayList<>();
    }

    public SortedMap<Team, RankedTeamDataModel> getTeams() {
        return teams;
    }

    public void setTeams(List<RankedTeamDataModel> teams) {
        this.teams.put(ONE, teams.get(0));
        this.teams.put(TWO, teams.get(1));
    }

    public RankedTeamDataModel getTeam(Team team) {
        return teams.get(team);
    }

    public void setTeam(Team team, RankedTeamDataModel teamDataModel) {
        teams.put(team, teamDataModel);
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

    public void countGoalFor(Team scoredTeam) {
        teams.get(scoredTeam).countGoal();
        history.rememberLastGoalFor(scoredTeam);
    }

    public boolean thereAreGoals() {
        return history.thereAreGoals();
    }

    public void undoLastGoal() {
        Team undo = history.undo();
        RankedTeamDataModel teamDataModel = teams.get(undo);
        teamDataModel.decreaseScore();
    }

    public void redoLastUndoneGoal() {
        Team redo = history.getLastUndoingTeam();
        countGoalFor(redo);
    }

    public boolean thereAreUndoneGoals() {
        return history.thereAreUndoneGoals();
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }
}
