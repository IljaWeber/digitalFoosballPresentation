package com.valtech.digitalFoosball.service.verifier;

import com.valtech.digitalFoosball.constants.Team;
import com.valtech.digitalFoosball.model.GameDataModel;
import com.valtech.digitalFoosball.model.internal.TeamDataModel;

import static com.valtech.digitalFoosball.constants.Team.*;

public class RankedGameSetWinApprover implements SetWinApprover {

    public void approveWin(GameDataModel gameDataModel) {
        Team winner = getWinner(gameDataModel);

        if (winner != NO_TEAM) {
            gameDataModel.increaseWonSetsFor(winner);
            gameDataModel.setSetWinner(winner);
        }
    }

    private Team getWinner(GameDataModel gameDataModel) {
        Team winner = NO_TEAM;

        if (thereIsALeadingTeam(gameDataModel)) {

            Team leadingTeam = getLeadingTeam(gameDataModel);
            TeamDataModel teamDataModel = gameDataModel.getTeam(leadingTeam);

            if (enoughGoals(teamDataModel) && bigEnoughScoreDifference(gameDataModel)) {
                winner = leadingTeam;
            }

        }

        return winner;
    }

    private boolean thereIsALeadingTeam(GameDataModel gameDataModel) {
        return getLeadingTeam(gameDataModel) != NO_TEAM;
    }

    private Team getLeadingTeam(GameDataModel gameDataModel) {
        Team leadingTeam = NO_TEAM;

        int scoreOne = getScoreOfTeam(ONE, gameDataModel);
        int scoreTwo = getScoreOfTeam(TWO, gameDataModel);

        if (scoreOne > scoreTwo) {
            leadingTeam = ONE;
        }

        if (scoreTwo > scoreOne) {
            leadingTeam = TWO;
        }

        return leadingTeam;
    }

    private int getScoreOfTeam(Team team, GameDataModel gameDataModel) {
        TeamDataModel teamDataModel = gameDataModel.getTeam(team);
        return teamDataModel.getScore();
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
}
