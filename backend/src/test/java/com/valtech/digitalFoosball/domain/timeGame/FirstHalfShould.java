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
    public void not_raise_score_when_a_team_has_a_score_of_at_least_ten() {
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

        Map<Team, Integer> gameData = firstHalf.getScoresOfTeams();
        Integer actualScoreOfTeamOne = gameData.get(ONE);
        assertThat(actualScoreOfTeamOne).isEqualTo(10);

        Integer actualScoreOfPlayerTwo = gameData.get(TWO);
        assertThat(actualScoreOfPlayerTwo).isEqualTo(8);

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

    @Test
    public void redo_last_undone_goals() {
        raiseScoreForTeam(TWO,
                          ONE, ONE, ONE,
                          TWO);
        firstHalf.undoLastGoal();

        firstHalf.redoLastGoal();

        Map<Team, Integer> scoresOfTeams = firstHalf.getScoresOfTeams();
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
}
