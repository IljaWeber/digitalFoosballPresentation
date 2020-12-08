package com.valtech.digitalFoosball.domain.timeGame;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.usecases.timeGame.IPlayATimeGame;
import com.valtech.digitalFoosball.domain.usecases.timeGame.TimeGameRules;
import com.valtech.digitalFoosball.domain.usecases.timeGame.sequences.EndByScore;
import com.valtech.digitalFoosball.domain.usecases.timeGame.sequences.FirstHalf;
import com.valtech.digitalFoosball.domain.usecases.timeGame.sequences.HalfTime;
import com.valtech.digitalFoosball.domain.usecases.timeGame.service.MatchScores;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

        MatchScores matchScores = firstHalf.getMatchScores();
        int actual = matchScores.getScoreOfTeamOne();
        assertThat(actual).isEqualTo(2);
    }

    @Test
    public void end_game_when_a_team_reaches_the_score_limit() {
        raiseScoreForTeam(ONE, ONE,
                          ONE, ONE,
                          ONE, ONE,
                          ONE, ONE,
                          ONE, ONE);

        assertThat(timeGameRules.game).isInstanceOf(EndByScore.class);
    }

    @Test
    public void end_first_half_when_time_is_over() {
        firstHalf.timeRanDown();

        assertThat(timeGameRules.game).isInstanceOf(HalfTime.class);
    }

    @Test
    public void undo_last_scored_goals() {
        raiseScoreForTeam(ONE, ONE);

        firstHalf.undoLastGoal();

        MatchScores matchScores = firstHalf.getMatchScores();
        int scoreOfTeamOne = matchScores.getScoreOfTeamOne();
        assertThat(scoreOfTeamOne).isEqualTo(1);
    }

    @Test
    public void redo_last_undone_goals() {
        raiseScoreForTeam(TWO, TWO);
        firstHalf.undoLastGoal();

        firstHalf.redoLastGoal();

        MatchScores matchScores = firstHalf.getMatchScores();
        int scoreOfTeamTwo = matchScores.getScoreOfTeamTwo();
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
