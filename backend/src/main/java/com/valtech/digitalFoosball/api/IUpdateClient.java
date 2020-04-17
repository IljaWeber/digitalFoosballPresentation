package com.valtech.digitalFoosball.api;

import com.valtech.digitalFoosball.model.output.GameDataModel;

public interface IUpdateClient {
    void updateClientWith(GameDataModel gameData);
}
