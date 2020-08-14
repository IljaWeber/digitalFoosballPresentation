package com.valtech.raspiController;

import com.pi4j.io.gpio.*;

public class DigitalFoosballRaspPi {

    public static void main(String[] args) throws InterruptedException {
        final GpioController gpio = GpioFactory.getInstance();

        GpioPinDigitalInput pinOne = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02, PinPullResistance.OFF);
        GpioPinDigitalInput pinTwo = gpio.provisionDigitalInputPin(RaspiPin.GPIO_03);

        PinListener listenerOne = new PinListener();

        listenerOne.setTeamNumber(1);
        pinOne.addListener(listenerOne);

        PinListener listenerTwo = new PinListener();
        listenerTwo.setTeamNumber(2);
        pinTwo.addListener(listenerTwo);

        System.out.println("Controller started");

        // todo: created on 14.08.20 by iljaweber: remove infinite while loop with java 8 concurrency
        while (true) {
            Thread.sleep(10000);
        }
    }
}
