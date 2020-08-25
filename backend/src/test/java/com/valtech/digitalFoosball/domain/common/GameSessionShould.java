package com.valtech.digitalFoosball.domain.common;

import com.valtech.digitalFoosball.domain.common.exceptions.HardwareExepction;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;

public class GameSessionShould {

    @Test
    public void register_client_to_desired_raspberry_pi() throws HardwareExepction {
        GameSession session = new GameSession();
        int raspBerryPiId = 1;
        int clientId = 1;
        session.registerRaspberryPiWith(raspBerryPiId);

        boolean clientWasRegistered = session.registerClientToDesiredRaspberryWith(clientId, raspBerryPiId);

        assertThat(clientWasRegistered).isTrue();
    }

    @Test
    public void notify_that_the_desired_raspberry_pi_is_already_in_use() throws HardwareExepction {
        GameSession session = new GameSession();
        int raspBerryPiId = 1;
        int clientId = 1;
        session.registerRaspberryPiWith(raspBerryPiId);
        session.registerClientToDesiredRaspberryWith(raspBerryPiId, clientId);

        HardwareExepction actual = assertThrows(HardwareExepction.class, () ->
                session.registerClientToDesiredRaspberryWith(raspBerryPiId, clientId));

        assertThat(actual.getMessage()).isEqualTo("Desired Raspberry Pi is already occupied");
    }

    @Test
    public void notify_that_the_desired_raspberry_pi_is_not_available() {
        GameSession session = new GameSession();
        int raspBerryPiId = 1;
        int clientId = 1;

        HardwareExepction actual = assertThrows(HardwareExepction.class, () ->
                session.registerClientToDesiredRaspberryWith(raspBerryPiId, clientId));

        assertThat(actual.getMessage()).isEqualTo("Desired Raspberry Pi is not available");
    }
}
