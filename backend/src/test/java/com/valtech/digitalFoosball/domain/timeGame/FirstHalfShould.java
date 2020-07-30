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
        raiseScoreForTeam(ONE,
                          TWO, TWO,
                          ONE);

        Map<Team, Integer> gameData = firstHalf.getScoreOfTeams();
        Integer actualScoreOfTeamOne = gameData.get(ONE);
        assertThat(actualScoreOfTeamOne).isEqualTo(2);

        Integer actualScoreOfPlayerTwo = gameData.get(TWO);
        assertThat(actualScoreOfPlayerTwo).isEqualTo(2);
    }

    @Test
    public void end_game_when_a_team_reaches_the_score_limit() {
        raiseScoreForTeam(ONE, ONE, ONE,
                          TWO, TWO,
                          ONE,
                          TWO, TWO, TWO,
                          ONE, ONE, ONE,
                          TWO,
                          ONE,
                          TWO,
                          ONE, ONE);

        assertThat(timeGameRules.game).isInstanceOf(EndByScoreLimit.class);
    }

    @Test
    public void end_first_half_when_time_is_over() {
        raiseScoreForTeam(TWO,
                          ONE, ONE,
                          TWO, TWO,
                          ONE);

        firstHalf.nextSequenceByTime();

        assertThat(timeGameRules.game).isInstanceOf(HalfTime.class);
    }

    @Test
    public void undo_last_scored_goals() {
        raiseScoreForTeam(TWO, TWO, TWO,
                          ONE);

        firstHalf.undoLastGoal();
        firstHalf.undoLastGoal();

        Map<Team, Integer> scoresOfTeams = firstHalf.getScoreOfTeams();
        Integer scoreOfTeamOne = scoresOfTeams.get(ONE);
        Integer scoreOfTeamTwo = scoresOfTeams.get(TWO);
        assertThat(scoreOfTeamOne).isEqualTo(0);
        assertThat(scoreOfTeamTwo).isEqualTo(2);
    }

    @Test
    public void redo_last_undone_goals() {
        raiseScoreForTeam(TWO,
                          ONE, ONE, ONE,
                          TWO);
        firstHalf.undoLastGoal();

        firstHalf.redoLastGoal();

        Map<Team, Integer> scoresOfTeams = firstHalf.getScoreOfTeams();
        Integer scoreOfTeamOne = scoresOfTeams.get(ONE);
        Integer scoreOfTeamTwo = scoresOfTeams.get(TWO);
        assertThat(scoreOfTeamOne).isEqualTo(3);
        assertThat(scoreOfTeamTwo).isEqualTo(2);
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
