package com.valtech.digitalFoosball.api.driven.notification;

import com.valtech.digitalFoosball.domain.gameModes.models.BaseOutputModel;

public interface Observer {
    void update(BaseOutputModel baseOutputModel);
}
