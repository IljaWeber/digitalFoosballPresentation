package com.valtech.digitalFoosball.domain.gameModes;

import com.valtech.digitalFoosball.domain.InitialService;
import com.valtech.digitalFoosball.domain.constants.GameMode;
import com.valtech.digitalFoosball.domain.gameModes.models.GameDataModel;
import com.valtech.digitalFoosball.domain.gameModes.models.InitDataModel;
import com.valtech.digitalFoosball.domain.gameModes.models.TeamOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InitService {
    private final InitServiceProvider provider;

    @Autowired
    public InitService(InitServiceProvider provider) {
        this.provider = provider;
    }

    public GameDataModel init(InitDataModel initDataModel) {
        GameMode mode = initDataModel.getMode();

        InitialService initService = provider.getInitService(mode);

        GameDataModel gameData = initService.init(initDataModel);

        gameData.setGameMode(mode);

        return gameData;
    }

    public List<TeamOutput> getAllTeamsFromDatabase() {
        InitialService initService = provider.getInitService(GameMode.RANKED);

        return initService.getAllTeams();
    }
}
