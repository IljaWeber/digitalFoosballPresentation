package com.valtech.digitalFoosball.domain.common;

import com.valtech.digitalFoosball.domain.common.models.GameDataModel;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;

public interface IInitializeGames {
    GameDataModel init(InitDataModel initDataModel);
}
