package com.valtech.digitalFoosball.domain.timeGame;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.valtech.digitalFoosball.domain.common.constants.Team.ONE;
import static com.valtech.digitalFoosball.domain.timeGame.GameState.*;
import static org.assertj.core.api.Assertions.assertThat;

public class TimeGameRulesShouldChangeTheIPlayAGameState {

    private TimeGameRules timeGameRules;

    @BeforeEach
    void setUp() {
        timeGameRules = new TimeGameRules();
    }

    @Test
    void from_first_half_to_halftime_when_time_ran_down() {
        timeGameRules.timeRanDown();

        assertThat(timeGameRules.getGameState()).isEqualTo(HALFTIME);
    }

    @Test
    void from_halftime_to_second_half() {
        timeGameRules.timeRanDown();

        timeGameRules.changeOver();

        assertThat(timeGameRules.getGameState()).isEqualTo(SECOND_HALF);
    }

    @Test
    void from_second_half_to_over() {
        timeGameRules.timeRanDown();
        timeGameRules.changeOver();

        timeGameRules.timeRanDown();

        GameState actual = timeGameRules.getGameState();
        assertThat(actual).isEqualTo(OVER);
    }

    @Test
    void to_over_when_a_team_reached_ten_goals() {
        raiseScoreFor(ONE, ONE, ONE, ONE, ONE, ONE, ONE, ONE, ONE, ONE);

        GameState actual = timeGameRules.getGameState();
        assertThat(actual).isEqualTo(OVER);
    }

    private void raiseScoreFor(Team... teams) {
        for (Team team : teams) {
            timeGameRules.raiseScoreFor(team);
        }
    }
}
