package com.valtech.digitalFoosball.domain.gameModes.models.output.game;

import com.valtech.digitalFoosball.domain.constants.Team;
import com.valtech.digitalFoosball.domain.gameModes.models.output.team.TeamOutputModel;

import java.util.List;

public interface GameOutputModel {
    List<TeamOutputModel> getTeams();

    void setTeams(List<TeamOutputModel> teams);

    Team getWinnerOfSet();

    Team getMatchWinner();

    void setMatchWinner(Team matchWinner);

    TeamOutputModel getTeam(Team team);
}
