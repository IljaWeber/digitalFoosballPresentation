package com.valtech.digitalFoosball.domain.gameModes.regular.ranked;

import com.valtech.digitalFoosball.domain.constants.Team;
import com.valtech.digitalFoosball.domain.gameModes.regular.models.TeamDataModel;

import java.util.Map;

import static com.valtech.digitalFoosball.domain.constants.Team.NO_TEAM;

public class MatchWinVerifier {

    private Team matchWinner;
    private Map<Team, TeamDataModel> teams;

    public Team getMatchWinner(Map<Team, TeamDataModel> teams) {
        this.teams = teams;

        matchWinner = NO_TEAM;

        teams.forEach((team, dataModel) -> checkForWin(team));

        return matchWinner;
    }

    private void checkForWin(Team team) {
        TeamDataModel teamDataModel = teams.get(team);

        int requiredSetWins = 2;
        int actualWonSets = teamDataModel.getWonSets();

        if (actualWonSets >= requiredSetWins) {
            matchWinner = team;
        }
    }

}
