package com.valtech.digitalFoosball.model.output;

import java.util.Arrays;
import java.util.List;

public class GameDataModel {

    private List<TeamOutput> teams;

    private int roundWinner;

    private int matchWinner;

    public GameDataModel() {
        teams = Arrays.asList(new TeamOutput(), new TeamOutput());
    }

    public GameDataModel(List<TeamOutput> teams) {
        this.teams = teams;
    }

    public List<TeamOutput> getTeams() {
        return teams;
    }

    public void setTeams(List<TeamOutput> teams) {
        this.teams = teams;
    }

    public int getRoundWinner() {
        return roundWinner;
    }

    public void setRoundWinner(int winner) {
        this.roundWinner = winner;
    }

    public int getMatchWinner() {
        return matchWinner;
    }

    public void setMatchWinner(int matchWinner) {
        this.matchWinner = matchWinner;
    }
}
