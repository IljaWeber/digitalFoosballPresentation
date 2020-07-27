package com.valtech.digitalFoosball.domain.ranked;

import com.valtech.digitalFoosball.domain.common.constants.Team;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import static com.valtech.digitalFoosball.domain.common.constants.Team.*;

public class RankedGameDataModel implements GameDataModel {
    protected SortedMap<Team, RankedTeamDataModel> teams;
    private Team actualWinner;

    public RankedGameDataModel() {
        teams = new TreeMap<>();
        actualWinner = NO_TEAM;
    }

    public SortedMap<Team, RankedTeamDataModel> getTeams() {
        return teams;
    }

    @Override
    public void setWinnerOfAGame(Team team) {
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
        teams.get(scoredTeam).countGoal();
    }

    public void undoLastGoalFor(Team team) {
        if (team == actualWinner) {
            teams.get(team).decreaseWonSets();
            actualWinner = NO_TEAM;
        }

        teams.get(team).decreaseScore();
    }

    public void resetMatch() {
        teams.clear();
        actualWinner = NO_TEAM;
    }

    @Override
    public Team getWinner() {
        return actualWinner;
    }

    public void changeOver() {
        teams.forEach((teamConstant, dataModel) -> dataModel.changeover());
        actualWinner = NO_TEAM;
    }

}
