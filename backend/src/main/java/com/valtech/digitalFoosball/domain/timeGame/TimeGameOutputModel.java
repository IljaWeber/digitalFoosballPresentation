package com.valtech.digitalFoosball.domain.timeGame;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.converter.Converter;
import com.valtech.digitalFoosball.domain.common.models.GameDataModel;
import com.valtech.digitalFoosball.domain.common.models.output.game.BaseGameOutputModel;
import com.valtech.digitalFoosball.domain.common.models.output.game.GameOutputModel;

public class TimeGameOutputModel extends BaseGameOutputModel implements GameOutputModel {
    private final GameState gameState;

    public TimeGameOutputModel(GameDataModel model, TimeGameRules timeGameRules) {
        super.teams = Converter.convertMapToTeamOutputs(model.getTeams());
        super.matchWinner = timeGameRules.determineWinner();
        gameState = timeGameRules.getGameState();

        if (teams.isEmpty()) {
            return;
        }

        teams.get(0).setScore(timeGameRules.getScoreOfTeam(Team.ONE));
        teams.get(1).setScore(timeGameRules.getScoreOfTeam(Team.TWO));
    }

    public GameState getGameState() {
        return gameState;
    }
}