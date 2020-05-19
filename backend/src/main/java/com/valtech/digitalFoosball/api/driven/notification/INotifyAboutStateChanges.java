package com.valtech.digitalFoosball.api.driven.notification;

import com.valtech.digitalFoosball.domain.gameModes.models.BaseOutputModel;

public interface INotifyAboutStateChanges extends Observer {
    void notifyAboutStateChange(BaseOutputModel gameData);
}
