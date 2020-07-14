package com.valtech.digitalFoosball.domain.ranked;

import com.valtech.digitalFoosball.domain.common.IPlayAGame;
import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.models.GameDataModel;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import com.valtech.digitalFoosball.domain.common.models.output.team.TeamOutputModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.valtech.digitalFoosball.domain.common.constants.Team.NO_TEAM;

@Service
public class RankedGame implements IPlayAGame {
    private final RankedInitService initService;
    private RankedGameDataModel gameDataModel;
    private final RankedGameRules rules;

    @Autowired
    public RankedGame(RankedInitService initService) {
        this.initService = initService;
        rules = new RankedGameRules();
    }

    @Override
    public List<TeamOutputModel> getAllTeamsFromDatabase() {
        return initService.getAllTeams();
    }

    public void initGame(InitDataModel initDataModel) {
        gameDataModel = initService.init(initDataModel);
    }

    public void undoGoal() {

        if (gameDataModel.thereAreGoals()) {

            if (rules.winConditionsFulfilled(gameDataModel)) {
                gameDataModel.decreaseWonSetsForRecentSetWinner();
                gameDataModel.setSetWinner(NO_TEAM);
            }

            gameDataModel.undoLastGoal();
        }
    }

    public void countGoalFor(Team team) {
        Team winner = rules.getTeamWithLeadOfTwo(gameDataModel);

        if (winner != NO_TEAM) {
            return;
        }

        gameDataModel.countGoalFor(team);

        rules.approveWin(gameDataModel);
    }

    public void redoGoal() {
        if (gameDataModel.thereAreUndoneGoals()) {

            gameDataModel.redoLastUndoneGoal();

            rules.approveWin(gameDataModel);
        }
    }

    public void changeover() {
        gameDataModel.changeOver();
    }

    @Override
    public void resetMatch() {
        gameDataModel.resetMatch();
    }

    @Override
    public GameDataModel getGameData() {
        return gameDataModel;
    }

    public RankedGameDataModel getTeams() {
        return gameDataModel;
    }
}
