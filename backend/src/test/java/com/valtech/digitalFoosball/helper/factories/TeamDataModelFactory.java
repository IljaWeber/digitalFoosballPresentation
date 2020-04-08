package com.valtech.digitalFoosball.helper.factories;

import com.valtech.digitalFoosball.model.internal.TeamDataModel;

public class TeamDataModelFactory {

    public TeamDataModel getInstanceWithNames(String teamName, String playerOne, String playerTwo){
        TeamDataModel teamDataModel = new TeamDataModel();
        teamDataModel.setName(teamName);
        teamDataModel.setNameOfPlayerOne(playerOne);
        teamDataModel.setNameOfPlayerTwo(playerTwo);

        return teamDataModel;
    }
}
