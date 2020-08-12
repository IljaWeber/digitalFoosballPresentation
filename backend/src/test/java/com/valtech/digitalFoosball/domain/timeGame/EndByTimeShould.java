package com.valtech.digitalFoosball.domain.timeGame;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.timeGame.sequences.EndByTime;
import org.junit.jupiter.api.Test;

import java.util.Stack;

import static com.valtech.digitalFoosball.domain.common.constants.Team.*;
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

    @Test
    public void determine_no_winner_when_time_is_over_and_goal_limit_was_not_reached_but_both_teams_has_an_equal_score() {
        Stack<Team> overview = new Stack<>();
        overview.push(ONE);
        overview.push(TWO);
        EndByTime endByTime = new EndByTime(overview);

        Team actual = endByTime.getMatchWinner();

        assertThat(actual).isEqualTo(NO_TEAM);

    }
}
