package com.valtech.digitalFoosball.domain.ranked;

import com.valtech.digitalFoosball.domain.common.ClassicGameRules;
import com.valtech.digitalFoosball.domain.common.IModifyGames;
import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.models.output.game.ClassicGameOutputModel;
import com.valtech.digitalFoosball.domain.common.models.output.game.GameOutputModel;

import java.util.SortedMap;

import static com.valtech.digitalFoosball.domain.common.constants.Team.NO_TEAM;

public class RankedGameRules extends ClassicGameRules implements IModifyGames {
    private final GameDataModel model;

    public RankedGameRules(RankedGameDataModel gameDataModel) {
        this.model = gameDataModel;
    }

    public void raiseScoreFor(Team team) {
        model.countGoalFor(team);

        approveSetWinner();
    }

    public void undoLastGoal() {
        if (model.thereAreGoals()) {
            model.undoLastGoal();
        }
    }

    public void redoGoal() {
        if (model.areThereUndoneGoals()) {
            model.redoLastUndoneGoal();
            approveSetWinner();
        }
    }

    public void changeOver() {
        model.changeOver();
    }

    public void resetMatch() {
        model.resetMatch();
    }

    @Override
    public GameOutputModel getPreparedDataForOutput() {
        return new ClassicGameOutputModel(model);
    }

    private void approveSetWinner() {
        SortedMap<Team, RankedTeamDataModel> teams = model.getTeams();

        Team winnerTeam = super.checkForWin(teams);

        if (model.getWinner() == NO_TEAM) {
            model.setWinnerOfAGame(winnerTeam);
        }
    }
}
