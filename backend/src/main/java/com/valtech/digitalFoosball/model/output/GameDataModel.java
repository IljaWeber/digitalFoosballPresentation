package com.valtech.digitalFoosball.model.output;

import com.valtech.digitalFoosball.constants.Team;
import com.valtech.digitalFoosball.model.internal.TeamDataModel;
import com.valtech.digitalFoosball.service.converter.Converter;
import com.valtech.digitalFoosball.service.verifier.MatchWinVerifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameDataModel {

    private List<TeamOutput> teams;

    private Team winnerOfSet;

    private Team matchWinner;

    public GameDataModel() {
        teams = new ArrayList<>();
        winnerOfSet = Team.NO_TEAM;
        matchWinner = Team.NO_TEAM;
    }

    public GameDataModel(Map<Team, TeamDataModel> teams, Team setWinner) {
        MatchWinVerifier matchWinVerifier = new MatchWinVerifier();
        List<TeamOutput> teamOutputs = Converter.convertMapToTeamOutputs(teams);

        this.teams = teamOutputs;

        winnerOfSet = setWinner;

        matchWinner = matchWinVerifier.getMatchWinner(teams);
    }

    public List<TeamOutput> getTeams() {
        return teams;
    }

    public void setTeams(List<TeamOutput> teams) {
        this.teams = teams;
    }

    public Team getWinnerOfSet() {
        return winnerOfSet;
    }

    public void setWinnerOfSet(Team winner) {
        this.winnerOfSet = winner;
    }

    public Team getMatchWinner() {
        return matchWinner;
    }

    public void setMatchWinner(Team matchWinner) {
        this.matchWinner = matchWinner;
    }

    public TeamOutput getTeam(Team team) {
        return teams.get(team.listAssociationNumber());
    }
}
