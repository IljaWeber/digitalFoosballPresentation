package com.valtech.digitalFoosball.service.verifier;

import com.valtech.digitalFoosball.constants.Team;
import com.valtech.digitalFoosball.model.internal.TeamDataModel;

import java.util.Map;

public class RegularGameSetWinVerifier implements SetWinVerifier {

    private final int neededGoals = 6;
    private final int requiredDifference = 2;

    public boolean teamWon(Map<Team, TeamDataModel> teams, Team scoringTeam) {
        TeamDataModel scoringTeamDataModel = teams.get(scoringTeam);
        TeamDataModel teamOne = teams.get(Team.ONE);
        TeamDataModel teamTwo = teams.get(Team.TWO);

        return enoughGoals(scoringTeamDataModel) && bigEnoughScoreDifference(teamOne, teamTwo);
    }

    private boolean enoughGoals(TeamDataModel team) {
        return team.getScore() >= neededGoals;
    }

    private boolean bigEnoughScoreDifference(TeamDataModel teamOne, TeamDataModel teamTwo) {
        int scoreTeamOne = teamOne.getScore();
        int scoreTeamTwo = teamTwo.getScore();

        int currentDifference = scoreTeamOne - scoreTeamTwo;
        int absoluteDifference = Math.abs(currentDifference);

        return absoluteDifference >= requiredDifference;
    }
}
