package com.valtech.digitalFoosball.domain.converter;

import com.valtech.digitalFoosball.domain.constants.Team;
import com.valtech.digitalFoosball.domain.gameModes.models.RegularTeamOutputModel;
import com.valtech.digitalFoosball.domain.gameModes.models.TeamOutputModel;
import com.valtech.digitalFoosball.domain.gameModes.regular.models.RankedTeamDataModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Converter {
    public static TeamOutputModel convertToTeamOutput(RankedTeamDataModel team) {
        RegularTeamOutputModel teamOutputModel = new RegularTeamOutputModel();
        teamOutputModel.setName(team.getName());
        teamOutputModel.setScore(team.getScore());
        teamOutputModel.setPlayerOne(team.getNameOfPlayerOne());
        teamOutputModel.setPlayerTwo(team.getNameOfPlayerTwo());
        teamOutputModel.setWinsForActualSet(team.getWonSets());

        return teamOutputModel;
    }

    public static List<TeamOutputModel> convertListToTeamOutputs(List<RankedTeamDataModel> teams) {
        List<TeamOutputModel> convertedTeams = new ArrayList<>();

        for (RankedTeamDataModel team : teams) {
            TeamOutputModel teamOutputModel = convertToTeamOutput(team);
            convertedTeams.add(teamOutputModel);
        }

        return convertedTeams;
    }

    public static List<TeamOutputModel> convertMapToTeamOutputs(Map<Team, RankedTeamDataModel> teams) {
        List<TeamOutputModel> convertedTeams = new ArrayList<>();

        teams.forEach((k, v) -> convertedTeams.add(convertToTeamOutput(v)));

        return convertedTeams;
    }

}
