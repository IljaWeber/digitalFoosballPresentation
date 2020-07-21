package com.valtech.digitalFoosball.domain.ranked;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.valtech.digitalFoosball.domain.common.constants.Team.NO_TEAM;
import static com.valtech.digitalFoosball.domain.common.constants.Team.TWO;
import static org.assertj.core.api.Assertions.assertThat;

public class RankedGameRulesShould {
    private RankedGameRules rules;
    private RankedGameDataModel gameDataModel;

    @BeforeEach
    void setUp() {
        List<RankedTeamDataModel> teams = new ArrayList<>();

        RankedTeamDataModel teamOne = new RankedTeamDataModel();
        RankedTeamDataModel teamTwo = new RankedTeamDataModel();

        teams.add(teamOne);
        teams.add(teamTwo);
        gameDataModel = new RankedGameDataModel();
        gameDataModel.setTeams(teams);

        rules = new RankedGameRules(gameDataModel);
    }

    @Test
    public void show_no_winner_when_no_team_scored_six_goals() {
        Team actual = gameDataModel.getWinner();

        assertThat(actual).isEqualTo(NO_TEAM);
    }

    @Test
    void show_no_winner_when_the_score_difference_is_less_than_two() {
        countGoalsFor(Team.ONE, Team.ONE, Team.ONE, Team.ONE, Team.ONE);
        countGoalsFor(TWO, TWO, TWO, TWO, TWO, TWO);

        Team actual = gameDataModel.getWinner();

        assertThat(actual).isEqualTo(NO_TEAM);
    }

    @Test
    public void show_that_the_last_scoring_team_won_when_they_scored_at_least_six_goals_with_a_lead_of_two() {
        countGoalsFor(TWO, TWO, TWO, TWO, TWO, TWO);

        Team actual = gameDataModel.getWinner();

        assertThat(actual).isEqualTo(TWO);
    }

    private void countGoalsFor(Team... teams) {
        for (Team team : teams) {
            rules.raiseScoreFor(team);
        }
    }
}
