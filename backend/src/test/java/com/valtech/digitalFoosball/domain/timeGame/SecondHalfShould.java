package com.valtech.digitalFoosball.domain.timeGame;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Stack;

import static com.valtech.digitalFoosball.domain.common.constants.Team.ONE;
import static com.valtech.digitalFoosball.domain.common.constants.Team.TWO;
import static org.assertj.core.api.Assertions.assertThat;

class SecondHalfShould {

    private SecondHalf secondHalf;
    private TimeGameRulesFake rules;

    @BeforeEach
    void setUp() {
        Stack<Team> goalsOfThePastHalf = new Stack<>();
        rules = new TimeGameRulesFake();
        secondHalf = new SecondHalf(goalsOfThePastHalf, rules);
    }

    @Test
    public void raise_score() {
        secondHalf.raiseScoreFor(ONE);

        Map<Team, Integer> scoreOfTeams = secondHalf.getScoreOfTeams();
        Integer actual = scoreOfTeams.get(ONE);
        assertThat(actual).isEqualTo(1);
    }

    @Test
    public void end_game_when_a_team_reaches_the_score_limit() {
        raiseScoreForTeam(TWO, TWO,
                          TWO, TWO,
                          TWO, TWO,
                          TWO, TWO,
                          TWO, TWO);

        assertThat(rules.game).isInstanceOf(EndByScoreLimit.class);
    }

    @Test
    public void undo_last_scored_goals() {
        raiseScoreForTeam(ONE);

        secondHalf.undoLastGoal();

        Map<Team, Integer> scoresOfTeams = secondHalf.getScoreOfTeams();
        Integer scoreOfTeamOne = scoresOfTeams.get(ONE);
        assertThat(scoreOfTeamOne).isEqualTo(0);
    }

    @Test
    public void redo_last_undone_goals() {
        raiseScoreForTeam(TWO);
        secondHalf.undoLastGoal();

        secondHalf.redoLastGoal();

        Map<Team, Integer> scoresOfTeams = secondHalf.getScoreOfTeams();
        Integer scoreOfTeamTwo = scoresOfTeams.get(TWO);
        assertThat(scoreOfTeamTwo).isEqualTo(1);
    }

    private void raiseScoreForTeam(Team... teams) {
        for (Team team : teams) {
            secondHalf.raiseScoreFor(team);
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