package com.valtech.digitalFoosball.api;

import com.valtech.digitalFoosball.model.output.GameOutputModel;

public interface IUpdateClient {
    void updateClientWith(GameOutputModel gameData);
}
