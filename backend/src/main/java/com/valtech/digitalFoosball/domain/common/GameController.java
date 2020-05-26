package com.valtech.digitalFoosball.domain.common;

import com.valtech.digitalFoosball.api.driven.notification.INotifyAboutStateChanges;
import com.valtech.digitalFoosball.api.driver.sensorcommands.IReactToGoals;
import com.valtech.digitalFoosball.api.driver.usercommands.IReactToUserCommands;
import com.valtech.digitalFoosball.domain.common.constants.GameMode;
import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.models.GameDataModel;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import com.valtech.digitalFoosball.domain.common.models.output.game.EmptyGameOutputModel;
import com.valtech.digitalFoosball.domain.common.models.output.game.GameOutputModel;
import com.valtech.digitalFoosball.domain.common.models.output.game.RegularGameOutputModel;
import com.valtech.digitalFoosball.domain.common.models.output.team.TeamOutputModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameController implements IReactToGoals, IReactToUserCommands {

    private final INotifyAboutStateChanges notifier;
    private final GameManipulatorProvider gameManipulatorProvider;
    private GameDataModel gameDataModel;

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
        if (null == gameDataModel) {
            return new EmptyGameOutputModel();
        }
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
        gameDataModel.resetMatch();
    }

    private void notifyAboutStateChange() {
        GameOutputModel gameData = getGameData();
        notifier.notifyAboutStateChange(gameData);
    }
}
