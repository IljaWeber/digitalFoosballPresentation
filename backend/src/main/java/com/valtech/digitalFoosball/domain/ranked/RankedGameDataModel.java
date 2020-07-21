package com.valtech.digitalFoosball.domain.ranked;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.histories.History;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import static com.valtech.digitalFoosball.domain.common.constants.Team.*;

public class RankedGameDataModel implements GameDataModel {
    protected SortedMap<Team, RankedTeamDataModel> teams;
    protected History history;
    private Team setWinner;

    public RankedGameDataModel() {
        teams = new TreeMap<>();
        history = new History();
        setWinner = NO_TEAM;
    }

    public SortedMap<Team, RankedTeamDataModel> getTeams() {
        return teams;
    }

    @Override
    public void setWinnerOfAGame(Team team) {
        this.setWinner = team;

        if (setWinner != NO_TEAM) {

            teams.get(team).increaseWonSets();
        }
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
        if (setWinner == NO_TEAM) {
            teams.get(scoredTeam).countGoal();
            history.rememberLastGoalFor(scoredTeam);
        }
    }

    public boolean thereAreGoals() {
        return history.thereAreGoals();
    }

    @Override
    public boolean areThereUndoneGoals() {
        return history.thereAreUndoneGoals();
    }

    public void undoLastGoal() {
        if (setWinner != NO_TEAM) {
            teams.get(setWinner).decreaseWonSets();
            setWinner = NO_TEAM;
        }

        Team undo = history.undo();
        teams.get(undo).decreaseScore();
    }

    public void redoLastUndoneGoal() {
        Team redo = history.getLastUndoingTeam();
        countGoalFor(redo);
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

    @Override
    public Team getWinner() {
        return setWinner;
    }

    public void changeOver() {
        teams.forEach((teamConstant, dataModel) -> dataModel.changeover());
        setWinner = NO_TEAM;
        history = new History();
    }

}
