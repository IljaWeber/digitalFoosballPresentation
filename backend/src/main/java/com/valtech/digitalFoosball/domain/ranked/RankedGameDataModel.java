package com.valtech.digitalFoosball.domain.ranked;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.histories.ScoreOverView;

import java.util.List;
import java.util.SortedMap;
import java.util.Stack;
import java.util.TreeMap;

import static com.valtech.digitalFoosball.domain.common.constants.Team.*;

public class RankedGameDataModel implements GameDataModel {
    protected SortedMap<Team, RankedTeamDataModel> teams;
    protected ScoreOverView scoreOverView;
    private final Stack<Team> winOverView;
    private Team actualWinner;

    public RankedGameDataModel() {
        teams = new TreeMap<>();
        scoreOverView = new ScoreOverView();
        actualWinner = NO_TEAM;
        winOverView = new Stack<>();
    }

    public SortedMap<Team, RankedTeamDataModel> getTeams() {
        return teams;
    }

    @Override
    public void setWinnerOfAGame(Team team) {
        winOverView.push(team);

        this.actualWinner = team;

        if (team != NO_TEAM) {
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
            scoreOverView.rememberLastGoalFor(scoredTeam);
        }
    }

    public boolean thereAreGoals() {
        return scoreOverView.thereAreGoals();
    }

    public boolean areThereUndoneGoals() {
        return scoreOverView.thereAreUndoneGoals();
    }

    public void undoLastGoal() {
        if (actualWinner != NO_TEAM) {
            teams.get(actualWinner).decreaseWonSets();
            actualWinner = NO_TEAM;
            winOverView.pop();
        }

        Team undo = scoreOverView.undo();
        teams.get(undo).decreaseScore();
    }

    public void redoLastUndoneGoal() {
        Team redo = scoreOverView.getLastUndoingTeam();
        countGoalFor(redo);
    }

    public void resetMatch() {
        teams.clear();
        actualWinner = NO_TEAM;
        scoreOverView = new ScoreOverView();
    }

    @Override
    public Team getWinner() {
        return actualWinner;
    }

    public void changeOver() {
        teams.forEach((teamConstant, dataModel) -> dataModel.changeover());
        actualWinner = NO_TEAM;
        scoreOverView = new ScoreOverView();
    }

    @Override
    public Stack<Team> getAllWins() {
        return winOverView;
    }
}
