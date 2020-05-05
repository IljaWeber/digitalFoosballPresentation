package com.valtech.digitalFoosball.service.game.modes;

import com.valtech.digitalFoosball.model.GameDataModel;
import com.valtech.digitalFoosball.model.input.InitDataModel;
import com.valtech.digitalFoosball.service.game.TeamManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdHocGameManipulator extends GameManipulator {

    private final TeamManager teamManager;

    @Autowired
    public AdHocGameManipulator(TeamManager teamManager) {
        super(teamManager);
        this.teamManager = teamManager;
    }

    @Override
    public GameDataModel initGame(InitDataModel initDataModel) {
        return teamManager.initAdHocGame();
    }
}