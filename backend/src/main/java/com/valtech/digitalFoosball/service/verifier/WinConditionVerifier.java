package com.valtech.digitalFoosball.service.verifier;

import com.valtech.digitalFoosball.model.internal.TeamDataModel;

import java.util.List;

public class WinConditionVerifier {
    private List<TeamDataModel> teams;

    public void init(List<TeamDataModel> teams) {
        this.teams = teams;
    }

    public int verifySetWinner() {
        int winnerOfActualSet = 0;

        for (TeamDataModel team : teams) {
            if (scoreGreaterOrEqualSix(team) && leadOfTwo(team)) {
                winnerOfActualSet = teams.indexOf(team) + 1;
            }
        }

        return winnerOfActualSet;
    }

    private boolean scoreGreaterOrEqualSix(TeamDataModel team) {
        return team.getScore() >= 6;
    }

    private boolean leadOfTwo(TeamDataModel team) {
        final int neededLead = 2;

        TeamDataModel opponentTeam = getOpponent(team);

        final int currentLead = team.getScore() - opponentTeam.getScore();

        return currentLead >= neededLead;
    }

    private TeamDataModel getOpponent(TeamDataModel team) {
        TeamDataModel opponent = new TeamDataModel();

        for (TeamDataModel teamDataModel : teams) {
            if (!teamDataModel.equals(team)) {
                opponent = teamDataModel;
            }
        }

        return opponent;
    }
}
