package com.valtech.digitalFoosball.domain.ranked;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.histories.History;
import com.valtech.digitalFoosball.domain.common.models.BaseGameDataModel;
import com.valtech.digitalFoosball.domain.common.models.TeamDataModel;

import java.util.ArrayList;

import static com.valtech.digitalFoosball.domain.common.constants.GameMode.NO_ACTIVE_GAME;
import static com.valtech.digitalFoosball.domain.common.constants.Team.NO_TEAM;

public class RankedGameDataModel extends BaseGameDataModel {
    private Team setWinner;

    public RankedGameDataModel() {
        super();
        setWinner = NO_TEAM;
        gameMode = NO_ACTIVE_GAME;
    }

    @Override
    protected void updateObservers() {
        // not implemented yet
    }

    @Override
    public void increaseWonSetsFor(Team team) {
        teams.get(team).increaseWonSets();
    }

    @Override
    public void decreaseWonSetsForRecentSetWinner() {
        TeamDataModel setWinningTeam = teams.get(setWinner);
        setWinningTeam.decreaseWonSets();
    }

    @Override
    public Team getSetWinner() {
        return setWinner;
    }

    @Override
    public void setSetWinner(Team setWinner) {
        this.setWinner = setWinner;
    }

    @Override
    public void resetMatch() {
        teams.clear();
        setWinner = NO_TEAM;
        history = new History();
        observers = new ArrayList<>();

    }

    public void changeOver() {
        teams.forEach((teamConstant, dataModel) -> dataModel.changeover());
        setWinner = NO_TEAM;
        history = new History();
    }

}
