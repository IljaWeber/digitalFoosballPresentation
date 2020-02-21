package com.valtech.raspiController;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.RaspiPin;

public class DigitalFoosballRaspPi {

    public static void main(String[] args) {
        MyGui myGui = new MyGui();

        final GpioController gpio = GpioFactory.getInstance();

        GpioPinDigitalInput pinOne = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02);
        GpioPinDigitalInput pinTwo = gpio.provisionDigitalInputPin(RaspiPin.GPIO_03);

        PinListener listenerOne = new PinListener(myGui);

        listenerOne.setTeamNumber(1);
        pinOne.addListener(listenerOne);

        PinListener listenerTwo = new PinListener(myGui);
        listenerTwo.setTeamNumber(2);
        pinTwo.addListener(listenerTwo);
    }
}