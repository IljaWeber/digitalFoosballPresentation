package com.valtech.digitalFoosball.domain.common.models;

import com.valtech.digitalFoosball.domain.SessionIdentifier;
import com.valtech.digitalFoosball.domain.common.constants.GameMode;

import java.util.Arrays;
import java.util.List;

public class InitDataModel {
    private TeamDataModel teamOne;
    private TeamDataModel teamTwo;
    private GameMode mode;
    private SessionIdentifier identifier;

    public InitDataModel() {
        teamOne = new TeamDataModel();
        teamTwo = new TeamDataModel();
    }

    public InitDataModel(TeamDataModel teamDataModelOne, TeamDataModel teamDataModelTwo) {
        teamOne = teamDataModelOne;
        teamTwo = teamDataModelTwo;
    }

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

    public String toString() {
        return teamOne.toString() + "; " + teamTwo.toString();
    }

    public void setMode(GameMode mode) {
        this.mode = mode;

    }

    public GameMode getMode() {
        return mode;
    }

    public void setIdentifier(SessionIdentifier identifier) {
        this.identifier = identifier;
    }

    public SessionIdentifier getIdentifier() {
        return identifier;
    }
}
