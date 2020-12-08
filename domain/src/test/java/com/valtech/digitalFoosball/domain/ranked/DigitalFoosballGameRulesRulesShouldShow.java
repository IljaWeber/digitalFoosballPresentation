package com.valtech.digitalFoosball.domain.ranked;

import com.valtech.digitalFoosball.domain.common.DigitalFoosballGameRules;
import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import com.valtech.digitalFoosball.domain.common.models.TeamDataModel;
import com.valtech.digitalFoosball.domain.common.models.output.game.ClassicGameOutputModel;
import com.valtech.digitalFoosball.domain.common.models.output.game.GameOutputModel;
import com.valtech.digitalFoosball.domain.ports.RankedGamePersistencePort;
import com.valtech.digitalFoosball.domain.usecases.ranked.service.RankedInitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.valtech.digitalFoosball.domain.common.constants.Team.*;
import static org.assertj.core.api.Assertions.assertThat;

public class DigitalFoosballGameRulesRulesShouldShow {
    private DigitalFoosballGameRules rules;

    @BeforeEach
    void setUp() {
        TeamDataModel teamOne = new TeamDataModel("T1", "P1", "P2");
        TeamDataModel teamTwo = new TeamDataModel("T2", "P3", "P4");
        InitDataModel initDataModel = new InitDataModel(teamOne, teamTwo);

        rules = new DigitalFoosballGameRules(new RankedInitService(new TeamServiceFake()));

        rules.initGame(initDataModel);
    }

    @Test
    public void no_set_winner_when_no_team_scored_six_goals() {
        countGoalsFor(TWO,
                      ONE, ONE,
                      TWO,
                      ONE);

        GameOutputModel gameData = rules.getGameData();
        ClassicGameOutputModel outputModel = (ClassicGameOutputModel) gameData;
        Team actual = outputModel.getWinnerOfSet();
        assertThat(actual).isEqualTo(NO_TEAM);
    }

    @Test
    void no_set_winner_when_teams_has_a_score_more_than_six_but_a_difference_of_less_than_two() {
        countGoalsFor(TWO, TWO, TWO,
                      ONE, ONE,
                      TWO,
                      ONE, ONE, ONE,
                      TWO, TWO,
                      ONE,
                      TWO);

        GameOutputModel gameData = rules.getGameData();
        ClassicGameOutputModel outputModel = (ClassicGameOutputModel) gameData;
        Team actual = outputModel.getWinnerOfSet();
        assertThat(actual).isEqualTo(NO_TEAM);
    }

    @Test
    public void a_set_winner_when_a_team_scored_more_than_six_goals_and_there_is_a_score_difference_of_at_least_two() {
        countGoalsFor(TWO, TWO, TWO, TWO, TWO, TWO);

        GameOutputModel gameData = rules.getGameData();
        ClassicGameOutputModel outputModel = (ClassicGameOutputModel) gameData;
        Team actual = outputModel.getWinnerOfSet();
        assertThat(actual).isEqualTo(TWO);
    }

    private void countGoalsFor(Team... teams) {
        for (Team team : teams) {
            rules.countGoalFor(team);
        }
    }

    private class TeamServiceFake implements RankedGamePersistencePort {

        @Override
        public TeamDataModel loadOrSaveIntoDatabase(TeamDataModel teamDataModel) {

            return teamDataModel;
        }

        @Override
        public List<TeamDataModel> getAllTeamsFromDatabase() {
            return null;
        }
    }
}
