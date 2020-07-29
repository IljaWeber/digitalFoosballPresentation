package com.valtech.digitalFoosball.domain.timeGame;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.valtech.digitalFoosball.domain.common.constants.Team.ONE;
import static com.valtech.digitalFoosball.domain.common.constants.Team.TWO;
import static org.assertj.core.api.Assertions.assertThat;

class FirstHalfShould {

    private IPlayATimeGame firstHalf;

    @BeforeEach
    void setUp() {
        firstHalf = new FirstHalf(new TimeGameRules());
    }

    @Test
    public void raise_score() {

        raiseScoreForTeam(ONE,
                          TWO, TWO,
                          ONE);

        Map<Team, Integer> gameData = firstHalf.getScoresOfTeams();
        Integer actualScoreOfTeamOne = gameData.get(ONE);
        assertThat(actualScoreOfTeamOne).isEqualTo(2);

        Integer actualScoreOfPlayerTwo = gameData.get(TWO);
        assertThat(actualScoreOfPlayerTwo).isEqualTo(2);
    }

    @Test
    public void undo_last_scored_goals() {
        raiseScoreForTeam(TWO, TWO, TWO,
                          ONE);

        firstHalf.undoLastGoal();
        firstHalf.undoLastGoal();

        Map<Team, Integer> scoresOfTeams = firstHalf.getScoresOfTeams();
        Integer scoreOfTeamOne = scoresOfTeams.get(ONE);
        Integer scoreOfTeamTwo = scoresOfTeams.get(TWO);
        assertThat(scoreOfTeamOne).isEqualTo(0);
        assertThat(scoreOfTeamTwo).isEqualTo(2);
    }

    private void raiseScoreForTeam(Team... teams) {
        for (Team team : teams) {
            firstHalf.raiseScoreFor(team);
        }
    }
}
