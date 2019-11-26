package com.valtech.digitalFoosball.model.input;

import com.valtech.digitalFoosball.model.internal.TeamDataModel;

import java.util.Arrays;
import java.util.List;

public class InitDataModel {
    private TeamDataModel teamOne;
    private TeamDataModel teamTwo;

    public List<TeamDataModel> getTeams() {
        return Arrays.asList(teamOne, teamTwo);
    }

    public void setTeams(List<TeamDataModel> teams) {
        teamOne = teams.get(0);
        teamTwo = teams.get(1);
    }

    public TeamDataModel getTeamOne() {
        return teamOne;
    }

    public void setTeamOne(TeamDataModel teamOne) {
        this.teamOne = teamOne;
    }

    public TeamDataModel getTeamTwo() {
        return teamTwo;
    }

    public void setTeamTwo(TeamDataModel teamTwo) {
        this.teamTwo = teamTwo;
    }

    public String toString(){
        return teamOne.toString() + "; " + teamTwo.toString();
    }
}
