package com.valtech.digitalFoosball.api.driven.notification;

import com.valtech.digitalFoosball.domain.gameModes.models.output.game.GameOutputModel;

public interface INotifyAboutStateChanges extends Observer {
    void notifyAboutStateChange(GameOutputModel gameData);
}
