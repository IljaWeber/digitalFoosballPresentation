package com.valtech.digitalFoosball.domain.common;

import com.valtech.digitalFoosball.domain.common.exceptions.HardwareOccupiedException;

public class GameSession {
    private int raspberryPiId;
    private int clientId;

    public GameSession() {
        raspberryPiId = 0;
        clientId = 0;
    }

    public void registerRaspberryPiWith(int raspberryPiId) {
        this.raspberryPiId = raspberryPiId;
    }

    public boolean registerClientToDesiredRaspberryWith(int raspberryPiId,
                                                        int clientId) throws HardwareOccupiedException {

        if (this.raspberryPiId == raspberryPiId && this.clientId == 0) {
            this.clientId = clientId;
            return true;
        }

        if (this.raspberryPiId == raspberryPiId && this.clientId != 0) {
            throw new HardwareOccupiedException("Desired Raspberry Pi is already occupied");
        }

        if (this.raspberryPiId == 0) {
            throw new HardwareOccupiedException("Desired Raspberry Pi is not available");
        }

        return false;
    }
}
