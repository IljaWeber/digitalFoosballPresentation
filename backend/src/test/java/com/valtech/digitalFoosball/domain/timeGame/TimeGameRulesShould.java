package com.valtech.digitalFoosball.domain.timeGame;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.valtech.digitalFoosball.domain.common.constants.Team.ONE;
import static org.assertj.core.api.Assertions.assertThat;

public class TimeGameRulesShould {

    private TimeGameRules rules;
    private IPlayATimeGame actual;

    @BeforeEach
    void setUp() {
        rules = new TimeGameRules();
    }

    @Test
    public void end_a_match_when_a_team_scores_ten_goals_before_time_is_over() {
        raiseScoreForTeam(ONE, ONE,
                          ONE, ONE,
                          ONE, ONE,
                          ONE, ONE,
                          ONE, ONE);

        actual = rules.getActualGameSequence();
        assertThat(actual).isInstanceOf(EndByScoreLimit.class);
    }

    @Test
    public void end_a_match_when_a_winning_goal_has_undone_and_redone_and_there_is_game_time_left() {
        raiseScoreForTeam(ONE, ONE,
                          ONE, ONE,
                          ONE, ONE,
                          ONE, ONE,
                          ONE, ONE);
        rules.undo();

        rules.redo();

        actual = rules.getActualGameSequence();
        assertThat(actual).isInstanceOf(EndByScoreLimit.class);
    }

    @Test
    public void not_count_goals_when_score_limit_is_reached_but_there_is_time_left() {
        raiseScoreForTeam(ONE, ONE,
                          ONE, ONE,
                          ONE, ONE,
                          ONE, ONE,
                          ONE, ONE,
                          ONE);

        Map<Team, Integer> gameData = rules.getActualGameSequence().getScoreOfTeams();
        Integer actualScore = gameData.get(ONE);
        assertThat(actualScore).isEqualTo(10);
    }

    @Test
    public void undo_a_match_win_when_winning_goal_was_undone_and_time_is_left() {
        raiseScoreForTeam(ONE, ONE,
                          ONE, ONE,
                          ONE, ONE,
                          ONE, ONE,
                          ONE, ONE);

        rules.undo();

        actual = rules.getActualGameSequence();
        assertThat(actual).isInstanceOf(FirstHalf.class);
    }

    @Test
    public void end_first_half_when_time_is_over() {
        FirstHalfFake firstHalfFake = new FirstHalfFake(rules);
        raiseScoreForTeam(ONE, ONE, ONE);

        firstHalfFake.nextSequenceByTime();

        actual = rules.getActualGameSequence();
        assertThat(actual).isInstanceOf(HalfTime.class);
    }

    private void raiseScoreForTeam(Team... teams) {
        for (Team team : teams) {
            rules.raise(team);
        }
    }

    private class FirstHalfFake extends FirstHalf {

        public FirstHalfFake(TimeGameRules timeGameRules) {
            super(timeGameRules);
        }
    }
}
