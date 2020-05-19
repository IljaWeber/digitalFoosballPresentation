package com.valtech.digitalFoosball.domain.model;

import com.valtech.digitalFoosball.domain.gameModes.regular.models.team.RankedTeamDataModel;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RankedTeamDataModelShould {

    private final RankedTeamDataModel teamDataModel;

    public RankedTeamDataModelShould() {
        this.teamDataModel = new RankedTeamDataModel();
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
    public void keep_the_names_and_set_the_score_to_zero_after_resetScore_was_made() {
        teamDataModel.countGoal();

        teamDataModel.changeover();

        List<String> actual = convert(teamDataModel);
        assertThat(actual).containsExactly("T1", "P1", "P2");
        assertThat(teamDataModel.getScore()).isEqualTo(0);
    }

    private List<String> convert(RankedTeamDataModel teamDataModel) {
        List<String> team = new ArrayList<>();

        team.add(teamDataModel.getName());
        team.add(teamDataModel.getNameOfPlayerOne());
        team.add(teamDataModel.getNameOfPlayerTwo());

        return team;
    }
}
