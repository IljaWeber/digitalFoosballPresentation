package com.valtech.digitalFoosball.model.internal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TeamDataModelShould {

    private TeamDataModel teamDataModel;

    @BeforeEach
    public void setUp() {
        teamDataModel = new TeamDataModel();
    }

    @Test
    public void increase_its_score_by_one() {
        teamDataModel.countGoal();

        int actual = teamDataModel.getScore();
        assertThat(actual).isEqualTo(1);
    }

    @Test
    public void decrease_its_score_by_one() {
        teamDataModel.countGoal();

        teamDataModel.decreaseScore();

        int actual = teamDataModel.getScore();
        assertThat(actual).isEqualTo(0);
    }

    @Test
    public void increase_its_won_matches_by_one() {
        teamDataModel.increaseWonMatches();

        int actual = teamDataModel.getWonMatches();
        assertThat(actual).isEqualTo(1);
    }

    @Test
    public void have_empty_strings_as_names_and_0_as_score_after_resetValues_was_made() {
        teamDataModel.setName("T1");
        teamDataModel.setNameOfPlayerOne("P1");
        teamDataModel.setNameOfPlayerTwo("P2");
        teamDataModel.countGoal();

        teamDataModel.resetValues();

        assertThat(teamDataModel.getName()).isEqualTo("");
        assertThat(teamDataModel.getNameOfPlayerOne()).isEqualTo("");
        assertThat(teamDataModel.getNameOfPlayerTwo()).isEqualTo("");
        assertThat(teamDataModel.getScore()).isEqualTo(0);
    }

    @Test
    public void keep_the_names_and_set_the_score_to_zero_after_resetScore_was_made() {
        teamDataModel.setName("T1");
        teamDataModel.setNameOfPlayerOne("P1");
        teamDataModel.setNameOfPlayerTwo("P2");
        teamDataModel.countGoal();

        teamDataModel.resetScore();

        assertThat(teamDataModel.getName()).isEqualTo("T1");
        assertThat(teamDataModel.getNameOfPlayerOne()).isEqualTo("P1");
        assertThat(teamDataModel.getNameOfPlayerTwo()).isEqualTo("P2");
        assertThat(teamDataModel.getScore()).isEqualTo(0);
    }
}
