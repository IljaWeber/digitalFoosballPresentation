package com.valtech.digitalFoosball.domain.timePlay;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.ranked.RankedGameDataModel;
import com.valtech.digitalFoosball.domain.ranked.RankedTeamDataModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TimeGameManipulatorShould {

    private TimeGame timeGame;
    private RankedTeamDataModel teamDataModelOne;
    private RankedTeamDataModel teamDataModelTwo;
    private RankedGameDataModel gameDataModel;

    @BeforeEach
    void setUp() {
        timeGame = new TimeGame(null);
        teamDataModelOne = new RankedTeamDataModel("T1", "P1", "P2");
        teamDataModelTwo = new RankedTeamDataModel("T2", "P3", "P4");

        List<RankedTeamDataModel> teams;
        teams = new ArrayList<>();
        teams.add(teamDataModelOne);
        teams.add(teamDataModelTwo);

        gameDataModel = new RankedGameDataModel();
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
