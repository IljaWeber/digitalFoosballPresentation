package com.valtech.raspiController;

import com.pi4j.io.gpio.PinEdge;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class PinListener implements GpioPinListenerDigital {
    private int teamNumber;
    private RequestSender requestSender = new RequestSender();
    private Logger logger = LogManager.getLogger(DigitalFoosballRaspPi.class);


    public void setTeamNumber(int teamNumber) {
        this.teamNumber = teamNumber;
    }

    @Override
    public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {

        System.out.println("Pin-state changed");
        if (event.getEdge().equals(PinEdge.RISING)) {
            try {
                logger.info("Team " + teamNumber + "has scored");
                requestSender.sendRaise(teamNumber);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}