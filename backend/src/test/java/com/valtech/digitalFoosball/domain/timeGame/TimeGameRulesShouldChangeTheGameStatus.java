package com.valtech.digitalFoosball.domain.timeGame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.valtech.digitalFoosball.domain.timeGame.GameStatus.HALFTIME;
import static com.valtech.digitalFoosball.domain.timeGame.GameStatus.SECOND_HALF;
import static org.assertj.core.api.Assertions.assertThat;

public class TimeGameRulesShouldChangeTheGameStatus {

    private TimeGameRules timeGameRules;

    @BeforeEach
    void setUp() {
        timeGameRules = new TimeGameRules();
    }

    @Test
    void from_first_half_to_halftime_when_time_is_expired() {
        timeGameRules.startNextGameSequence();

        assertThat(timeGameRules.getGameStatus()).isEqualTo(HALFTIME);
    }

    @Test
    void from_halftime_to_second_half() {
        timeGameRules.startNextGameSequence();

        timeGameRules.startNextGameSequence();

        assertThat(timeGameRules.getGameStatus()).isEqualTo(SECOND_HALF);
    }
}
