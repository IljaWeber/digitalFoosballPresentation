package com.valtech.digitalFoosball.api;

import com.valtech.digitalFoosball.model.output.GameOutputModel;

public interface INotifyAboutStateChanges {
    void notifyAboutStateChange(GameOutputModel gameData);
}