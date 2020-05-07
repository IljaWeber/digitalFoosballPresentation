package com.valtech.digitalFoosball.service.verifier.setwin;

import com.valtech.digitalFoosball.constants.GameMode;

import static com.valtech.digitalFoosball.constants.GameMode.AD_HOC;
import static com.valtech.digitalFoosball.constants.GameMode.RANKED;

public abstract class SetWinVerifierProvider {

    public static WonSetVerifier createVerifier(GameMode mode) {
        if (mode == RANKED || mode == AD_HOC) {
            return new RegularGameSetWinVerifier();
        }

        return new TimeGameSetWinVerifier();
    }
}
