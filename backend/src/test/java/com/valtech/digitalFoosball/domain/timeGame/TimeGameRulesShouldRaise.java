package com.valtech.digitalFoosball.domain.timeGame;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.valtech.digitalFoosball.domain.common.constants.Team.ONE;
import static com.valtech.digitalFoosball.domain.common.constants.Team.TWO;
import static org.assertj.core.api.Assertions.assertThat;

public class TimeGameRulesShouldRaise {

    private TimeGameRules timeGameRules;

    @BeforeEach
    void setUp() {
        timeGameRules = new TimeGameRules();
    }

    @Test
    void scores() {
        raiseScoreFor(ONE, ONE,
                      TWO,
                      ONE);

        int actualScore = timeGameRules.getScoreOfTeam(ONE);
        assertThat(actualScore).isEqualTo(3);

        actualScore = timeGameRules.getScoreOfTeam(TWO);
        assertThat(actualScore).isEqualTo(1);
    }

    private void raiseScoreFor(Team... teams) {
        for (Team team : teams) {
            timeGameRules.raiseScoreFor(team);
        }
    }

}
