package com.valtech.digitalFoosball.service;

import com.valtech.digitalFoosball.model.internal.TeamDataModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RoundWinApproverShould {

    private RoundWinApprover roundWinApprover;
    private TeamDataModel teamOne;
    private TeamDataModel teamTwo;

    @BeforeEach
    void setUp() {
        roundWinApprover = new RoundWinApprover();

        teamOne = new TeamDataModel();
        teamTwo = new TeamDataModel();

        List<TeamDataModel> teams = new ArrayList<>();
        teams.add(teamOne);
        teams.add(teamTwo);

        roundWinApprover.init(teams);
    }

    @Test
    public void show_zero_when_there_is_no_winner() {
        int actual = roundWinApprover.getSetWinner();

        assertThat(actual).isEqualTo(0);
    }
}
