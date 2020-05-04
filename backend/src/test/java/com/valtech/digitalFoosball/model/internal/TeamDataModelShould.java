package com.valtech.digitalFoosball.model.internal;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TeamDataModelShould {

    private final TeamDataModel teamDataModel;


    public TeamDataModelShould() {
        this.teamDataModel = new TeamDataModel();
        teamDataModel.setName("T1");
        teamDataModel.setNameOfPlayerOne("P1");
        teamDataModel.setNameOfPlayerTwo("P2");
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
    public void keep_the_names_and_set_the_score_to_zero_after_resetScore_was_made() {
        teamDataModel.countGoal();

        teamDataModel.changeover();

        List<String> actual = convert(teamDataModel);
        assertThat(actual).containsExactly("T1", "P1", "P2");
        assertThat(teamDataModel.getScore()).isEqualTo(0);
    }

    private List<String> convert(TeamDataModel teamDataModel) {
        List<String> team = new ArrayList<>();

        team.add(teamDataModel.getName());
        team.add(teamDataModel.getNameOfPlayerOne());
        team.add(teamDataModel.getNameOfPlayerTwo());

        return team;
    }
}
