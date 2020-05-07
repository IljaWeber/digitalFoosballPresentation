package com.valtech.digitalFoosball.service.verifier.setwin;

import com.valtech.digitalFoosball.constants.Team;
import com.valtech.digitalFoosball.model.GameDataModel;
import com.valtech.digitalFoosball.model.internal.TeamDataModel;

import java.util.Map;

public class RegularGameSetWinVerifier implements WonSetVerifier {

    @Deprecated
    public boolean teamWon(Map<Team, TeamDataModel> teams, Team scoringTeam) {
        TeamDataModel scoringTeamDataModel = teams.get(scoringTeam);
        TeamDataModel teamOne = teams.get(Team.ONE);
        TeamDataModel teamTwo = teams.get(Team.TWO);

        return enoughGoals(scoringTeamDataModel) && bigEnoughScoreDifference(teamOne, teamTwo);
    }

    @Override
    public boolean teamWon(GameDataModel teams) {
        boolean enoughGoals = false;
        TeamDataModel teamOne = teams.get(Team.ONE);
        TeamDataModel teamTwo = teams.get(Team.TWO);

        for (TeamDataModel value : teams.values()) {
            enoughGoals = enoughGoals(value);

            if (enoughGoals) {
                break;
            }
        }

        return enoughGoals && bigEnoughScoreDifference(teamOne, teamTwo);
    }

    private boolean enoughGoals(TeamDataModel team) {
        int neededGoals = 6;
        return team.getScore() >= neededGoals;
    }

    private boolean bigEnoughScoreDifference(TeamDataModel teamOne, TeamDataModel teamTwo) {
        int scoreTeamOne = teamOne.getScore();
        int scoreTeamTwo = teamTwo.getScore();

        int currentDifference = scoreTeamOne - scoreTeamTwo;
        int absoluteDifference = Math.abs(currentDifference);

        int requiredDifference = 2;
        return absoluteDifference >= requiredDifference;
    }
}
