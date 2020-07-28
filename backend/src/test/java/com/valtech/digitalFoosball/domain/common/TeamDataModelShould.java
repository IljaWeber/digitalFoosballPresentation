package com.valtech.digitalFoosball.domain.common;

import com.valtech.digitalFoosball.domain.ranked.TeamDataModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TeamDataModelShould {

    private TeamDataModel teamDataModel;

    @BeforeEach
    void setUp() {
        teamDataModel = new TeamDataModel();
    }

    @Test
    public void save_initialized_team_and_player_names() {
        teamDataModel
                .setName("FC Barcelona");
        teamDataModel
                .setNameOfPlayerOne("Marc-Andre ter Stegen");
        teamDataModel
                .setNameOfPlayerTwo("Lionel Messi");

        List<String> actual = convert(teamDataModel);

        assertThat(actual).containsExactly("FC Barcelona",
                                           "Marc-Andre ter Stegen",
                                           "Lionel Messi");
    }

    private List<String> convert(TeamDataModel teamDataModel) {
        List<String> team = new ArrayList<>();

        team.add(teamDataModel.getName());
        team.add(teamDataModel.getNameOfPlayerOne());
        team.add(teamDataModel.getNameOfPlayerTwo());

        return team;
    }
}
