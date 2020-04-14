package com.valtech.digitalFoosball.factories;

import com.valtech.digitalFoosball.model.internal.TeamDataModel;

public class TeamDataModelBuilder {

    public static TeamDataModel buildWithNames(String teamName, String playerOne, String playerTwo) {
        TeamDataModel teamDataModel = new TeamDataModel();
        teamDataModel.setName(teamName);
        teamDataModel.setNameOfPlayerOne(playerOne);
        teamDataModel.setNameOfPlayerTwo(playerTwo);

        return teamDataModel;
    }

    public static TeamDataModel buildWithAdHocPlayerNames(String teamName) {
        TeamDataModel teamDataModel = new TeamDataModel();
        teamDataModel.setName(teamName);
        teamDataModel.setNameOfPlayerOne("Goalie");
        teamDataModel.setNameOfPlayerTwo("Stiker");

        return teamDataModel;
    }

}
