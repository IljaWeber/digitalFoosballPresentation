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
        rules.setGame(new FakeTimeGame());
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
    public void end_a_match_when_a_winning_goal_has_undone_then_redone_and_there_is_game_time_left() {
        raiseScoreForTeam(ONE, ONE,
                          ONE, ONE,
                          ONE, ONE,
                          ONE, ONE,
                          ONE, ONE);
        rules.undoLastGoal();

        rules.redoLastGoal();

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

        rules.undoLastGoal();

        actual = rules.getActualGameSequence();
        assertThat(actual).isInstanceOf(FirstHalf.class);
    }

    @Test
    public void determine_a_winner_when_goal_limit_has_reached_but_some_time_is_left() {
        raiseScoreForTeam(ONE, ONE,
                          ONE, ONE,
                          ONE, ONE,
                          ONE, ONE,
                          ONE, ONE);

        Team actual = rules.getMatchWinner();

        assertThat(actual).isEqualTo(ONE);
    }

    @Test
    public void start_the_half_time_when_time_limit_is_reached() {
        FirstHalfFake firstHalfFake = new FirstHalfFake(rules);

        firstHalfFake.nextSequenceByTime();

        IPlayATimeGame actual = rules.getActualGameSequence();
        assertThat(actual).isInstanceOf(HalfTime.class);
    }

    @Test
    public void not_count_goals_when_time_is_over_and_score_limit_is_not_reached() {
        FirstHalfFake firstHalfFake = new FirstHalfFake(rules);
        firstHalfFake.nextSequenceByTime();

        raiseScoreForTeam(ONE);

        IPlayATimeGame gameSequence = rules.getActualGameSequence();
        Map<Team, Integer> scoreOfTeams = gameSequence.getScoreOfTeams();
        Integer actualScore = scoreOfTeams.get(ONE);
        assertThat(actualScore).isEqualTo(0);
    }

    @Test
    public void start_second_half_when_there_was_a_changeover() {
        FirstHalfFake firstHalfFake = new FirstHalfFake(rules);
        firstHalfFake.nextSequenceByTime();

        rules.changeOver();

        IPlayATimeGame actual = rules.getActualGameSequence();
        assertThat(actual).isInstanceOf(SecondHalf.class);
    }

    @Test
    public void prepare_running_game_sequence_for_output() {
        String actual = rules.getAlternativeGameSequenceRepresentation();

        assertThat(actual).isEqualTo("First Half");
    }

    @Test
    void inform_the_game_when_the_sequence_changes() {
        FakeTimeGame game = new FakeTimeGame();
        rules.setGame(game);

        rules.setActualTimeGameSequence(new FirstHalf(rules));

        assertThat(game.isInformed).isTrue();
    }

    private void raiseScoreForTeam(Team... teams) {
        for (Team team : teams) {
            rules.raiseScoreFor(team);
        }
    }

    private class FirstHalfFake extends FirstHalf {

        public FirstHalfFake(TimeGameRules timeGameRules) {
            super(timeGameRules);
        }
    }

    private class FakeTimeGame extends TimeGame {
        boolean isInformed = false;

        public FakeTimeGame() {
            super(null, null);
        }

        @Override
        public void gameSequenceChanged() {
            isInformed = true;
        }
    }
}
