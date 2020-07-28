package com.valtech.digitalFoosball.domain.timeGame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.valtech.digitalFoosball.domain.timeGame.GameSequence.*;
import static org.assertj.core.api.Assertions.assertThat;

public class TimeGameRulesShouldChangeTheGameSequence {

    private TimeGameRules timeGameRules;

    @BeforeEach
    void setUp() {
        timeGameRules = new TimeGameRules();
    }

    @Test
    void from_first_half_to_halftime_when_time_is_expired() {
        timeGameRules.startNextGameSequence();

        assertThat(timeGameRules.getGameSequence()).isEqualTo(HALFTIME);
    }

    @Test
    void from_halftime_to_second_half() {
        timeGameRules.startNextGameSequence();

        timeGameRules.startNextGameSequence();

        assertThat(timeGameRules.getGameSequence()).isEqualTo(SECOND_HALF);
    }

    @Test
    void from_second_half_to_over() {
        timeGameRules.startNextGameSequence();
        timeGameRules.startNextGameSequence();

        timeGameRules.startNextGameSequence();

        assertThat(timeGameRules.getGameSequence()).isEqualTo(OVER);
    }
}
