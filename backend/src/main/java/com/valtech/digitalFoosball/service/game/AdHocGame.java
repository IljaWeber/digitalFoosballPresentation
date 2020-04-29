package com.valtech.digitalFoosball.service.game;

import com.valtech.digitalFoosball.api.IUpdateClient;
import com.valtech.digitalFoosball.constants.Team;
import com.valtech.digitalFoosball.model.GameDataModel;
import com.valtech.digitalFoosball.model.input.InitDataModel;
import com.valtech.digitalFoosball.model.output.GameOutputModel;
import com.valtech.digitalFoosball.model.output.TeamOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdHocGame implements Game {

    @Autowired
    private TeamManager teamManager;

    @Autowired
    private IUpdateClient clientUpdater;

    private final ScoreManager scoreManager;
    private GameDataModel gameDataModel;

    public AdHocGame() {
        gameDataModel = new GameDataModel();
        scoreManager = new ScoreManager();
    }

    @Override
    public List<TeamOutput> getAllTeamsFromDatabase() {
        return teamManager.getAllTeams();
    }

    @Override
    public void initGame(InitDataModel initDataModel) {
        gameDataModel = teamManager.initAdHocGame();
    }

    @Override
    public void countGoalFor(Team team) {
        scoreManager.countGoalFor(team, gameDataModel);
        clientUpdater.updateClientWith(getGameData());
    }

    @Override
    public void undoGoal() {
        scoreManager.undoGoal(gameDataModel);
    }

    @Override
    public void redoGoal() {
        scoreManager.redoGoal(gameDataModel);
    }

    @Override
    public void changeover() {
        gameDataModel.setScoresToZero();
        scoreManager.clearHistory();
    }

    @Override
    public void resetMatch() {
        gameDataModel.resetMatchValues();
        scoreManager.clearHistory();
    }

    @Override
    public GameOutputModel getGameData() {
        return new GameOutputModel(gameDataModel);
    }
}
