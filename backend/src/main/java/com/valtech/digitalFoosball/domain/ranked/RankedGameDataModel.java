package com.valtech.digitalFoosball.domain.ranked;

import com.valtech.digitalFoosball.domain.common.constants.GameMode;
import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.histories.History;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import static com.valtech.digitalFoosball.domain.common.constants.GameMode.NO_ACTIVE_GAME;
import static com.valtech.digitalFoosball.domain.common.constants.Team.*;

public class RankedGameDataModel {
    protected SortedMap<Team, RankedTeamDataModel> teams;
    protected GameMode gameMode;
    protected History history;
    private Team setWinner;

    public RankedGameDataModel() {
        teams = new TreeMap<>();
        history = new History();
        setWinner = NO_TEAM;
        gameMode = NO_ACTIVE_GAME;
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

    public void increaseWonSetsFor(Team team) {
        teams.get(team).increaseWonSets();
    }

    public void decreaseWonSetsForRecentSetWinner() {
        RankedTeamDataModel setWinningTeam = teams.get(setWinner);
        setWinningTeam.decreaseWonSets();
    }

    public Team getSetWinner() {
        return setWinner;
    }

    public void setSetWinner(Team setWinner) {
        this.setWinner = setWinner;
    }

    public void resetMatch() {
        teams.clear();
        setWinner = NO_TEAM;
        history = new History();
    }

    public void changeOver() {
        teams.forEach((teamConstant, dataModel) -> dataModel.changeover());
        setWinner = NO_TEAM;
        history = new History();
    }

}
