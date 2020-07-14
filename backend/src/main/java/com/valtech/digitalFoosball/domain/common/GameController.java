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
    private IPlayAGame game;

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
        GameDataModel gameDataModel = game.getGameData();

        if (null == gameDataModel) {
            return new EmptyGameOutputModel();
        }

        return new RegularGameOutputModel(gameDataModel);
    }

    public void initGame(InitDataModel initDataModel) {
        GameMode mode = initDataModel.getMode();
        game = gameManipulatorProvider.getGameManipulator(mode);
        game.initGame(initDataModel);
    }

    public void countGoalFor(Team team) {
        game.countGoalFor(team);

        notifyAboutStateChange();
    }


    public void undoGoal() {
        game.undoGoal();
    }

    public void redoGoal() {
        game.redoGoal();
    }

    public void changeover() {
        game.changeover();
    }

    public void resetMatch() {
        game.resetMatch();
    }

    private void notifyAboutStateChange() {
        GameOutputModel gameData = getGameData();
        notifier.notifyAboutStateChange(gameData);
    }
}
