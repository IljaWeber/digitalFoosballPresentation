package com.valtech.digitalFoosball.model.output;

import java.util.Arrays;
import java.util.List;

public class GameDataModel {

    private List<TeamOutput> teams;

    private int winnerOfActualSet;

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

    public int getWinnerOfActualSet() {
        return winnerOfActualSet;
    }

    public void setWinnerOfActualSet(int winner) {
        this.winnerOfActualSet = winner;
    }

    public int getMatchWinner() {
        return matchWinner;
    }

    public void setMatchWinner(int matchWinner) {
        this.matchWinner = matchWinner;
    }
}
