package com.valtech.digitalFoosball.domain.gameModes.timePlay;

import com.valtech.digitalFoosball.domain.gameModes.manipulators.DigitalFoosballGame;
import com.valtech.digitalFoosball.domain.gameModes.models.InitDataModel;
import com.valtech.digitalFoosball.domain.gameModes.models.output.team.TeamOutputModel;
import com.valtech.digitalFoosball.domain.gameModes.regular.models.game.GameDataModel;
import com.valtech.digitalFoosball.domain.gameModes.regular.ranked.RankedInitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Timer;

@Service
public class TimeGame extends DigitalFoosballGame {
    private boolean timeIsOver = false;
    private final TimeGameRules timeGameRules;

    @Autowired
    public TimeGame(RankedInitService initService) {
        super(initService, new TimeGameRules());
        timeGameRules = new TimeGameRules();
    }

    public void setTimer(long timeDuration, GameDataModel dataModel) {
        Timer timer = new Timer();
        timer.schedule(new TaskOfTimer(this, dataModel), timeDuration);
    }

    @Override
    public List<TeamOutputModel> getAllTeamsFromDatabase() {
        return null;
    }

    @Override
    public GameDataModel initGame(InitDataModel initDataModel) {
        int MAX_TIME = 420000;
        GameDataModel dataModel = initService.init(initDataModel);

        setTimer(MAX_TIME, dataModel);

        return dataModel;
    }

    @Override
    public void undoGoal(GameDataModel gameDataModel) {

    }

    public void timeIsOver() {
        timeIsOver = true;
    }

    @Override
    public void changeover(GameDataModel gameDataModel) {
    }

    public boolean isTimeOver() {
        return timeIsOver;
    }
}
