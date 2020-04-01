package com.valtech.digitalFoosball.model.output;

import java.util.List;

public class AdHocGameOutput {
    private List<AdHocTeamData> teams;
    private int roundWinner;
    private int matchWinner;

    public List<AdHocTeamData> getTeams() {
        return teams;
    }

    public void setTeams(List<AdHocTeamData> teams) {
        this.teams = teams;
    }

    public int getRoundWinner() {
        return roundWinner;
    }

    public void setRoundWinner(int roundWinner) {
        this.roundWinner = roundWinner;
    }

    public int getMatchWinner() {
        return matchWinner;
    }

    public void setMatchWinner(int matchWinner) {
        this.matchWinner = matchWinner;
    }
}
