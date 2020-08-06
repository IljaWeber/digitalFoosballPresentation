package com.valtech.digitalFoosball.api;

import com.valtech.digitalFoosball.domain.common.models.output.game.GameOutputModel;

public interface INotifyAboutStateChanges {
    void notifyAboutStateChange(GameOutputModel gameData);
}