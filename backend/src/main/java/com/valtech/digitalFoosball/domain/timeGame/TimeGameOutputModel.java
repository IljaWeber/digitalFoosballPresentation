package com.valtech.digitalFoosball.domain.timeGame;

import com.valtech.digitalFoosball.domain.common.converter.Converter;
import com.valtech.digitalFoosball.domain.common.models.GameDataModel;
import com.valtech.digitalFoosball.domain.common.models.output.game.BaseGameOutputModel;
import com.valtech.digitalFoosball.domain.common.models.output.game.GameOutputModel;

public class TimeGameOutputModel extends BaseGameOutputModel implements GameOutputModel {
    private final String actualGameSequence;

    public TimeGameOutputModel(GameDataModel model, TimeGameRules timeGameRules) {
        super.teams = Converter.convertMapToTeamOutputs(model.getTeams());
        super.matchWinner = timeGameRules.getMatchWinner();
        this.actualGameSequence = timeGameRules.getAlternativeGameSequenceRepresentation();
        MatchScores matchScores = timeGameRules.getMatchScores();
        if (teams.isEmpty()) {
            return;
        }

        teams.get(0).setScore(matchScores.getScoreOfTeamOne());
        teams.get(1).setScore(matchScores.getScoreOfTeamTwo());
    }

    public String getActualGameSequence() {
        return actualGameSequence;
    }
}
