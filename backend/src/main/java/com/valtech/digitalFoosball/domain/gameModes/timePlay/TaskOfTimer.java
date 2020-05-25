package com.valtech.digitalFoosball.domain.gameModes.timePlay;

import com.valtech.digitalFoosball.domain.gameModes.regular.models.game.GameDataModel;

import java.util.TimerTask;

public class TaskOfTimer extends TimerTask {
    private final TimeGame timeGame;
    private TimeGameRules timeGameRules;

    public TaskOfTimer(TimeGame timeGame, GameDataModel dataModel) {
        this.timeGame = timeGame;
    }

    @Override
    public void run() {
        timeGame.timeIsOver();
    }
}
