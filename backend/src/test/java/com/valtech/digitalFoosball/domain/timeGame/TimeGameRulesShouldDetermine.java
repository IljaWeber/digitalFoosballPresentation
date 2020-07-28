package com.valtech.digitalFoosball.domain.timeGame;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.valtech.digitalFoosball.domain.common.constants.Team.*;
import static org.assertj.core.api.Assertions.assertThat;

public class TimeGameRulesShouldDetermine {

    private TimeGameRules timeGameRules;

    @BeforeEach
    void setUp() {
        timeGameRules = new TimeGameRules();
    }

    @Test
    void winner_when_one_team_scored_ten_goals() {
        raiseScoreFor(ONE, ONE, ONE, ONE, ONE,
                      TWO, TWO,
                      ONE, ONE, ONE,
                      TWO,
                      ONE, ONE);

        Team actual = timeGameRules.getMatchWinner();
        assertThat(actual).isEqualTo(ONE);
    }

    @Test
    void winner_when_the_time_is_over_and_one_team_is_leading() {
        raiseScoreFor(ONE,
                      TWO, TWO);
        timeGameRules.startNextGameSequence();
        timeGameRules.startNextGameSequence();
        timeGameRules.startNextGameSequence();

        Team actual = timeGameRules.getMatchWinner();

        assertThat(actual).isEqualTo(TWO);
    }

    @Test
    void no_winner_when_no_team_is_leading_after_the_time_ran_down() {
        raiseScoreFor(ONE,
                      TWO);
        timeGameRules.startNextGameSequence();
        timeGameRules.startNextGameSequence();
        timeGameRules.startNextGameSequence();

        Team actual = timeGameRules.getMatchWinner();

        assertThat(actual).isEqualTo(NO_TEAM);
    }

    private void raiseScoreFor(Team... teams) {
        for (Team team : teams) {
            timeGameRules.raiseScoreFor(team);
        }
    }
}
