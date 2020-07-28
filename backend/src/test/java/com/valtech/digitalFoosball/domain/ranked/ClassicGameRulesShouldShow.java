package com.valtech.digitalFoosball.domain.ranked;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.valtech.digitalFoosball.domain.common.constants.Team.*;
import static org.assertj.core.api.Assertions.assertThat;

public class ClassicGameRulesShouldShow {
    private ClassicGameRules rules;

    @BeforeEach
    void setUp() {
        rules = new ClassicGameRules();
    }

    @Test
    public void no_set_winner_when_no_team_scored_six_goals() {
        countGoalsFor(TWO,
                      ONE, ONE,
                      TWO,
                      ONE);

        Team actual = rules.getActualWinner();

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

        Team actual = rules.getActualWinner();

        assertThat(actual).isEqualTo(NO_TEAM);
    }

    @Test
    public void a_set_winner_when_a_team_scored_more_than_six_goals_and_there_is_a_score_difference_of_at_least_two() {
        countGoalsFor(TWO, TWO, TWO, TWO, TWO, TWO);

        Team actual = rules.getActualWinner();

        assertThat(actual).isEqualTo(TWO);
    }

    private void countGoalsFor(Team... teams) {
        for (Team team : teams) {
            rules.raiseScoreFor(team);
        }
    }
}
