package com.valtech.digitalFoosball.domain.common.models.output.team;

public class RegularTeamOutputModel extends BaseTeamOutputModel {
    private int setWins;

    public void setWinsForActualSet(int setWins) {
        this.setWins = setWins;
    }
}
