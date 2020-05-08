package com.valtech.digitalFoosball.service.game;

import com.valtech.digitalFoosball.model.internal.GameDataModel;
import com.valtech.digitalFoosball.service.game.manipulator.TimeGameManipulator;
import com.valtech.digitalFoosball.service.verifier.TimeGameSetWinVerifier;

import java.util.TimerTask;

public class TaskOfTimer extends TimerTask {
    private final TimeGameManipulator timeGame;
    private TimeGameSetWinVerifier timeGameSetWinVerifier;

    public TaskOfTimer(TimeGameManipulator timeGame, GameDataModel dataModel) {
        this.timeGame = timeGame;
    }

    @Override
    public void run() {
        timeGame.timeIsOver();
    }
}
