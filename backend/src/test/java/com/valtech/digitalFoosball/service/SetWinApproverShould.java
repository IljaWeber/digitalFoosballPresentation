package com.valtech.digitalFoosball.service;

import com.valtech.digitalFoosball.model.internal.TeamDataModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.valtech.digitalFoosball.service.GameManagerTestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

public class SetWinApproverShould {

    private SetWinApprover setWinApprover;
    private TeamDataModel teamOne;
    private TeamDataModel teamTwo;

    @BeforeEach
    void setUp() {
        setWinApprover = new SetWinApprover();

        teamOne = new TeamDataModel();
        teamTwo = new TeamDataModel();

        List<TeamDataModel> teams = new ArrayList<>();
        teams.add(teamOne);
        teams.add(teamTwo);

        setWinApprover.init(teams);
    }

    @Test
    public void show_no_winner_when_no_team_scored_six_goals() {
        int actual = setWinApprover.getSetWinner();

        assertThat(actual).isEqualTo(NO_WINNER);
    }

    @Test
    void show_no_winner_when_the_score_difference_is_less_than_two() {
        countGoalsFor(TEAM_ONE, TEAM_ONE, TEAM_ONE, TEAM_ONE, TEAM_ONE);
        countGoalsFor(TEAM_TWO, TEAM_TWO, TEAM_TWO, TEAM_TWO, TEAM_TWO, TEAM_TWO);

        int actual = setWinApprover.getSetWinner();

        assertThat(actual).isEqualTo(NO_WINNER);
    }

    @Test
    public void show_the_team_that_scored_at_least_six_goals_with_a_lead_of_two() {
        countGoalsFor(TEAM_TWO, TEAM_TWO, TEAM_TWO, TEAM_TWO, TEAM_TWO, TEAM_TWO);

        int actual = setWinApprover.getSetWinner();

        assertThat(actual).isEqualTo(TEAM_TWO);
    }

    private void countGoalsFor(int... teams) {
        for (int team : teams) {
            if (team == 1) {
                teamOne.countGoal();
            } else {
                teamTwo.countGoal();
            }
        }
    }
}
