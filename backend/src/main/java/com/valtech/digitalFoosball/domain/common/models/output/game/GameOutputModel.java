package com.valtech.digitalFoosball.domain.common.models.output.game;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.models.output.team.TeamOutputModel;

import java.util.List;

public interface GameOutputModel {
    List<TeamOutputModel> getTeams();

    Team getWinnerOfSet();

    Team getMatchWinner();

    void setMatchWinner(Team matchWinner);

    TeamOutputModel getTeam(Team team);
}
