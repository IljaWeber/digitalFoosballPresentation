package com.valtech.digitalFoosball.service.verifier.setwin;

import com.valtech.digitalFoosball.constants.Team;
import com.valtech.digitalFoosball.model.GameDataModel;

public interface GameSetVerifier {
    void approveWin(GameDataModel gameDataModel);

    Team getWinner(GameDataModel gameDataModel);
}
