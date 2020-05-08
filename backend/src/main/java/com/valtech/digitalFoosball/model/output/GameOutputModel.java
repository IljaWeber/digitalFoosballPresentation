package com.valtech.digitalFoosball.model.output;

import com.valtech.digitalFoosball.constants.Team;
import com.valtech.digitalFoosball.model.internal.GameDataModel;
import com.valtech.digitalFoosball.model.internal.TeamDataModel;
import com.valtech.digitalFoosball.service.converter.Converter;
import com.valtech.digitalFoosball.service.verifier.MatchWinVerifier;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

import static com.valtech.digitalFoosball.constants.Team.NO_TEAM;

public class GameOutputModel {
    private List<TeamOutput> teams;

    private Team winnerOfSet;

    private Team matchWinner;

    public GameOutputModel(GameDataModel gameDataModel) {
        MatchWinVerifier matchWinVerifier = new MatchWinVerifier();
        SortedMap<Team, TeamDataModel> teamMap = gameDataModel.getTeams();

        this.teams = Converter.convertMapToTeamOutputs(teamMap);
        winnerOfSet = gameDataModel.getSetWinner();
        matchWinner = matchWinVerifier.getMatchWinner(teamMap);
    }

    public GameOutputModel() {
        teams = new ArrayList<>();
        winnerOfSet = NO_TEAM;
        matchWinner = NO_TEAM;
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
