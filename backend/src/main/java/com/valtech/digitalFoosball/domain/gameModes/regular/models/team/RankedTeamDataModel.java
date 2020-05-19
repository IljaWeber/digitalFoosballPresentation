package com.valtech.digitalFoosball.domain.gameModes.regular.models.team;

import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity(name = "team")
public class RankedTeamDataModel extends BaseTeamDataModel {

    @Transient
    private int wonSets;

    public RankedTeamDataModel() {
        super();
        name = "";
    }

    public RankedTeamDataModel(String teamName, String playerOne, String playerTwo) {
        super();
        name = teamName;
        setNameOfPlayerOne(playerOne);
        setNameOfPlayerTwo(playerTwo);
    }

    public int getWonSets() {
        return wonSets;
    }

    public void increaseWonSets() {
        wonSets++;
    }

    public void decreaseWonSets() {
        wonSets--;
    }
}
