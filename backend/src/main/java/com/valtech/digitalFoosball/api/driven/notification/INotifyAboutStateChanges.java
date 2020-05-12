package com.valtech.digitalFoosball.api.driven.notification;

import com.valtech.digitalFoosball.domain.gameModes.models.GameOutputModel;

public interface INotifyAboutStateChanges extends Observer {
    void notifyAboutStateChange(GameOutputModel gameData);
}
