package com.valtech.digitalFoosball.domain.timeGame;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.timeGame.sequences.*;
import org.junit.jupiter.api.Test;

import java.util.Stack;

import static com.valtech.digitalFoosball.domain.common.constants.Team.ONE;
import static org.assertj.core.api.Assertions.assertThat;

public class EndByScoreShould {

    @Test
    void switch_to_halftime_when_undo_is_made_after_score_limit_was_reached_and_the_time_ran_down_afterwards() {
        FakeTimeGameRules rules = new FakeTimeGameRules();
        FirstHalf previous = new FirstHalf(rules);
        EndByScore endByScore = new EndByScore(previous, rules, ONE);
        endByScore.timeRanDown();

        endByScore.undoLastGoal();

        assertThat(rules.sequence).isInstanceOf(HalfTime.class);
    }

    @Test
    void end_the_game_by_time_condition_when_undo_is_made_after_score_limit_was_reached_and_the_time_ran_down_afterwards() {
        FakeTimeGameRules rules = new FakeTimeGameRules();
        Stack<Team> stack = new Stack<>();
        SecondHalf previous = new SecondHalf(stack, stack, rules);
        EndByScore endByScore = new EndByScore(previous, rules, ONE);
        endByScore.timeRanDown();

        endByScore.undoLastGoal();

        assertThat(rules.sequence).isInstanceOf(EndByTime.class);
    }



    private class FakeTimeGameRules extends TimeGameRules {
        public IPlayATimeGame sequence = null;

        @Override
        public void setActualTimeGameSequence(IPlayATimeGame gameSequence) {
            sequence = gameSequence;
        }
    }
}
