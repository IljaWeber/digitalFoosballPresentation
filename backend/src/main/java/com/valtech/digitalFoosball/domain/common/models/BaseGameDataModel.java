package com.valtech.digitalFoosball.domain.common.models;

import com.valtech.digitalFoosball.api.driven.notification.Observer;
import com.valtech.digitalFoosball.domain.common.constants.GameMode;
import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.histories.History;
import com.valtech.digitalFoosball.domain.ranked.RankedTeamDataModel;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import static com.valtech.digitalFoosball.domain.common.constants.Team.*;

public abstract class BaseGameDataModel implements GameDataModel {
    protected SortedMap<Team, TeamDataModel> teams;
    protected List<Observer> observers;
    protected GameMode gameMode;
    protected History history;

    public BaseGameDataModel() {
        teams = new TreeMap<>();
        history = new History();
        observers = new ArrayList<>();
    }

    public SortedMap<Team, TeamDataModel> getTeams() {
        return teams;
    }

    public void setTeams(List<RankedTeamDataModel> teams) {
        this.teams.put(ONE, teams.get(0));
        this.teams.put(TWO, teams.get(1));
    }

    public TeamDataModel getTeam(Team team) {
        return teams.get(team);
    }

    public void setTeam(Team team, RankedTeamDataModel teamDataModel) {
        teams.put(team, teamDataModel);
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
        TeamDataModel teamDataModel = teams.get(undo);
        teamDataModel.decreaseScore();
    }

    public void redoLastUndoneGoal() {
        Team redo = history.getLastUndoingTeam();
        countGoalFor(redo);
    }

    @Override
    public Team getLeadingTeam() {
        Team leadingTeam = NO_TEAM;

        int scoreOne = teams.get(ONE).getScore();
        int scoreTwo = teams.get(TWO).getScore();

        if (scoreOne > scoreTwo) {
            leadingTeam = ONE;
        }

        if (scoreTwo > scoreOne) {
            leadingTeam = TWO;
        }

        return leadingTeam;
    }

    public boolean thereAreUndoneGoals() {
        return history.thereAreUndoneGoals();
    }
}
