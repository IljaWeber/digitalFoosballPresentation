package com.valtech.digitalFoosball.domain.gameModes.models;

import com.valtech.digitalFoosball.domain.constants.Team;
import com.valtech.digitalFoosball.domain.converter.Converter;
import com.valtech.digitalFoosball.domain.gameModes.regular.models.RankedTeamDataModel;
import com.valtech.digitalFoosball.domain.gameModes.regular.ranked.MatchWinVerifier;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

import static com.valtech.digitalFoosball.domain.constants.Team.NO_TEAM;

public class GameOutputModel {
    private List<TeamOutput> teams;

    private Team winnerOfSet;

    private Team matchWinner;

    public GameOutputModel(GameDataModel gameDataModel) {
        MatchWinVerifier matchWinVerifier = new MatchWinVerifier();
        SortedMap<Team, RankedTeamDataModel> teamMap = gameDataModel.getTeams();

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
