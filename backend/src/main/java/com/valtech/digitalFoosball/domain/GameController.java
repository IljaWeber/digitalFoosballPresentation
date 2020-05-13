package com.valtech.digitalFoosball.domain;

import com.valtech.digitalFoosball.api.driven.notification.INotifyAboutStateChanges;
import com.valtech.digitalFoosball.api.driver.sensorcommands.IReactToGoals;
import com.valtech.digitalFoosball.api.driver.usercommands.IReactToUserCommands;
import com.valtech.digitalFoosball.domain.constants.GameMode;
import com.valtech.digitalFoosball.domain.constants.Team;
import com.valtech.digitalFoosball.domain.gameModes.InitService;
import com.valtech.digitalFoosball.domain.gameModes.manipulators.AbstractGameManipulator;
import com.valtech.digitalFoosball.domain.gameModes.manipulators.GameManipulatorFactory;
import com.valtech.digitalFoosball.domain.gameModes.models.GameDataModel;
import com.valtech.digitalFoosball.domain.gameModes.models.GameOutputModel;
import com.valtech.digitalFoosball.domain.gameModes.models.InitDataModel;
import com.valtech.digitalFoosball.domain.gameModes.models.TeamOutput;
import com.valtech.digitalFoosball.domain.gameModes.regular.models.RegularGameDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameController implements IReactToGoals, IReactToUserCommands {

    private final INotifyAboutStateChanges notifier;
    private final InitService initService;
    private GameDataModel gameDataModel = new RegularGameDataModel();

    @Autowired
    public GameController(INotifyAboutStateChanges notifier,
                          InitService initService) {
        this.notifier = notifier;
        this.initService = initService;
    }

    public List<TeamOutput> getAllTeams() {
        return initService.getAllTeamsFromDatabase();
    }

    public GameOutputModel getGameData() {
        if (gameDataModel.isEmpty()) {
            return new GameOutputModel();
        }

        return new GameOutputModel(gameDataModel);
    }

    public void initGame(InitDataModel initDataModel) {
        gameDataModel = initService.init(initDataModel);
        gameDataModel.addObserver(notifier);
    }

    public void countGoalFor(Team team) {
        AbstractGameManipulator gameManipulator = getGameManipulator();
        gameManipulator.countGoalFor(team, gameDataModel);

        notifyAboutStateChange();
    }

    private AbstractGameManipulator getGameManipulator() {
        GameMode gameMode = gameDataModel.getGameMode();
        return GameManipulatorFactory.createManipulatorFor(gameMode);
    }

    private void notifyAboutStateChange() {
        GameOutputModel gameData = getGameData();
        notifier.notifyAboutStateChange(gameData);
    }

    public void undoGoal() {
        AbstractGameManipulator gameManipulator = getGameManipulator();
        gameManipulator.undoGoal(gameDataModel);
    }

    public void redoGoal() {
        AbstractGameManipulator gameManipulator = getGameManipulator();
        gameManipulator.redoGoal(gameDataModel);
    }

    public void changeover() {
        AbstractGameManipulator gameManipulator = getGameManipulator();
        gameManipulator.changeover(gameDataModel);
    }

    public void resetMatch() {
        gameDataModel = new RegularGameDataModel();
    }
}
