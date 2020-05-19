package com.valtech.digitalFoosball.domain.gameModes.models.output.game;

import com.valtech.digitalFoosball.domain.constants.Team;
import com.valtech.digitalFoosball.domain.converter.Converter;
import com.valtech.digitalFoosball.domain.gameModes.models.GameDataModel;
import com.valtech.digitalFoosball.domain.gameModes.models.output.team.TeamOutputModel;
import com.valtech.digitalFoosball.domain.gameModes.regular.models.RankedTeamDataModel;
import com.valtech.digitalFoosball.domain.gameModes.regular.ranked.MatchWinVerifier;

import java.util.List;
import java.util.SortedMap;

public abstract class BaseGameOutputModel implements GameOutputModel {

    private List<TeamOutputModel> teams;

    private Team matchWinner;

    public BaseGameOutputModel(GameDataModel gameDataModel) {
        MatchWinVerifier matchWinVerifier = new MatchWinVerifier();
        SortedMap<Team, RankedTeamDataModel> teamMap = gameDataModel.getTeams();

        this.teams = Converter.convertMapToTeamOutputs(teamMap);
        matchWinner = matchWinVerifier.getMatchWinner(teamMap);
    }

    @Override
    public List<TeamOutputModel> getTeams() {
        return teams;
    }

    @Override
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
