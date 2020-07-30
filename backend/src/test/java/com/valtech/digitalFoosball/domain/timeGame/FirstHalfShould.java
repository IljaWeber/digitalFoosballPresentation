package com.valtech.digitalFoosball.domain.timeGame;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.valtech.digitalFoosball.domain.common.constants.Team.ONE;
import static com.valtech.digitalFoosball.domain.common.constants.Team.TWO;
import static org.assertj.core.api.Assertions.assertThat;

class FirstHalfShould {
    private FirstHalf firstHalf;
    private TimeGameRulesFake timeGameRules;

    @BeforeEach
    void setUp() {
        timeGameRules = new TimeGameRulesFake();
        firstHalf = new FirstHalf(timeGameRules);
    }

    @Test
    public void raise_score() {
        raiseScoreForTeam(ONE, ONE);

        Map<Team, Integer> gameData = firstHalf.getScoreOfTeams();
        Integer actualScoreOfTeamOne = gameData.get(ONE);
        assertThat(actualScoreOfTeamOne).isEqualTo(2);
    }

    @Test
    public void end_game_when_a_team_reaches_the_score_limit() {
        raiseScoreForTeam(ONE, ONE,
                          ONE, ONE,
                          ONE, ONE,
                          ONE, ONE,
                          ONE, ONE);

        assertThat(timeGameRules.game).isInstanceOf(EndByScoreLimit.class);
    }

    @Test
    public void end_first_half_when_time_is_over() {
        firstHalf.nextSequenceByTime();

        assertThat(timeGameRules.game).isInstanceOf(HalfTime.class);
    }

    @Test
    public void undo_last_scored_goals() {
        raiseScoreForTeam(ONE, ONE);

        firstHalf.undoLastGoal();

        Map<Team, Integer> scoresOfTeams = firstHalf.getScoreOfTeams();
        Integer scoreOfTeamOne = scoresOfTeams.get(ONE);
        assertThat(scoreOfTeamOne).isEqualTo(1);
    }

    @Test
    public void redo_last_undone_goals() {
        raiseScoreForTeam(TWO, TWO);
        firstHalf.undoLastGoal();

        firstHalf.redoLastGoal();

        Map<Team, Integer> scoresOfTeams = firstHalf.getScoreOfTeams();
        Integer scoreOfTeamTwo = scoresOfTeams.get(TWO);
        assertThat(scoreOfTeamTwo).isEqualTo(2);
    }

    @Test
    public void reset_the_scores() {
        raiseScoreForTeam(ONE, ONE);

        firstHalf.resetGame();

        Map<Team, Integer> scoreOfTeams = firstHalf.getScoreOfTeams();
        Integer actual = scoreOfTeams.get(ONE);
        assertThat(actual).isEqualTo(0);
    }

    private void raiseScoreForTeam(Team... teams) {
        for (Team team : teams) {
            firstHalf.raiseScoreFor(team);
        }
    }

    private class TimeGameRulesFake extends TimeGameRules {
        public IPlayATimeGame game;

        @Override
        public void setActualTimeGameSequence(IPlayATimeGame gameSequence) {
            game = gameSequence;
        }
    }
}
