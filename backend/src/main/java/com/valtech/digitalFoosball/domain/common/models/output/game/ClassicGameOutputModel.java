package com.valtech.digitalFoosball.domain.common.models.output.game;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.converter.Converter;
import com.valtech.digitalFoosball.domain.ranked.GameDataModel;
import com.valtech.digitalFoosball.domain.ranked.RankedGameRules;
import com.valtech.digitalFoosball.domain.ranked.TeamDataModel;

import java.util.SortedMap;

public class ClassicGameOutputModel extends BaseGameOutputModel implements GameOutputModel {
    private final Team winnerOfSet;

    public ClassicGameOutputModel(GameDataModel gameDataModel,
                                  RankedGameRules rankedGameRules) {
        SortedMap<Team, TeamDataModel> teamMap = gameDataModel.getTeams();

        super.matchWinner = rankedGameRules.getMatchWinner();
        this.winnerOfSet = rankedGameRules.getActualWinner();
        teams = Converter.convertMapToTeamOutputs(teamMap);

        if (teams.isEmpty()) {
            return;
        }
        teams.get(0).setScore(rankedGameRules.getScoreOfTeam(Team.ONE));
        teams.get(1).setScore(rankedGameRules.getScoreOfTeam(Team.TWO));

    }

    @Override
    public Team getWinnerOfSet() {
        return winnerOfSet;
    }

}
