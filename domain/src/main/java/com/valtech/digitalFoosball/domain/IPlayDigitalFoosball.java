package com.valtech.digitalFoosball.domain;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import com.valtech.digitalFoosball.domain.common.models.output.game.GameOutputModel;
import com.valtech.digitalFoosball.domain.common.session.SessionIdentifier;

import java.util.UUID;

public interface IPlayDigitalFoosball {
    void initGame(InitDataModel initDataModel, UUID raspberryId);

    void countGoalFor(Team team, UUID relatedIdentifier);

    void changeover(UUID relatedIdentifier);

    void undoGoal(UUID relatedIdentifier);

    void redoGoal(UUID relatedIdentifier);

    void resetMatch(UUID relatedIdentifier);

    GameOutputModel getGameData(UUID relatedIdentifier);

    SessionIdentifier registerAvailableRaspBerry();
}
