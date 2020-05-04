package com.valtech.digitalFoosball.service.game;

import com.valtech.digitalFoosball.api.INotifyAboutStateChanges;
import com.valtech.digitalFoosball.constants.GameMode;
import com.valtech.digitalFoosball.constants.Team;
import com.valtech.digitalFoosball.model.GameDataModel;
import com.valtech.digitalFoosball.model.input.InitDataModel;
import com.valtech.digitalFoosball.model.output.GameOutputModel;
import com.valtech.digitalFoosball.model.output.TeamOutput;
import com.valtech.digitalFoosball.service.game.modes.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameController implements IReactToGoals, IReactToPlayerCommands {

    private final INotifyAboutStateChanges notifier;
    private final GameModeHolder gameModeHolder;
    private GameDataModel gameDataModel = new GameDataModel();
    private Game game;

    @Autowired
    public GameController(GameModeHolder gameModeHolder, INotifyAboutStateChanges notifier) {
        this.gameModeHolder = gameModeHolder;
        this.notifier = notifier;
    }

    public List<TeamOutput> getAllTeamsFromDatabase() {
        game = gameModeHolder.getGame(GameMode.RANKED);
        return game.getAllTeamsFromDatabase();
    }

    public GameOutputModel getGameData() {
        if (gameDataModel.isEmpty()) {
            return new GameOutputModel();
        }

        return new GameOutputModel(gameDataModel);
    }

    public void initGame(InitDataModel initDataModel, GameMode gameMode) {
        Game game = gameModeHolder.getGame(gameMode);
        gameDataModel = game.initGame(initDataModel);
        gameDataModel.setGameMode(gameMode);
    }

    public void countGoalFor(Team team) {
        Game game = getGame();
        game.countGoalFor(team, gameDataModel);

        notifyAboutStateChange();
    }

    private Game getGame() {
        GameMode gameMode = gameDataModel.getGameMode();
        return gameModeHolder.getGame(gameMode);
    }

    private void notifyAboutStateChange() {
        GameOutputModel gameData = getGameData();
        notifier.notifyAboutStateChange(gameData);
    }

    public void undoGoal() {
        Game game = getGame();
        game.undoGoal(gameDataModel);
    }

    public void redoGoal() {
        Game game = getGame();
        game.redoGoal(gameDataModel);
    }

    public void changeover() {
        Game game = getGame();
        game.changeover(gameDataModel);
    }

    public void resetMatch() {
        gameDataModel = new GameDataModel();
    }
}