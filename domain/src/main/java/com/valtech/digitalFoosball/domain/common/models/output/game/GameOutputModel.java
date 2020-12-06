package com.valtech.digitalFoosball.domain.common.models.output.game;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.models.output.team.TeamOutputModel;

import java.util.List;

public interface GameOutputModel {
    List<TeamOutputModel> getTeams();

    Team getMatchWinner();

    TeamOutputModel getTeam(Team team);
}
