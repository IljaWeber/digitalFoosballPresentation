package com.valtech.digitalFoosball.domain.common.models.output.game;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.models.output.team.TeamOutputModel;

import java.util.List;

public abstract class BaseGameOutputModel {
    protected List<TeamOutputModel> teams;

    protected Team matchWinner;

    public List<TeamOutputModel> getTeams() {
        return teams;
    }

    public Team getMatchWinner() {
        return matchWinner;
    }

    public TeamOutputModel getTeam(Team team) {
        return teams.get(team.listAssociationNumber());
    }
}
