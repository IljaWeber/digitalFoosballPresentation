package com.valtech.digitalFoosball.api.driven.notification;

import com.valtech.digitalFoosball.domain.gameModes.models.GameOutputModel;

public interface Observer {
    void update(GameOutputModel gameOutputModel);
}
