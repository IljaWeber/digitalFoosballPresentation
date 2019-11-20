package com.valtech.raspiController;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.RaspiPin;

import java.util.Scanner;

public class DigitalFoosballRaspPi {
    public static void main(String[] args) {
        final GpioController gpio = GpioFactory.getInstance();
        Scanner scanner = new Scanner(System.in);

        GpioPinDigitalInput pinOne = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02);
        GpioPinDigitalInput pinTwo = gpio.provisionDigitalInputPin(RaspiPin.GPIO_03);

        PinListener listenerOne = new PinListener();
        listenerOne.setTeamNumber(1);
        pinOne.addListener(listenerOne);

        PinListener listenerTwo = new PinListener();
        listenerTwo.setTeamNumber(2);
        pinTwo.addListener(listenerTwo);

        scanner.next();
    }
}