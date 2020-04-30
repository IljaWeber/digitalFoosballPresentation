package com.valtech.digitalFoosball.service.game;

import com.valtech.digitalFoosball.constants.Team;
import com.valtech.digitalFoosball.model.GameDataModel;
import com.valtech.digitalFoosball.model.input.InitDataModel;
import com.valtech.digitalFoosball.model.output.TeamOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdHocGame implements Game {

    private TeamManager teamManager;

    private final ScoreManager scoreManager;
    private GameDataModel gameDataModel;

    public AdHocGame() {
        scoreManager = new ScoreManager();
        gameDataModel = new GameDataModel();
    }

    @Autowired
    public AdHocGame(TeamManager teamManager) {
        this.teamManager = teamManager;
        scoreManager = new ScoreManager();

        gameDataModel = new GameDataModel();
    }

    @Override
    public List<TeamOutput> getAllTeamsFromDatabase() {
        return teamManager.getAllTeams();
    }

    @Override
    public GameDataModel initGame(InitDataModel initDataModel) {
        return gameDataModel = teamManager.initAdHocGame();
    }

    @Override
    public void countGoalFor(Team team, GameDataModel gameDataModel) {
        scoreManager.countGoalFor(team, this.gameDataModel);
    }

    @Override
    public void undoGoal(GameDataModel gameDataModel) {
        scoreManager.undoGoal(this.gameDataModel);
    }

    @Override
    public void redoGoal(GameDataModel gameDataModel) {
        scoreManager.redoGoal(this.gameDataModel);
    }

    @Override
    public void changeover(GameDataModel gameDataModel) {
        this.gameDataModel.changeOver();
    }

    @Override
    public void resetMatch(GameDataModel gameDataModel) {
        this.gameDataModel.resetMatchValues();
    }
}
