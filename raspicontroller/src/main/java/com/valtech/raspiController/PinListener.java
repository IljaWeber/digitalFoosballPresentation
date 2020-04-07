package com.valtech.raspiController;

import com.pi4j.io.gpio.PinEdge;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

import java.io.IOException;

public class PinListener implements GpioPinListenerDigital {
    private int teamNumber;
    private RequestSender requestSender = new RequestSender();

    public void setTeamNumber(int teamNumber) {
        this.teamNumber = teamNumber;
    }

    @Override
    public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {

        if (event.getEdge().equals(PinEdge.RISING)) {
            System.out.println("Pin-state changed for Team " + teamNumber);
            try {
                requestSender.sendRaise(teamNumber);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}