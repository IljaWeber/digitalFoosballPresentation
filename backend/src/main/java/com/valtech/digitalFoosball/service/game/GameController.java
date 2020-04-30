package com.valtech.digitalFoosball.service.game;

import com.valtech.digitalFoosball.api.INotifyAboutStateChanges;
import com.valtech.digitalFoosball.constants.GameMode;
import com.valtech.digitalFoosball.constants.Team;
import com.valtech.digitalFoosball.model.GameDataModel;
import com.valtech.digitalFoosball.model.input.InitDataModel;
import com.valtech.digitalFoosball.model.output.GameOutputModel;
import com.valtech.digitalFoosball.model.output.TeamOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameController implements IReactToGoals, IReactToPlayerCommands {

    private final INotifyAboutStateChanges notifier;
    private final GameModeHolder gameModeHolder;
    private GameDataModel gameDataModel = new GameDataModel();

    @Autowired
    public GameController(GameModeHolder gameModeHolder, INotifyAboutStateChanges notifier) {
        this.gameModeHolder = gameModeHolder;
        this.notifier = notifier;
    }

    public List<TeamOutput> getAllTeamsFromDatabase() {
        Game game = gameModeHolder.getGame(GameMode.RANKED);
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
        GameMode gameMode = gameDataModel.getGameMode();
        Game game = gameModeHolder.getGame(gameMode);
        game.countGoalFor(team, gameDataModel);

        notifyAboutStateChange();
    }

    private void notifyAboutStateChange() {
        GameOutputModel gameData = getGameData();
        notifier.notifyAboutStateChange(gameData);
    }

    public void undoGoal() {
        GameMode gameMode = gameDataModel.getGameMode();
        Game game = gameModeHolder.getGame(gameMode);
        game.undoGoal(gameDataModel);
    }

    public void redoGoal() {
        GameMode gameMode = gameDataModel.getGameMode();
        Game game = gameModeHolder.getGame(gameMode);
        game.redoGoal(gameDataModel);
    }

    public void changeover() {
        GameMode gameMode = gameDataModel.getGameMode();
        Game game = gameModeHolder.getGame(gameMode);
        game.changeover(gameDataModel);
    }

    public void resetMatch() {
        gameDataModel = new GameDataModel();
    }
}
