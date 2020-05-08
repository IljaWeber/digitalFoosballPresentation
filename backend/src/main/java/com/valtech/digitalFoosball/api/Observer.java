package com.valtech.digitalFoosball.api;

import com.valtech.digitalFoosball.model.output.GameOutputModel;

public interface Observer {
    void update(GameOutputModel gameOutputModel);
}
