package com.valtech.digitalFoosball.domain.cases.adhoc;

import javax.persistence.Entity;

@Entity(name = "team")
public class AdHocTeamDataModel extends BaseAdHocTeamDataModel {

    public AdHocTeamDataModel() {
    }

    public AdHocTeamDataModel(String teamName, String playerOne, String playerTwo) {
        super();
        name = teamName;
        setNameOfPlayerOne(playerOne);
        setNameOfPlayerTwo(playerTwo);
    }
}
