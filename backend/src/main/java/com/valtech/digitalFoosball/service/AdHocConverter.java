package com.valtech.digitalFoosball.service;

import com.valtech.digitalFoosball.model.internal.TeamDataModel;
import com.valtech.digitalFoosball.model.output.AdHocTeamData;

import java.util.ArrayList;
import java.util.List;

public class AdHocConverter {
    public List<AdHocTeamData> convertForOutput(List<TeamDataModel> teams) {
        List<AdHocTeamData> output = new ArrayList<>();

        for (TeamDataModel team : teams) {
            AdHocTeamData convertedTeam = new AdHocTeamData();
            convertedTeam.setName(team.getName());
            convertedTeam.setScore(team.getScore());
            convertedTeam.setWonRounds(team.getWonRounds());
            output.add(convertedTeam);
        }

        return output;
    }
}
