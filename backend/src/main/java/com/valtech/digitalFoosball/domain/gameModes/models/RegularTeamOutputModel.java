package com.valtech.digitalFoosball.domain.gameModes.models;

public class RegularTeamOutputModel extends BaseTeamOutputModel {
    private int setWins;

    public void setWinsForActualSet(int setWins) {
        this.setWins = setWins;
    }
}
