package com.valtech.digitalFoosball.domain.timeGame;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Stack;

import static com.valtech.digitalFoosball.domain.common.constants.Team.ONE;
import static org.assertj.core.api.Assertions.assertThat;

class SecondHalfShould {

    private SecondHalf secondHalf;

    @BeforeEach
    void setUp() {
        Stack<Team> goalsOfThePastHalf = new Stack<>();
        TimeGameRules rules = new TimeGameRules();
        secondHalf = new SecondHalf(goalsOfThePastHalf, rules);
    }

    @Test
    public void raise_score() {
        secondHalf.raiseScoreFor(ONE);

        Map<Team, Integer> scoreOfTeams = secondHalf.getScoreOfTeams();
        Integer actual = scoreOfTeams.get(ONE);
        assertThat(actual).isEqualTo(1);
    }
}
