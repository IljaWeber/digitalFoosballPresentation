package com.valtech.digitalFoosball.domain.common.models.output.game;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.converter.Converter;
import com.valtech.digitalFoosball.domain.common.models.GameDataModel;
import com.valtech.digitalFoosball.domain.common.models.TeamDataModel;
import com.valtech.digitalFoosball.domain.ranked.ClassicGameRules;

import java.util.SortedMap;

public class ClassicGameOutputModel extends BaseGameOutputModel {
    private final Team winnerOfSet;

    public ClassicGameOutputModel(GameDataModel gameDataModel,
                                  ClassicGameRules classicGameRules) {
        SortedMap<Team, TeamDataModel> teamMap = gameDataModel.getTeams();

        super.matchWinner = classicGameRules.getMatchWinner();
        this.winnerOfSet = classicGameRules.getCurrentSetWinner();
        teams = Converter.convertMapToTeamOutputs(teamMap);

        if (teams.isEmpty()) {
            return;
        }

        // TODO: 28.07.20 m.huber refactor for more consistency (no empty list exception)
        teams.get(0).setScore(classicGameRules.getScoreOfTeam(Team.ONE));
        teams.get(1).setScore(classicGameRules.getScoreOfTeam(Team.TWO));
    }

    public Team getWinnerOfSet() {
        return winnerOfSet;
    }
}