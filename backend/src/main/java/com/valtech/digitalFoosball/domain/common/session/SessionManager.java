package com.valtech.digitalFoosball.domain.common.session;

import com.valtech.digitalFoosball.domain.IPlayAGame;

import java.util.*;

public class SessionManager {
    private final Map<UUID, GameSession> runningSessions;
    private final HashMap<String, GameSession> currentSessions;

    public SessionManager() {
        runningSessions = new HashMap<>();
        currentSessions = new HashMap<>();
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

    public void registerRaspberryPiWithName(String nameOfRegisteredPi) {
        GameSession session = new GameSession();
        currentSessions.put(nameOfRegisteredPi, session);
    }

    public List<String> getAllAvailableRaspberryPi() {
        return new ArrayList<>(currentSessions.keySet());
    }
}
