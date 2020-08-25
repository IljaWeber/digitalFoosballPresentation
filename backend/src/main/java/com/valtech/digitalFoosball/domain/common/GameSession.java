package com.valtech.digitalFoosball.domain.common;

import com.valtech.digitalFoosball.domain.common.exceptions.HardwareExepction;

public class GameSession {
    private int raspberryPiId;
    private int clientId;

    public void registerRaspberryPiWith(int raspberryPiId) {
        this.raspberryPiId = raspberryPiId;
    }

    public boolean registerClientToDesiredRaspberryWith(int raspberryPiId,
                                                        int clientId) throws HardwareExepction {
        boolean clientIsRegistered = false;

        if (this.raspberryPiId == raspberryPiId)
            if (this.clientId == 0) {
                this.clientId = clientId;
                clientIsRegistered = true;
            } else {
                throw new HardwareExepction("Desired Raspberry Pi is already occupied");
            }

        if (this.raspberryPiId == 0) {
            throw new HardwareExepction("Desired Raspberry Pi is not available");
        }

        return clientIsRegistered;
    }
}
