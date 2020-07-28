package com.valtech.digitalFoosball.domain.common.models.output.game;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.converter.Converter;
import com.valtech.digitalFoosball.domain.common.histories.RankedScoreManager;
import com.valtech.digitalFoosball.domain.ranked.GameDataModel;
import com.valtech.digitalFoosball.domain.ranked.TeamDataModel;

import java.util.SortedMap;

public class ClassicGameOutputModel extends BaseGameOutputModel implements GameOutputModel {
    private Team winnerOfSet;

    public ClassicGameOutputModel(GameDataModel gameDataModel,
                                  RankedScoreManager rankedScoreManager) {
        SortedMap<Team, TeamDataModel> teamMap = gameDataModel.getTeams();

        super.matchWinner = rankedScoreManager.getMatchWinner();
        this.winnerOfSet = rankedScoreManager.getActualWinner();
        teams = Converter.convertMapToTeamOutputs(teamMap);

        if (teams.isEmpty()) {
            return;
        }
        teams.get(0).setScore(rankedScoreManager.getScoreOfTeam(Team.ONE));
        teams.get(1).setScore(rankedScoreManager.getScoreOfTeam(Team.TWO));

    }

    @Override
    public Team getWinnerOfSet() {
        return winnerOfSet;
    }

}
