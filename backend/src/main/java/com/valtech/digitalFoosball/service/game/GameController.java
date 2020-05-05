package com.valtech.digitalFoosball.service.game;

import com.valtech.digitalFoosball.api.INotifyAboutStateChanges;
import com.valtech.digitalFoosball.constants.GameMode;
import com.valtech.digitalFoosball.constants.Team;
import com.valtech.digitalFoosball.model.GameDataModel;
import com.valtech.digitalFoosball.model.input.InitDataModel;
import com.valtech.digitalFoosball.model.output.GameOutputModel;
import com.valtech.digitalFoosball.model.output.TeamOutput;
import com.valtech.digitalFoosball.service.game.modes.GameManipulator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameController implements IReactToGoals, IReactToPlayerCommands {

    private final INotifyAboutStateChanges notifier;
    private final GameManipulatorProvider gameManipulatorProvider;
    private GameDataModel gameDataModel = new GameDataModel();
    private GameManipulator gameManipulator;

    @Autowired
    public GameController(GameManipulatorProvider gameManipulatorProvider, INotifyAboutStateChanges notifier) {
        this.gameManipulatorProvider = gameManipulatorProvider;
        this.notifier = notifier;
    }

    public List<TeamOutput> getAllTeamsFromDatabase() {
        return gameManipulator.getAllTeamsFromDatabase();
    }

    public GameOutputModel getGameData() {
        if (gameDataModel.isEmpty()) {
            return new GameOutputModel();
        }

        return new GameOutputModel(gameDataModel);
    }

    public void initGame(InitDataModel initDataModel, GameMode gameMode) {
        this.gameManipulator = gameManipulatorProvider.getGameManipulator(gameMode);
        gameDataModel = gameManipulator.initGame(initDataModel);
    }

    public void countGoalFor(Team team) {
        gameManipulator.countGoalFor(team, gameDataModel);

        notifyAboutStateChange();
    }

    private void notifyAboutStateChange() {
        GameOutputModel gameData = getGameData();
        notifier.notifyAboutStateChange(gameData);
    }

    public void undoGoal() {
        gameManipulator.undoGoal(gameDataModel);
    }

    public void redoGoal() {
        gameManipulator.redoGoal(gameDataModel);
    }

    public void changeover() {
        gameManipulator.changeover(gameDataModel);
    }

    public void resetMatch() {
        gameDataModel = new GameDataModel();
    }
}
