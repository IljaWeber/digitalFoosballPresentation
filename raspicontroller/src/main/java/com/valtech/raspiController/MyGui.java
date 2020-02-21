package com.valtech.raspiController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MyGui {
    private JFrame mainFrame;
    private JLabel counterOne;
    private JLabel counterTwo;
    private JPanel controlPanel;

    public MyGui() {
        prepareGUI();
    }

    private void prepareGUI() {
        mainFrame = new JFrame("Java SWING Examples");
        mainFrame.setSize(400, 400);
        mainFrame.setLayout(new GridLayout(3, 1));

        counterOne = new JLabel("0", JLabel.CENTER);
        counterTwo = new JLabel("0", JLabel.CENTER);
        counterOne.setSize(350, 100);
        counterTwo.setSize(350, 100);

        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });

        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        mainFrame.add(counterOne);
        mainFrame.add(controlPanel);
        mainFrame.add(counterTwo);
        mainFrame.setVisible(true);
    }

    public void raiseCounter(int counter) {
        if (counter == 1) {
            String scoreAsString = counterOne.getText();
            int score = Integer.parseInt(scoreAsString);
            score++;
            counterOne.setText(String.valueOf(score));
        } else {
            String scoreAsString = counterTwo.getText();
            int score = Integer.parseInt(scoreAsString);
            score++;
            counterTwo.setText(String.valueOf(score));
        }
    }
}