package com.valtech.digitalFoosball.factories;

import com.valtech.digitalFoosball.model.input.InitDataModel;
import com.valtech.digitalFoosball.model.internal.TeamDataModel;

public class InitDataModelBuilder {
    public static InitDataModel buildWithTeams(TeamDataModel teamDataModelOne, TeamDataModel teamDataModelTwo) {
        InitDataModel initDataModel = new InitDataModel();
        initDataModel.setTeamOne(teamDataModelOne);
        initDataModel.setTeamTwo(teamDataModelTwo);
        return initDataModel;
    }
}