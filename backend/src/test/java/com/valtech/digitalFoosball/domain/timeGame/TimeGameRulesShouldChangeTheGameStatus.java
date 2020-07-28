package com.valtech.digitalFoosball.domain.timeGame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.valtech.digitalFoosball.domain.timeGame.GameStatus.HALFTIME;
import static org.assertj.core.api.Assertions.assertThat;

public class TimeGameRulesShouldChangeTheGameStatus {

    private TimeGameRules timeGameRules;

    @BeforeEach
    void setUp() {
        timeGameRules = new TimeGameRules();
    }

    @Test
    void from_first_half_to_halftime_when_time_is_expired() {
        timeGameRules.timeExpired();

        assertThat(timeGameRules.getGameStatus()).isEqualTo(HALFTIME);
    }
}
