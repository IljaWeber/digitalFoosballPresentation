package com.valtech.digitalFoosball.api.driven.notification;

import com.valtech.digitalFoosball.domain.common.models.output.game.GameOutputModel;

public interface Observer {
    void update(GameOutputModel gameOutputModel);
}
