package com.valtech.digitalFoosball.domain.common.models.output.game;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.converter.Converter;
import com.valtech.digitalFoosball.domain.ranked.GameDataModel;
import com.valtech.digitalFoosball.domain.ranked.RankedGameMatchWinVerifier;
import com.valtech.digitalFoosball.domain.ranked.RankedTeamDataModel;

import java.util.SortedMap;

public class ClassicGameOutputModel extends BaseGameOutputModel implements GameOutputModel {
    private Team winnerOfSet;

    public ClassicGameOutputModel(GameDataModel gameDataModel) {
        RankedGameMatchWinVerifier matchWinVerifier = new RankedGameMatchWinVerifier();
        SortedMap<Team, RankedTeamDataModel> teamMap = gameDataModel.getTeams();

        super.teams = Converter.convertMapToTeamOutputs(teamMap);
        super.matchWinner = matchWinVerifier.getMatchWinner(teamMap);
        this.winnerOfSet = gameDataModel.getWinner();
    }

    @Override
    public Team getWinnerOfSet() {
        return winnerOfSet;
    }

}
