package com.valtech.digitalFoosball.service.game.modes;

import com.valtech.digitalFoosball.constants.Team;
import com.valtech.digitalFoosball.model.GameDataModel;
import com.valtech.digitalFoosball.model.input.InitDataModel;
import com.valtech.digitalFoosball.model.output.TeamOutput;
import com.valtech.digitalFoosball.service.game.ScoreManager;
import com.valtech.digitalFoosball.service.game.TeamManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.valtech.digitalFoosball.storage.IObtainTeams;

import java.util.List;

@Service
public class AdHocGame implements Game {

    private TeamManager teamManager;

    private final ScoreManager scoreManager;

    @Autowired
    public AdHocGame(TeamManager teamManager) {
        this.teamManager = teamManager;
        scoreManager = new ScoreManager();
    }

    @Override
    public List<TeamOutput> getAllTeamsFromDatabase() {
        return teamManager.getAllTeams();
    }

    @Override
    public GameDataModel initGame(InitDataModel initDataModel) {
        return teamManager.initAdHocGame();
    }

    @Override
    public void countGoalFor(Team team, GameDataModel gameDataModel) {
        scoreManager.countGoalFor(team, gameDataModel);
    }

    @Override
    public void undoGoal(GameDataModel gameDataModel) {
        scoreManager.undoGoal(gameDataModel);
    }

    @Override
    public void redoGoal(GameDataModel gameDataModel) {
        scoreManager.redoGoal(gameDataModel);
    }

    @Override
    public void changeover(GameDataModel gameDataModel) {
        gameDataModel.changeOver();
    }

    @Override
    public void resetMatch(GameDataModel gameDataModel) {
        gameDataModel.resetMatchValues();
    }
}
