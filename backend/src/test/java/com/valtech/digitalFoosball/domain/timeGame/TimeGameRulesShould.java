package com.valtech.digitalFoosball.domain.timeGame;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.valtech.digitalFoosball.domain.common.constants.Team.ONE;
import static com.valtech.digitalFoosball.domain.common.constants.Team.TWO;
import static org.assertj.core.api.Assertions.assertThat;

public class TimeGameRulesShould {

    TimeGameRules rules;

    @BeforeEach
    void setUp() {
        rules = new TimeGameRules();
    }

    @Test
    public void end_a_match_by_score_win_when_a_team_scores_at_least_ten_goals() {
        raiseScoreForTeam(ONE,
                          TWO, TWO,
                          ONE,
                          TWO, TWO, TWO, TWO,
                          ONE, ONE,
                          TWO,
                          ONE,
                          TWO, TWO,
                          ONE, ONE,
                          TWO,
                          ONE);

        IPlayATimeGame actual = rules.getActualGameSequence();
        assertThat(actual).isInstanceOf(EndByScoreLimit.class);
    }

    @Test
    public void not_raise_score_when_a_game_was_end_by_reaching_score_limit() {
        raiseScoreForTeam(ONE, ONE, ONE,
                          TWO, TWO,
                          ONE,
                          TWO, TWO, TWO,
                          ONE, ONE, ONE,
                          TWO,
                          ONE,
                          TWO,
                          ONE, ONE,
                          TWO,
                          ONE);

        Map<Team, Integer> gameData = rules.getActualGameSequence().getScoreOfTeams();
        Integer actualScoreOfTeamOne = gameData.get(ONE);
        assertThat(actualScoreOfTeamOne).isEqualTo(10);

        Integer actualScoreOfPlayerTwo = gameData.get(TWO);
        assertThat(actualScoreOfPlayerTwo).isEqualTo(7);
    }

    @Test
    public void undo_a_match_win_by_score_when_winning_goal_was_undone() {
        raiseScoreForTeam(ONE, ONE,
                          TWO, TWO, TWO,
                          ONE,
                          TWO, TWO,
                          ONE, ONE, ONE, ONE,
                          TWO,
                          ONE, ONE, ONE);

        rules.undo();

        IPlayATimeGame actual = rules.getActualGameSequence();
        assertThat(actual).isInstanceOf(FirstHalf.class);
    }

    @Test
    public void end_a_match_when_a_winning_goal_has_undone_and_redone() {
        raiseScoreForTeam(ONE, ONE,
                          TWO, TWO, TWO,
                          ONE,
                          TWO, TWO,
                          ONE, ONE, ONE, ONE,
                          TWO,
                          ONE, ONE, ONE);
        rules.undo();

        rules.redo();

        IPlayATimeGame actual = rules.getActualGameSequence();
        assertThat(actual).isInstanceOf(EndByScoreLimit.class);
    }

    @Test
    public void show_the_final_score_when_a_team_won_by_the_score_limit() {
        raiseScoreForTeam(ONE, ONE, ONE, ONE,
                          TWO,
                          ONE,
                          TWO, TWO,
                          ONE, ONE,
                          TWO, TWO, TWO,
                          ONE, ONE, ONE, ONE);

        IPlayATimeGame gameSequence = rules.getActualGameSequence();

        Map<Team, Integer> scoreOfTeams = gameSequence.getScoreOfTeams();
        Integer finalScoreOfTeamOne = scoreOfTeams.get(ONE);
        Integer finalScoreOfTeamTwo = scoreOfTeams.get(TWO);
        assertThat(finalScoreOfTeamOne).isEqualTo(10);
        assertThat(finalScoreOfTeamTwo).isEqualTo(6);
    }

    @Test
    public void end_first_half_when_time_is_over() {
        FirstHalfFake firstHalfFake = new FirstHalfFake(rules);
        raiseScoreForTeam(ONE,
                          TWO, TWO, TWO,
                          ONE, ONE);

        firstHalfFake.nextSequenceByTime();

        IPlayATimeGame actual = rules.getActualGameSequence();
        assertThat(actual).isInstanceOf(HalfTime.class);
    }

    private void raiseScoreForTeam(Team... teams) {
        for (Team team : teams) {
            rules.raise(team);
        }
    }

    private class FirstHalfFake extends FirstHalf {

        public FirstHalfFake(TimeGameRules timeGameRules) {
            this.rules = timeGameRules;
        }
    }
}
