package com.valtech.digitalFoosball.model.output;

import com.valtech.digitalFoosball.constants.Team;

import java.util.Arrays;
import java.util.List;

public class GameDataModel {

    private List<TeamOutput> teams;

    private int winnerOfSet;

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

    public int getWinnerOfSet() {
        return winnerOfSet;
    }

    public void setWinnerOfSet(int winner) {
        this.winnerOfSet = winner;
    }

    public int getMatchWinner() {
        return matchWinner;
    }

    public void setMatchWinner(int matchWinner) {
        this.matchWinner = matchWinner;
    }

    public TeamOutput getTeam(Team team) {
        return teams.get(team.value() - 1);
    }
}
