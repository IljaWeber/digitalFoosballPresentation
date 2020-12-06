package com.valtech.digitalFoosball.domain.ports;

import com.valtech.digitalFoosball.domain.common.models.output.game.GameOutputModel;

public interface INotifyAboutStateChanges {
    void notifyAboutStateChange(GameOutputModel gameData);
}
