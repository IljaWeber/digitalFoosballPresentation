package com.valtech.digitalFoosball.domain.common.models.output.game;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.converter.Converter;
import com.valtech.digitalFoosball.domain.common.models.output.team.TeamOutputModel;
import com.valtech.digitalFoosball.domain.ranked.GameDataModel;
import com.valtech.digitalFoosball.domain.ranked.MatchWinVerifier;
import com.valtech.digitalFoosball.domain.ranked.RankedTeamDataModel;

import java.util.List;
import java.util.SortedMap;
import java.util.Stack;

public abstract class BaseGameOutputModel implements GameOutputModel {
    private List<TeamOutputModel> teams;

    private Team matchWinner;

    public BaseGameOutputModel(GameDataModel gameDataModel) {
        MatchWinVerifier matchWinVerifier = new MatchWinVerifier();
        SortedMap<Team, RankedTeamDataModel> teamMap = gameDataModel.getTeams();
        Stack<Team> allSetWins = gameDataModel.getAllWins();

        this.matchWinner = matchWinVerifier.getMatchWinner(allSetWins);
        this.teams = Converter.convertMapToTeamOutputs(teamMap);
    }

    @Override
    public List<TeamOutputModel> getTeams() {
        return teams;
    }

    public void setTeams(List<TeamOutputModel> teams) {
        this.teams = teams;
    }

    @Override
    public Team getMatchWinner() {
        return matchWinner;
    }

    @Override
    public void setMatchWinner(Team matchWinner) {
        this.matchWinner = matchWinner;
    }

    @Override
    public TeamOutputModel getTeam(Team team) {
        return teams.get(team.listAssociationNumber());
    }
}
