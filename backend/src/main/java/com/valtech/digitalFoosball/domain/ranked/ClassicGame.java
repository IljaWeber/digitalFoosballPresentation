package com.valtech.digitalFoosball.domain.ranked;

import com.valtech.digitalFoosball.domain.common.IPlayAGame;
import com.valtech.digitalFoosball.domain.common.InitService;
import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.models.GameDataModel;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import com.valtech.digitalFoosball.domain.common.models.output.game.ClassicGameOutputModel;
import com.valtech.digitalFoosball.domain.common.models.output.game.GameOutputModel;
import com.valtech.digitalFoosball.domain.common.models.output.team.TeamOutputModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassicGame implements IPlayAGame {
    private ClassicGameRules classicGameRules;
    private GameDataModel model;
    private final InitService initService;

    @Autowired
    public ClassicGame(InitService initService) {
        this.initService = initService;
        classicGameRules = new ClassicGameRules();
    }

    public List<TeamOutputModel> getAllTeamsFromDatabase() {
        return initService.getAllTeams();
    }

    public void initGame(InitDataModel initDataModel) {
        classicGameRules = new ClassicGameRules();

        model = initService.init(initDataModel);
    }

    public void countGoalFor(Team team) {
        classicGameRules.raiseScoreFor(team);
    }

    public void undoGoal() {
        classicGameRules.undoLastGoal();
    }

    public void redoGoal() {
        classicGameRules.redoLastGoal();
    }

    public void changeover() {
        classicGameRules.changeOver();
    }

    public void resetMatch() {
        model.resetMatch();
        classicGameRules = new ClassicGameRules();
    }

    public GameOutputModel getGameData() {
        return new ClassicGameOutputModel(model, classicGameRules);
    }

}
