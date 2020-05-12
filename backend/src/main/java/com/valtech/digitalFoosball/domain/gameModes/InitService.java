package com.valtech.digitalFoosball.domain.gameModes;

import com.valtech.digitalFoosball.domain.InitialService;
import com.valtech.digitalFoosball.domain.gameModes.models.GameDataModel;
import com.valtech.digitalFoosball.domain.gameModes.models.InitDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InitService {
    private final InitServiceProvider provider;

    @Autowired
    public InitService(InitServiceProvider provider) {
        this.provider = provider;
    }

    public GameDataModel init(InitDataModel initDataModel) {
        InitialService initService = provider.getInitService(initDataModel);

        GameDataModel gameData = initService.init(initDataModel);
        gameData.setGameMode(initDataModel.getMode());

        return gameData;
    }
}
