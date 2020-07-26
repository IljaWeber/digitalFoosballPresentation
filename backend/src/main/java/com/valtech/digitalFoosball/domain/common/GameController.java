package com.valtech.digitalFoosball.domain.common;

import com.valtech.digitalFoosball.api.driven.notification.INotifyAboutStateChanges;
import com.valtech.digitalFoosball.api.driver.sensorcommands.IReactToGoals;
import com.valtech.digitalFoosball.api.driver.usercommands.IReactToUserCommands;
import com.valtech.digitalFoosball.domain.common.constants.GameMode;
import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import com.valtech.digitalFoosball.domain.common.models.output.game.EmptyGameOutputModel;
import com.valtech.digitalFoosball.domain.common.models.output.game.GameOutputModel;
import com.valtech.digitalFoosball.domain.common.models.output.team.TeamOutputModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameController implements IReactToGoals, IReactToUserCommands {

    private final INotifyAboutStateChanges
            notifier;
    private final GameProvider
            gameProvider;
    private IPlayAGame
            game;

    @Autowired
    public GameController(GameProvider gameProvider, INotifyAboutStateChanges notifier) {
        this.gameProvider = gameProvider;
        this.notifier = notifier;
    }

    public List<TeamOutputModel> getAllTeams() {
        IPlayAGame gameManipulator = gameProvider.getGameManipulator(GameMode.RANKED);
        return gameManipulator.getAllTeamsFromDatabase();
    }

    public GameOutputModel getGameData() {
        if (null == game) {
            return new EmptyGameOutputModel();
        }

        return game.getGameData();
    }

    public void initGame(InitDataModel initDataModel) {
        GameMode mode = initDataModel.getMode();
        game = gameProvider.getGameManipulator(mode);
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
