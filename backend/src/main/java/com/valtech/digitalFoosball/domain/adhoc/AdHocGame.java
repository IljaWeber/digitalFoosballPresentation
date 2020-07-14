package com.valtech.digitalFoosball.domain.adhoc;

import com.valtech.digitalFoosball.domain.common.IPlayAGame;
import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.models.GameDataModel;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import com.valtech.digitalFoosball.domain.common.models.output.team.TeamOutputModel;
import com.valtech.digitalFoosball.domain.ranked.RankedGameDataModel;
import com.valtech.digitalFoosball.domain.ranked.RankedGameRules;
import com.valtech.digitalFoosball.domain.ranked.RankedTeamDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.valtech.digitalFoosball.domain.common.constants.Team.NO_TEAM;

@Service
public class AdHocGame implements IPlayAGame {
    private final AdHocInitService initService;
    private RankedGameDataModel gameDataModel;
    private final RankedGameRules rules;

    @Autowired
    public AdHocGame(AdHocInitService initService) {
        this.initService = initService;
        rules = new RankedGameRules();
    }

    @Override
    public List<TeamOutputModel> getAllTeamsFromDatabase() {
        return null;
    }

    @Override
    public void initGame(InitDataModel initDataModel) {
        initDataModel = new InitDataModel();
        RankedTeamDataModel teamOne = new RankedTeamDataModel("Orange", "Goalie", "Striker");
        RankedTeamDataModel teamTwo = new RankedTeamDataModel("Green", "Goalie", "Striker");

        initDataModel.setTeamOne(teamOne);
        initDataModel.setTeamTwo(teamTwo);

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
}
