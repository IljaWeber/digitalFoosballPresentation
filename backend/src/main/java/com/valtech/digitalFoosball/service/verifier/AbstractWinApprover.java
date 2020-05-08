package com.valtech.digitalFoosball.service.verifier;

import com.valtech.digitalFoosball.constants.Team;
import com.valtech.digitalFoosball.model.internal.GameDataModel;
import com.valtech.digitalFoosball.model.internal.TeamDataModel;

import static com.valtech.digitalFoosball.constants.Team.*;

public abstract class AbstractWinApprover implements SetWinApprover {

    protected boolean thereIsALeadingTeam(GameDataModel gameDataModel) {
        return getLeadingTeam(gameDataModel) != NO_TEAM;
    }

    protected Team getLeadingTeam(GameDataModel gameDataModel) {
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

    protected int getScoreOfTeam(Team team, GameDataModel gameDataModel) {
        TeamDataModel teamDataModel = gameDataModel.getTeam(team);
        return teamDataModel.getScore();
    }
}
