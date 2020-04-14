package com.valtech.digitalFoosball.service.verifier;

import com.valtech.digitalFoosball.constants.Team;
import com.valtech.digitalFoosball.model.internal.TeamDataModel;

import java.util.Map;

public class MatchWinVerifier {

    private int matchWinner;
    private Map<Team, TeamDataModel> teams;

    public int getMatchWinner(Map<Team, TeamDataModel> teams) {
        this.teams = teams;

        matchWinner = 0;

        teams.forEach((team, dataModel) -> checkForMatchWin(team));

        return matchWinner;
    }

    private void checkForMatchWin(Team team) {
        TeamDataModel teamDataModel = teams.get(team);

        if (winConditionFulfilled(teamDataModel)) {
            matchWinner = team.getInt();
        }
    }

    private boolean winConditionFulfilled(TeamDataModel teamDataModel) {
        int requiredSetWins = 2;
        int actualWonSets = teamDataModel.getWonSets();

        return actualWonSets >= requiredSetWins;
    }
}