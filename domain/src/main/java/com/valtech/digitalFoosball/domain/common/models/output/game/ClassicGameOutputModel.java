package com.valtech.digitalFoosball.domain.common.models.output.game;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.converter.Converter;
import com.valtech.digitalFoosball.domain.common.models.GameDataModel;
import com.valtech.digitalFoosball.domain.common.models.TeamDataModel;

import java.util.SortedMap;

public class ClassicGameOutputModel extends BaseGameOutputModel {
    private final Team winnerOfSet;

    public ClassicGameOutputModel(GameDataModel gameDataModel) {
        SortedMap<Team, TeamDataModel> teamMap = gameDataModel.getTeams();

        super.matchWinner = gameDataModel.getMatchWinner();
        this.winnerOfSet = gameDataModel.getCurrentSetWinner();
        teams = Converter.convertMapToTeamOutputs(teamMap);

        if (teams.isEmpty()) {
            return;
        }

        teams.get(0).setScore(gameDataModel.getScoreOfTeam(Team.ONE));
        teams.get(1).setScore(gameDataModel.getScoreOfTeam(Team.TWO));
    }

    public Team getWinnerOfSet() {
        return winnerOfSet;
    }
}
