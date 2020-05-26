package com.valtech.digitalFoosball.domain.ranked;

import com.valtech.digitalFoosball.api.driven.notification.Observer;
import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.histories.History;
import com.valtech.digitalFoosball.domain.common.models.BaseGameDataModel;
import com.valtech.digitalFoosball.domain.common.models.output.game.GameOutputModel;
import com.valtech.digitalFoosball.domain.common.models.output.game.RegularGameOutputModel;

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
    public void increaseWonSetsFor(Team team) {
        teams.get(team).increaseWonSets();
    }

    @Override
    public boolean setHasAWinner() {
        return setWinner != NO_TEAM;
    }

    @Override
    public void decreaseWonSetsForRecentSetWinner() {
        RankedTeamDataModel setWinningTeam = teams.get(setWinner);
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

    protected void updateObservers() {
        for (Observer observer : observers) {
            GameOutputModel gameOutputModel = new RegularGameOutputModel(this);
            observer.update(gameOutputModel);
        }
    }

    public void changeOver() {
        teams.forEach((teamConstant, dataModel) -> dataModel.changeover());
        setWinner = NO_TEAM;
        history = new History();
    }
}
