package com.valtech.digitalFoosball.domain.common.models;

import com.valtech.digitalFoosball.domain.common.constants.GameMode;
import com.valtech.digitalFoosball.domain.ranked.RankedTeamDataModel;

import java.util.Arrays;
import java.util.List;

public class InitDataModel {
    private RankedTeamDataModel teamOne;
    private RankedTeamDataModel teamTwo;
    private GameMode mode;

    public InitDataModel() {
    }

    public InitDataModel(RankedTeamDataModel teamDataModelOne, RankedTeamDataModel teamDataModelTwo) {
        teamOne = teamDataModelOne;
        teamTwo = teamDataModelTwo;
    }

    public List<RankedTeamDataModel> getTeams() {
        return Arrays.asList(teamOne, teamTwo);
    }

    public void setTeams(List<RankedTeamDataModel> teams) {
        teamOne = teams.get(0);
        teamTwo = teams.get(1);
    }

    public RankedTeamDataModel getTeamOne() {
        return teamOne;
    }

    public void setTeamOne(RankedTeamDataModel teamOne) {
        this.teamOne = teamOne;
    }

    public RankedTeamDataModel getTeamTwo() {
        return teamTwo;
    }

    public void setTeamTwo(RankedTeamDataModel teamTwo) {
        this.teamTwo = teamTwo;
    }

    public String toString() {
        return teamOne.toString() + "; " + teamTwo.toString();
    }

    public GameMode getMode() {
        return mode;
    }

    public void setMode(GameMode mode) {
        this.mode = mode;
    }
}
