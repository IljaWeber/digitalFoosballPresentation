package com.valtech.digitalFoosball.domain.timeGame;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.valtech.digitalFoosball.domain.common.constants.Team.ONE;
import static com.valtech.digitalFoosball.domain.common.constants.Team.TWO;
import static org.assertj.core.api.Assertions.assertThat;

public class TimeGameRulesShould {

    private TimeGameRules rules;

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

    private void raiseScoreForTeam(Team... teams) {
        for (Team team : teams) {
            rules.raise(team);
        }
    }
}
