package com.valtech.digitalFoosball.domain.common.session;

import com.valtech.digitalFoosball.domain.IPlayAGame;

import java.util.*;

public class SessionManager {

    @Deprecated
    private final Map<UUID, GameSession> runningSessions;

    private final Map<String, GameSession> currentSessions;

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

    // todo: created on 01.09.20 by iljaweber:  Raspberry Pi which registers should get a name by the backend not vice versa
    @Deprecated
    public void registerRaspberryPiWith(String nameOfRegisteredPi) {
        GameSession session = new GameSession();
        currentSessions.put(nameOfRegisteredPi, session);
    }

    public List<String> getAllAvailableRaspberryPi() {
        return new ArrayList<>(currentSessions.keySet());
    }

    public void registerClientToPlayground(String clientsPlayground, IPlayAGame game) {
        currentSessions.get(clientsPlayground).registerClient(game);
    }

    public IPlayAGame getSession(String clientsPlayground) {
        return currentSessions.get(clientsPlayground).getRules();
    }
}
