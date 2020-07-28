package com.valtech.digitalFoosball.domain.timeGame;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.valtech.digitalFoosball.domain.common.constants.Team.ONE;
import static com.valtech.digitalFoosball.domain.common.constants.Team.TWO;
import static org.assertj.core.api.Assertions.assertThat;

public class TimeGameRulesShouldRedo {

    private TimeGameRules timeGameRules;

    @BeforeEach
    void setUp() {
        timeGameRules = new TimeGameRules();
    }

    @Test
    void the_undone_goals_in_the_reverse_order() {
        raiseScoreFor(ONE, ONE,
                      TWO,
                      ONE,
                      TWO);
        timeGameRules.undoLastGoal();
        timeGameRules.undoLastGoal();
        timeGameRules.undoLastGoal();

        timeGameRules.redoLastGoal();
        timeGameRules.redoLastGoal();

        int actualScore = timeGameRules.getScoreOfTeam(ONE);
        assertThat(actualScore).isEqualTo(3);
        actualScore = timeGameRules.getScoreOfTeam(TWO);
        assertThat(actualScore).isEqualTo(1);
    }

    @Test
    void just_when_there_are_undone_goals() {
        raiseScoreFor(ONE, ONE,
                      TWO,
                      ONE,
                      TWO);

        timeGameRules.redoLastGoal();

        int actualScore = timeGameRules.getScoreOfTeam(ONE);
        assertThat(actualScore).isEqualTo(3);
        actualScore = timeGameRules.getScoreOfTeam(TWO);
        assertThat(actualScore).isEqualTo(2);
    }

    private void raiseScoreFor(Team... teams) {
        for (Team team : teams) {
            timeGameRules.raiseScoreFor(team);
        }
    }
}
