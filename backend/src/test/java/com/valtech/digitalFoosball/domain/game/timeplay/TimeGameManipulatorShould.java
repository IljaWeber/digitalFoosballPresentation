package com.valtech.digitalFoosball.domain.game.timeplay;

import com.valtech.digitalFoosball.domain.constants.Team;
import com.valtech.digitalFoosball.domain.gameModes.regular.models.RegularGameDataModel;
import com.valtech.digitalFoosball.domain.gameModes.regular.models.TeamDataModel;
import com.valtech.digitalFoosball.domain.gameModes.timePlay.TimeGameManipulator;
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
    private RegularGameDataModel gameDataModel;

    @BeforeEach
    void setUp() {
        timeGame = new TimeGameManipulator(null);
        teamDataModelOne = new TeamDataModel("T1", "P1", "P2");
        teamDataModelTwo = new TeamDataModel("T2", "P3", "P4");

        List<TeamDataModel> teams;
        teams = new ArrayList<>();
        teams.add(teamDataModelOne);
        teams.add(teamDataModelTwo);

        gameDataModel = new RegularGameDataModel();
        gameDataModel.setTeams(teams);
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
