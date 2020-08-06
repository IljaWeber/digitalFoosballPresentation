package com.valtech.digitalFoosball.domain.common.models.output.game;

import com.valtech.digitalFoosball.domain.common.DigitalFoosballGameRules;
import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.converter.Converter;
import com.valtech.digitalFoosball.domain.common.models.GameDataModel;
import com.valtech.digitalFoosball.domain.common.models.TeamDataModel;

import java.util.SortedMap;

public class ClassicGameOutputModel extends BaseGameOutputModel {
    private final Team winnerOfSet;

    public ClassicGameOutputModel(GameDataModel gameDataModel,
                                  DigitalFoosballGameRules digitalFoosballGameRules) {
        SortedMap<Team, TeamDataModel> teamMap = gameDataModel.getTeams();

        super.matchWinner = digitalFoosballGameRules.getMatchWinner();
        this.winnerOfSet = digitalFoosballGameRules.getCurrentSetWinner();
        teams = Converter.convertMapToTeamOutputs(teamMap);

        if (teams.isEmpty()) {
            return;
        }

        // TODO: 28.07.20 m.huber refactor for more consistency (no empty list exception)
        teams.get(0).setScore(digitalFoosballGameRules.getScoreOfTeam(Team.ONE));
        teams.get(1).setScore(digitalFoosballGameRules.getScoreOfTeam(Team.TWO));
    }

    public Team getWinnerOfSet() {
        return winnerOfSet;
    }
}
