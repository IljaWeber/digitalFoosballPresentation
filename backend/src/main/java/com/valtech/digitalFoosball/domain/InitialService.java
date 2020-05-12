package com.valtech.digitalFoosball.domain;

import com.valtech.digitalFoosball.domain.gameModes.models.GameDataModel;
import com.valtech.digitalFoosball.domain.gameModes.models.InitDataModel;

public interface InitialService {
    GameDataModel init(InitDataModel initDataModel);
}
