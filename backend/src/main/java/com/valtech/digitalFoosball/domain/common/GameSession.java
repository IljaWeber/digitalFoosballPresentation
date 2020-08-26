package com.valtech.digitalFoosball.domain.common;

import com.valtech.digitalFoosball.domain.IPlayAGame;

public class GameSession {
    private IPlayAGame rules;

    public IPlayAGame getRules() {
        return rules;
    }

    public void registerClient(IPlayAGame gameRules) {
        this.rules = gameRules;
    }
}
