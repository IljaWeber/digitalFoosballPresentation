package com.valtech.digitalFoosball.domain.common;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GameSessionShould {
    @Test
    public void register_client_to_desired_raspberry_pi() {
        GameSession session = new GameSession();
        int raspBerryPiId = 1;
        int clientId = 1;
        session.registerRaspberryPiWith(raspBerryPiId);

        boolean clientWasRegistered = session.registerClientToDesiredRaspberryWith(clientId, raspBerryPiId);

        assertThat(clientWasRegistered).isTrue();
    }
}
