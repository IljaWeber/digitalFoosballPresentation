package com.valtech.digitalFoosball.service.verifier;

import com.valtech.digitalFoosball.constants.Team;
import com.valtech.digitalFoosball.model.internal.TeamDataModel;

import java.util.Map;

public class SetWinVerifier {

    private final int neededGoals = 6;
    private Map<Team, TeamDataModel> teams;
    private final int requiredDifference = 2;

    public boolean teamWon(Map<Team, TeamDataModel> teams, Team scoringTeam) {
        this.teams = teams;
        TeamDataModel scoringTeamDataModel = teams.get(scoringTeam);

        return enoughGoals(scoringTeamDataModel) && bigEnoughScoreDifference();
    }

    private boolean enoughGoals(TeamDataModel team) {
        return team.getScore() >= neededGoals;
    }

    private boolean bigEnoughScoreDifference() {
        TeamDataModel teamOne = teams.get(Team.ONE);
        TeamDataModel teamTwo = teams.get(Team.TWO);

        int scoreTeamOne = teamOne.getScore();
        int scoreTeamTwo = teamTwo.getScore();

        int currentDifference = scoreTeamOne - scoreTeamTwo;
        int absoluteDifference = Math.abs(currentDifference);

        return absoluteDifference >= requiredDifference;
    }
}
