package com.valtech.digitalFoosball.service;

import com.valtech.digitalFoosball.model.internal.TeamDataModel;

import java.util.List;

public class SetWinApprover {

    private List<TeamDataModel> teams;

    public void init(List<TeamDataModel> teams) {
        this.teams = teams;
    }

    public int getSetWinner() {
        int roundWinner = 0;

        for (TeamDataModel team : teams) {
            if (scoreGreaterOrEqualSix(team) && leadingWithTwoOrMoreGoals(team)) {
                roundWinner = teams.indexOf(team) + 1;
            }
        }

        return roundWinner;
    }

    private boolean scoreGreaterOrEqualSix(TeamDataModel team) {
        return team.getScore() >= 6;
    }

    private boolean leadingWithTwoOrMoreGoals(TeamDataModel team) {
        final int necessaryScoreDifference = 2;

        TeamDataModel opponentTeam = getOpponent(team);

        final int actualScoreDifference = team.getScore() - opponentTeam.getScore();

        return actualScoreDifference >= necessaryScoreDifference;
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
