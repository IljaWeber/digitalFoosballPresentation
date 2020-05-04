package com.valtech.digitalFoosball.service.game;

import com.valtech.digitalFoosball.constants.Team;
import com.valtech.digitalFoosball.model.GameDataModel;
import com.valtech.digitalFoosball.model.internal.TeamDataModel;
import com.valtech.digitalFoosball.service.game.modes.TimeGameManipulator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TimeGameManipulatorShould {

    private TimeGameManipulator timeGame;
    private TeamDataModel teamDataModelOne;
    private TeamDataModel teamDataModelTwo;
    private GameDataModel gameDataModel;

    @BeforeEach
    void setUp() {
        timeGame = new TimeGameManipulator();
        teamDataModelOne = new TeamDataModel("T1", "P1", "P2");
        teamDataModelTwo = new TeamDataModel("T2", "P3", "P4");

        List<TeamDataModel> teams;
        teams = new ArrayList<>();
        teams.add(teamDataModelOne);
        teams.add(teamDataModelTwo);

        gameDataModel = new GameDataModel(teams);
    }

    @Disabled
    @Test
    void changeover() {
        assertThat(teamDataModelOne.getScore()).isEqualTo(1);
    }

    private void countGoalForTeam(Team... teams) {
        for (Team team : teams) {
            timeGame.countGoalFor(team, gameDataModel);
        }
    }
}
