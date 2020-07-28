package com.valtech.digitalFoosball.domain.timeGame;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.valtech.digitalFoosball.domain.common.constants.Team.ONE;
import static com.valtech.digitalFoosball.domain.common.constants.Team.TWO;
import static org.assertj.core.api.Assertions.assertThat;

public class TimeGameRulesShouldDetermineWinner {

    private TimeGameRules timeGameRules;

    @BeforeEach
    void setUp() {
        timeGameRules = new TimeGameRules();
    }

    @Test
    void when_one_team_scored_ten_goals() {
        raiseScoreFor(ONE, ONE, ONE, ONE, ONE,
                      TWO, TWO,
                      ONE, ONE, ONE,
                      TWO,
                      ONE, ONE);

        Team actual = timeGameRules.getMatchWinner();
        assertThat(actual).isEqualTo(ONE);
    }

    @Test
    void when_the_time_is_over_and_one_team_is_leading() {
        raiseScoreFor(ONE,
                      TWO, TWO);
        timeGameRules.startNextGameSequence();
        timeGameRules.startNextGameSequence();
        timeGameRules.startNextGameSequence();

        Team actual = timeGameRules.getMatchWinner();

        assertThat(actual).isEqualTo(TWO);
    }

    private void raiseScoreFor(Team... teams) {
        for (Team team : teams) {
            timeGameRules.raiseScoreFor(team);
        }
    }
}
