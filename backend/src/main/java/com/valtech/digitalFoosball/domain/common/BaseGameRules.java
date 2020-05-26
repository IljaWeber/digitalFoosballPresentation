package com.valtech.digitalFoosball.domain.common;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.models.GameDataModel;
import com.valtech.digitalFoosball.domain.ranked.RankedTeamDataModel;

import static com.valtech.digitalFoosball.domain.common.constants.Team.*;

public abstract class BaseGameRules implements GameRules {

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
        RankedTeamDataModel teamDataModel = gameDataModel.getTeam(team);
        return teamDataModel.getScore();
    }
}
