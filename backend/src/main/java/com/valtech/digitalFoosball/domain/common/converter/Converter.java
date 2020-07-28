package com.valtech.digitalFoosball.domain.common.converter;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.models.output.team.RegularTeamOutputModel;
import com.valtech.digitalFoosball.domain.common.models.output.team.TeamOutputModel;
import com.valtech.digitalFoosball.domain.ranked.TeamDataModel;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

// TODO: 15.07.20 m.huber move
public class Converter {
    public static TeamOutputModel convertToTeamOutput(TeamDataModel team) {
        RegularTeamOutputModel teamOutputModel = new RegularTeamOutputModel();
        teamOutputModel.setName(team.getName());
        teamOutputModel.setPlayerOne(team.getNameOfPlayerOne());
        teamOutputModel.setPlayerTwo(team.getNameOfPlayerTwo());

        return teamOutputModel;
    }

    public static List<TeamOutputModel> convertListToTeamOutputs(List<TeamDataModel> teams) {
        List<TeamOutputModel> convertedTeams = new ArrayList<>();

        for (TeamDataModel team : teams) {
            TeamOutputModel teamOutputModel = convertToTeamOutput(team);
            convertedTeams.add(teamOutputModel);
        }

        return convertedTeams;
    }

    public static List<TeamOutputModel> convertMapToTeamOutputs(SortedMap<Team, TeamDataModel> teams) {
        List<TeamOutputModel> convertedTeams = new ArrayList<>();

        teams.forEach((k, v) -> convertedTeams.add(convertToTeamOutput(v)));

        return convertedTeams;
    }

}
