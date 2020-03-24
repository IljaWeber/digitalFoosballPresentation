package com.valtech.digitalFoosball.service;

import com.valtech.digitalFoosball.model.internal.TeamDataModel;
import com.valtech.digitalFoosball.model.output.AdHocGameOutput;

import java.util.ArrayList;
import java.util.List;

public class AdHocConverter {
    public List<AdHocGameOutput> convertForOutput(List<TeamDataModel> teams) {
        List<AdHocGameOutput> output = new ArrayList<>();

        for (TeamDataModel team : teams) {
            AdHocGameOutput convertedTeam = new AdHocGameOutput();
            convertedTeam.setName(team.getName());
            convertedTeam.setScore(team.getScore());
            convertedTeam.setWonRounds(team.getWonRounds());
            output.add(convertedTeam);
        }

        return output;
    }
}
