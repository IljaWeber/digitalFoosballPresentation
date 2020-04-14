package com.valtech.digitalFoosball.builders;

import com.valtech.digitalFoosball.constants.Team;
import com.valtech.digitalFoosball.model.internal.TeamDataModel;
import com.valtech.digitalFoosball.model.output.GameDataModel;
import com.valtech.digitalFoosball.model.output.TeamOutput;
import com.valtech.digitalFoosball.service.Converter;
import com.valtech.digitalFoosball.service.verifier.MatchWinVerifier;

import java.util.List;
import java.util.Map;

public class GameDataModelBuilder {

    private static final MatchWinVerifier matchWinVerifier = new MatchWinVerifier();

    public static GameDataModel buildWithTeamsAndSetWinner(Map<Team, TeamDataModel> teams, int setWinner) {
        List<TeamOutput> teamOutputs = Converter.convertMapToTeamOutputs(teams);

        GameDataModel gameDataModel = new GameDataModel(teamOutputs);

        gameDataModel.setWinnerOfSet(setWinner);

        int matchWinner = matchWinVerifier.getMatchWinner(teams);
        gameDataModel.setMatchWinner(matchWinner);

        return gameDataModel;
    }
}