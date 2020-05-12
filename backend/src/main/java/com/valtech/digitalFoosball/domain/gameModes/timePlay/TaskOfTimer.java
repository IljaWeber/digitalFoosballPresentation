package com.valtech.digitalFoosball.domain.gameModes.timePlay;

import com.valtech.digitalFoosball.domain.gameModes.models.GameDataModel;

import java.util.TimerTask;

public class TaskOfTimer extends TimerTask {
    private final TimeGameManipulator timeGame;
    private TimeGameWinApprover timeGameWinApprover;

    public TaskOfTimer(TimeGameManipulator timeGame, GameDataModel dataModel) {
        this.timeGame = timeGame;
    }

    @Override
    public void run() {
        timeGame.timeIsOver();
    }
}
