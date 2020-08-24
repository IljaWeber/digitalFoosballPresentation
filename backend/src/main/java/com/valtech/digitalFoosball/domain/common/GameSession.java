package com.valtech.digitalFoosball.domain.common;

public class GameSession {
    private int raspberryPiId;
    private int clientId;

    public void registerRaspberryPiWith(int raspberryPiId) {
        this.raspberryPiId = raspberryPiId;
    }

    public boolean registerClientToDesiredRaspberryWith(int raspberryPiId, int clientId) {
        boolean wasRegistered = false;

        if (this.raspberryPiId == raspberryPiId) {
            this.clientId = clientId;
            wasRegistered = true;
        }

        return wasRegistered;
    }
}
