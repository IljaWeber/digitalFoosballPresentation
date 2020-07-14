package com.valtech.digitalFoosball.domain.ranked;

import com.valtech.digitalFoosball.domain.common.IPlayAGame;
import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.models.GameDataModel;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import com.valtech.digitalFoosball.domain.common.models.TeamDataModel;
import com.valtech.digitalFoosball.domain.common.models.output.team.TeamOutputModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.valtech.digitalFoosball.domain.common.constants.Team.*;

@Service
public class RankedGame implements IPlayAGame {
    private RankedInitService initService;
    private RankedGameDataModel gameDataModel;
    private Team setWinner = NO_TEAM;

    @Autowired
    public RankedGame(RankedInitService initService) {
        this.initService = initService;
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

            if (winConditionsFulfilled()) {
                gameDataModel.decreaseWonSetsForRecentSetWinner();
                gameDataModel.setSetWinner(NO_TEAM);
            }

            gameDataModel.undoLastGoal();
        }
    }

    public void countGoalFor(Team team) {
        Team winner = getTeamWithLeadOfTwo(gameDataModel);

        if (winner != NO_TEAM) {
            return;
        }

        gameDataModel.countGoalFor(team);

        approveWin(gameDataModel);
    }

    public void redoGoal() {
        if (gameDataModel.thereAreUndoneGoals()) {

            gameDataModel.redoLastUndoneGoal();

            approveWin(gameDataModel);
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

    private void approveWin(GameDataModel gameDataModel) {
        Team winner = getTeamWithLeadOfTwo(gameDataModel);

        if (winner != NO_TEAM) {
            gameDataModel.increaseWonSetsFor(winner);
            gameDataModel.setSetWinner(winner);
        }
    }

    private boolean winConditionsFulfilled() {
        Team setWinner = gameDataModel.getSetWinner();

        if (setWinner != NO_TEAM) {
            this.setWinner = setWinner;

            return true;
        }

        return false;
    }

    private int getScoreOfTeam(Team team, GameDataModel gameDataModel) {
        TeamDataModel teamDataModel = gameDataModel.getTeam(team);
        return teamDataModel.getScore();
    }

    private Team getTeamWithLeadOfTwo(GameDataModel gameDataModel) {
        Team winner = NO_TEAM;

        if (thereIsALeadingTeam(gameDataModel)) {

            Team leadingTeam = gameDataModel.getLeadingTeam();
            TeamDataModel teamDataModel = gameDataModel.getTeam(leadingTeam);

            if (enoughGoals(teamDataModel) && bigEnoughScoreDifference(gameDataModel)) {
                winner = leadingTeam;
            }

        }

        return winner;
    }

    private boolean thereIsALeadingTeam(GameDataModel gameDataModel) {
        return gameDataModel.getLeadingTeam() != NO_TEAM;
    }

    private boolean enoughGoals(TeamDataModel team) {
        int neededGoals = 6;
        return team.getScore() >= neededGoals;
    }

    private boolean bigEnoughScoreDifference(GameDataModel gameDataModel) {
        int scoreTeamOne = getScoreOfTeam(ONE, gameDataModel);
        int scoreTeamTwo = getScoreOfTeam(TWO, gameDataModel);

        int currentDifference = scoreTeamOne - scoreTeamTwo;
        int absoluteDifference = Math.abs(currentDifference);

        int requiredDifference = 2;
        return absoluteDifference >= requiredDifference;
    }

    public RankedGameDataModel getTeams() {
        return gameDataModel;
    }
}
