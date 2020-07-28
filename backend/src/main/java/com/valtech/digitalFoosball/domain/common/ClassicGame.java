package com.valtech.digitalFoosball.domain.common;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import com.valtech.digitalFoosball.domain.common.models.output.game.ClassicGameOutputModel;
import com.valtech.digitalFoosball.domain.common.models.output.game.GameOutputModel;
import com.valtech.digitalFoosball.domain.common.models.output.team.TeamOutputModel;
import com.valtech.digitalFoosball.domain.ranked.RankedGameRules;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassicGame implements IPlayAGame {
    protected RankedGameRules rankedGameRules;
    protected GameDataModel model;
    protected InitService initService;

    @Autowired
    public ClassicGame(InitService initService) {
        this.initService = initService;
        rankedGameRules = new RankedGameRules();
    }

    public List<TeamOutputModel> getAllTeamsFromDatabase() {
        return initService.getAllTeams();
    }

    public void initGame(InitDataModel initDataModel) {
        rankedGameRules = new RankedGameRules();

        model = initService.init(initDataModel);
    }

    public void countGoalFor(Team team) {
        rankedGameRules.raiseScoreFor(team);
    }

    public void undoGoal() {
        rankedGameRules.undoLastGoal();
    }

    public void redoGoal() {
        rankedGameRules.redoLastGoal();
    }

    public void changeover() {
        rankedGameRules.changeOver();
    }

    public void resetMatch() {
        model.resetMatch();
        rankedGameRules = new RankedGameRules();
    }

    public GameOutputModel getGameData() {
        return new ClassicGameOutputModel(model, rankedGameRules);
    }

}
