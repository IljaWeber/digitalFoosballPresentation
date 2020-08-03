package com.valtech.digitalFoosball.domain.timeGame.iljaRefactoring;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import org.junit.jupiter.api.Test;

import java.util.Stack;

import static com.valtech.digitalFoosball.domain.common.constants.Team.ONE;
import static org.assertj.core.api.Assertions.assertThat;

class EndByTimeShould {

    @Test
    public void determine_a_match_winner_when_time_is_over_and_goal_limit_was_not_reached_but_a_team_has_a_higher_score_than_the_other() {
        Stack<Team> overview = new Stack<>();
        overview.push(ONE);
        EndByTime endByTime = new EndByTime(overview);

        Team actual = endByTime.getMatchWinner();

        assertThat(actual).isEqualTo(ONE);
    }

}
