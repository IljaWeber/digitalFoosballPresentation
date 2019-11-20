package com.valtech.digitalFoosball.service;

import com.valtech.digitalFoosball.model.internal.TeamDataModel;
import com.valtech.digitalFoosball.model.output.TeamOutput;

public class Converter {
    public TeamOutput convertToTeamOutput(TeamDataModel team) {
        TeamOutput teamOutput = new TeamOutput();
        teamOutput.setName(team.getName());
        teamOutput.setScore(team.getScore());
        teamOutput.setPlayerOne(team.getNameOfPlayerOne());
        teamOutput.setPlayerTwo(team.getNameOfPlayerTwo());
        teamOutput.setRoundWins(team.getWonRounds());

        return teamOutput;
    }
}