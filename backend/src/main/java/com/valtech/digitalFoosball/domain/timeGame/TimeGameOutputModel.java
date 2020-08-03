package com.valtech.digitalFoosball.domain.timeGame;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.converter.Converter;
import com.valtech.digitalFoosball.domain.common.models.GameDataModel;
import com.valtech.digitalFoosball.domain.common.models.output.game.BaseGameOutputModel;
import com.valtech.digitalFoosball.domain.common.models.output.game.GameOutputModel;

import java.util.Map;

public class TimeGameOutputModel extends BaseGameOutputModel implements GameOutputModel {
    private final String actualGameSequence;

    public TimeGameOutputModel(GameDataModel model, TimeGameRules timeGameRules) {
        super.teams = Converter.convertMapToTeamOutputs(model.getTeams());
        super.matchWinner = timeGameRules.getMatchWinner();
        this.actualGameSequence = timeGameRules.getAlternativeGameSequenceRepresentation();
        Map<Team, Integer> scoreOfTeams = timeGameRules.getScoreOfTeams();

        if (teams.isEmpty()) {
            return;
        }

        teams.get(0).setScore(scoreOfTeams.get(Team.ONE));
        teams.get(1).setScore(scoreOfTeams.get(Team.TWO));
    }

    public String getActualGameSequence() {
        return actualGameSequence;
    }
}
