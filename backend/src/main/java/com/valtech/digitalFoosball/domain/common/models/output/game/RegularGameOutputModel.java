package com.valtech.digitalFoosball.domain.common.models.output.game;

import com.valtech.digitalFoosball.domain.common.MatchWinVerifier;
import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.converter.Converter;
import com.valtech.digitalFoosball.domain.ranked.GameDataModel;
import com.valtech.digitalFoosball.domain.ranked.RankedGameMatchWinVerifier;
import com.valtech.digitalFoosball.domain.ranked.RankedTeamDataModel;

import java.util.SortedMap;
import java.util.Stack;

public class RegularGameOutputModel extends BaseGameOutputModel implements GameOutputModel {
    private Team winnerOfSet;

    public RegularGameOutputModel(GameDataModel gameDataModel) {
        MatchWinVerifier matchWinVerifier = new RankedGameMatchWinVerifier();
        SortedMap<Team, RankedTeamDataModel> teamMap = gameDataModel.getTeams();
        Stack<Team> allSetWins = gameDataModel.getAllWins();

        super.matchWinner = matchWinVerifier.getMatchWinner(allSetWins);
        super.teams = Converter.convertMapToTeamOutputs(teamMap);

        this.winnerOfSet = gameDataModel.getWinner();
    }

    @Override
    public Team getWinnerOfSet() {
        return winnerOfSet;
    }

}
