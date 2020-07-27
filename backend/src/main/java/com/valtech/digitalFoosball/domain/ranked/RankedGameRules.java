package com.valtech.digitalFoosball.domain.ranked;

import com.valtech.digitalFoosball.domain.common.ClassicGameRules;
import com.valtech.digitalFoosball.domain.common.IModifyGames;
import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.histories.ScoreOverView;
import com.valtech.digitalFoosball.domain.common.models.output.game.ClassicGameOutputModel;
import com.valtech.digitalFoosball.domain.common.models.output.game.GameOutputModel;

import java.util.SortedMap;

import static com.valtech.digitalFoosball.domain.common.constants.Team.NO_TEAM;

public class RankedGameRules extends ClassicGameRules implements IModifyGames {
    private final GameDataModel model;
    private ScoreOverView scoreOverView;

    public RankedGameRules(RankedGameDataModel gameDataModel) {
        this.model = gameDataModel;
        scoreOverView = new ScoreOverView();
    }

    public void raiseScoreFor(Team team) {
        if (model.getWinner() == NO_TEAM) {
            model.countGoalFor(team);
            scoreOverView.rememberLastGoalFor(team);
        }

        approveSetWinner();
    }

    public void undoLastGoal() {
        if (scoreOverView.thereAreGoals()) {
            model.undoLastGoalFor(scoreOverView.getLastScoredTeam());
        }
    }

    public void redoGoal() {
        if (scoreOverView.thereAreUndoneGoals()) {
            Team team = scoreOverView.getLastUndoingTeam();
            model.countGoalFor(team);
            approveSetWinner();
        }
    }

    public void changeOver() {
        model.changeOver();
        scoreOverView = new ScoreOverView();
    }

    public void resetMatch() {
        model.resetMatch();
        scoreOverView = new ScoreOverView();
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
