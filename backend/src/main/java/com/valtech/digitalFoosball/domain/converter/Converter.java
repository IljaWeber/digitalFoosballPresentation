package com.valtech.digitalFoosball.domain.converter;

import com.valtech.digitalFoosball.domain.constants.Team;
import com.valtech.digitalFoosball.domain.gameModes.models.TeamOutput;
import com.valtech.digitalFoosball.domain.gameModes.regular.models.RankedTeamDataModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Converter {
    public static TeamOutput convertToTeamOutput(RankedTeamDataModel team) {
        TeamOutput teamOutput = new TeamOutput();
        teamOutput.setName(team.getName());
        teamOutput.setScore(team.getScore());
        teamOutput.setPlayerOne(team.getNameOfPlayerOne());
        teamOutput.setPlayerTwo(team.getNameOfPlayerTwo());
        teamOutput.setWinsForActualSet(team.getWonSets());

        return teamOutput;
    }

    public static List<TeamOutput> convertListToTeamOutputs(List<RankedTeamDataModel> teams) {
        List<TeamOutput> convertedTeams = new ArrayList<>();

        for (RankedTeamDataModel team : teams) {
            TeamOutput teamOutput = convertToTeamOutput(team);
            convertedTeams.add(teamOutput);
        }

        return convertedTeams;
    }

    public static List<TeamOutput> convertMapToTeamOutputs(Map<Team, RankedTeamDataModel> teams) {
        List<TeamOutput> convertedTeams = new ArrayList<>();

        teams.forEach((k, v) -> convertedTeams.add(convertToTeamOutput(v)));

        return convertedTeams;
    }

}
