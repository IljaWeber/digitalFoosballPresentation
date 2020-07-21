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
    private Team actualWinner;

    public RankedGameDataModel() {
        teams = new TreeMap<>();
        history = new History();
        actualWinner = NO_TEAM;
    }

    public SortedMap<Team, RankedTeamDataModel> getTeams() {
        return teams;
    }

    @Override
    public void setWinnerOfAGame(Team team) {
        this.actualWinner = team;

        if (actualWinner != NO_TEAM) {

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

    public void countGoalFor(Team scoredTeam) {
        if (actualWinner == NO_TEAM) {
            teams.get(scoredTeam).countGoal();
            history.rememberLastGoalFor(scoredTeam);
        }
    }

    public boolean thereAreGoals() {
        return history.thereAreGoals();
    }

    public boolean areThereUndoneGoals() {
        return history.thereAreUndoneGoals();
    }

    public void undoLastGoal() {
        if (actualWinner != NO_TEAM) {
            teams.get(actualWinner).decreaseWonSets();
            actualWinner = NO_TEAM;
        }

        Team undo = history.undo();
        teams.get(undo).decreaseScore();
    }

    public void redoLastUndoneGoal() {
        Team redo = history.getLastUndoingTeam();
        countGoalFor(redo);
    }

    public void resetMatch() {
        teams.clear();
        actualWinner = NO_TEAM;
        history = new History();
    }

    @Override
    public Team getWinner() {
        return actualWinner;
    }

    public void changeOver() {
        teams.forEach((teamConstant, dataModel) -> dataModel.changeover());
        actualWinner = NO_TEAM;
        history = new History();
    }

}
