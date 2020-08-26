package com.valtech.digitalFoosball.domain;

import com.valtech.digitalFoosball.domain.common.GameSession;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SessionManager {
    private final Map<UUID, GameSession> runningSessions;

    public SessionManager() {
        runningSessions = new HashMap<>();
    }

    public UUID registerRaspberryPiWithId() {
        GameSession gameSession = new GameSession();
        UUID uuid = UUID.randomUUID();

        runningSessions.put(uuid, gameSession);

        return uuid;
    }

    public UUID setSession(UUID availableRaspberry, IPlayAGame gameRules) {
        GameSession gameSession = runningSessions.get(availableRaspberry);

        gameSession.registerClient(gameRules);

        return availableRaspberry;
    }

    public IPlayAGame getSession(UUID relatedIdentifier) {
        GameSession gameSession = runningSessions.get(relatedIdentifier);

        return gameSession.getRules();
    }
}
