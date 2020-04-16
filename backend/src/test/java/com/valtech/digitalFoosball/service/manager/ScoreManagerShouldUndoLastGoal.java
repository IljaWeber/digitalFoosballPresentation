package com.valtech.digitalFoosball.service.manager;

import com.valtech.digitalFoosball.constants.Team;
import com.valtech.digitalFoosball.model.internal.TeamDataModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.SortedMap;
import java.util.TreeMap;

import static com.valtech.digitalFoosball.constants.Team.ONE;
import static com.valtech.digitalFoosball.constants.Team.TWO;
import static org.assertj.core.api.Assertions.assertThat;

public class ScoreManagerShouldUndoLastGoal {
    public ScoreManager scoreManager = new ScoreManager();
    private SortedMap<Team, TeamDataModel> teams;

    @BeforeEach
    void setUp() {
        TeamDataModel teamDataModelOne = new TeamDataModel("T1", "P1", "P2");
        TeamDataModel teamDataModelTwo = new TeamDataModel("T2", "P3", "P4");
        teams = new TreeMap<>();
        teams.put(ONE, teamDataModelOne);
        teams.put(TWO, teamDataModelTwo);
        scoreManager.setTeams(teams);
    }

    @Test
    void in_the_reversed_order_of_scoring() {
        raiseScoreOf(ONE, TWO, ONE);

        scoreManager.undoGoal();

        int actual = getScoreOfTeam(ONE);
        assertThat(actual).isEqualTo(1);
    }

    private int getScoreOfTeam(Team team) {
        TeamDataModel teamOne = teams.get(team);
        return teamOne.getScore();
    }

    @Test
    void but_if_no_scores_have_been_made_then_do_nothing() {
        scoreManager.undoGoal();

        int actualScoreTeamOne = getScoreOfTeam(ONE);
        int actualScoreTeamTwo = getScoreOfTeam(TWO);
        assertThat(actualScoreTeamOne).isEqualTo(0);
        assertThat(actualScoreTeamTwo).isEqualTo(0);
    }

    @Test
    void and_decrease_the_number_of_won_sets_when_win_condition_has_been_fulfilled() {
        raiseScoreOf(ONE, ONE, ONE, ONE, ONE, ONE);

        scoreManager.undoGoal();

        int actual = getNumberOfWonSets(ONE);
        assertThat(actual).isEqualTo(0);
    }

    private int getNumberOfWonSets(Team team) {
        TeamDataModel teamOne = teams.get(team);
        return teamOne.getWonSets();
    }

    private void raiseScoreOf(Team... teams) {
        for (Team team : teams) {
            scoreManager.countGoalFor(team);
        }
    }
}
