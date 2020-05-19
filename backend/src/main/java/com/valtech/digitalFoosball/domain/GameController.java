package com.valtech.digitalFoosball.domain;

import com.valtech.digitalFoosball.api.driven.notification.INotifyAboutStateChanges;
import com.valtech.digitalFoosball.api.driver.sensorcommands.IReactToGoals;
import com.valtech.digitalFoosball.api.driver.usercommands.IReactToUserCommands;
import com.valtech.digitalFoosball.domain.constants.GameMode;
import com.valtech.digitalFoosball.domain.constants.Team;
import com.valtech.digitalFoosball.domain.gameModes.manipulators.GameManipulatorProvider;
import com.valtech.digitalFoosball.domain.gameModes.manipulators.IPlayAGame;
import com.valtech.digitalFoosball.domain.gameModes.models.InitDataModel;
import com.valtech.digitalFoosball.domain.gameModes.models.output.game.GameOutputModel;
import com.valtech.digitalFoosball.domain.gameModes.models.output.game.RegularGameOutputModel;
import com.valtech.digitalFoosball.domain.gameModes.models.output.team.TeamOutputModel;
import com.valtech.digitalFoosball.domain.gameModes.regular.models.game.BaseGameDataModel;
import com.valtech.digitalFoosball.domain.gameModes.regular.models.game.GameDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameController implements IReactToGoals, IReactToUserCommands {

    private final INotifyAboutStateChanges notifier;
    private final GameManipulatorProvider gameManipulatorProvider;
    private GameDataModel gameDataModel = new BaseGameDataModel();

    @Autowired
    public GameController(GameManipulatorProvider gameManipulatorProvider, INotifyAboutStateChanges notifier) {
        this.gameManipulatorProvider = gameManipulatorProvider;
        this.notifier = notifier;
    }

    public List<TeamOutputModel> getAllTeams() {
        IPlayAGame gameManipulator = gameManipulatorProvider.getGameManipulator(GameMode.RANKED);
        return gameManipulator.getAllTeamsFromDatabase();
    }

    public GameOutputModel getGameData() {
        gameDataModel.isEmpty();

        return new RegularGameOutputModel(gameDataModel);
    }

    public void initGame(InitDataModel initDataModel) {
        GameMode mode = initDataModel.getMode();
        IPlayAGame gameManipulator = gameManipulatorProvider.getGameManipulator(mode);
        gameDataModel = gameManipulator.initGame(initDataModel);
        gameDataModel.setGameMode(mode);
        gameDataModel.addObserver(notifier);
    }

    public void countGoalFor(Team team) {
        IPlayAGame gameManipulator = getGame();
        gameManipulator.countGoalFor(team, gameDataModel);

        notifyAboutStateChange();
    }

    private IPlayAGame getGame() {
        GameMode gameMode = gameDataModel.getGameMode();
        return gameManipulatorProvider.getGameManipulator(gameMode);
    }

    private void notifyAboutStateChange() {
        GameOutputModel gameData = getGameData();
        notifier.notifyAboutStateChange(gameData);
    }

    public void undoGoal() {
        IPlayAGame gameManipulator = getGame();
        gameManipulator.undoGoal(gameDataModel);
    }

    public void redoGoal() {
        IPlayAGame gameManipulator = getGame();
        gameManipulator.redoGoal(gameDataModel);
    }

    public void changeover() {
        IPlayAGame gameManipulator = getGame();
        gameManipulator.changeover(gameDataModel);
    }

    public void resetMatch() {
        gameDataModel = new BaseGameDataModel();
    }
}
