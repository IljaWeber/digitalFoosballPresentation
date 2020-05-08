package com.valtech.digitalFoosball.service.verifier;

import com.valtech.digitalFoosball.constants.Team;
import com.valtech.digitalFoosball.model.internal.RegularGameDataModel;
import com.valtech.digitalFoosball.model.internal.TeamDataModel;
import com.valtech.digitalFoosball.service.game.manipulator.TimeGameManipulator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.valtech.digitalFoosball.constants.Team.ONE;
import static org.assertj.core.api.Assertions.assertThat;

class TimeGameManipulatorSetWinVerifierShould {
    private TimeGameSetWinVerifier timeGameSetWinVerifier;
    private RegularGameDataModel gameDataModel;
    private TeamDataModel teamDataModelOne;
    private TeamDataModel teamDataModelTwo;
    private TimeGameManipulator timeGame;

    @BeforeEach
    void setUp() {
        timeGame = new TimeGameManipulator(null);
        timeGameSetWinVerifier = new TimeGameSetWinVerifier();
        teamDataModelOne = new TeamDataModel("T1", "P1", "P2");
        teamDataModelTwo = new TeamDataModel("T2", "P3", "P4");

        List<TeamDataModel> teams;
        teams = new ArrayList<>();
        teams.add(teamDataModelOne);
        teams.add(teamDataModelTwo);

        gameDataModel = new RegularGameDataModel();
        gameDataModel.setTeams(teams);
    }

    @Test
    public void show_no_winner_when_no_team_scored_ten_goals() {
        countGoalsFor(ONE, ONE);
        Team actual = timeGameSetWinVerifier.getWinner(gameDataModel);

        assertThat(actual).isEqualTo(Team.NO_TEAM);
    }

    @Test
    public void show_winner_when_the_team_scored_ten_goals() {
        countGoalsFor(ONE, ONE, ONE, ONE, ONE, ONE, ONE, ONE, ONE, ONE);
        Team actual = timeGameSetWinVerifier.getWinner(gameDataModel);

        assertThat(actual).isEqualTo(ONE);
    }

    private void countGoalsFor(Team... teams) {
        for (Team team : teams) {
            timeGame.countGoalFor(team, gameDataModel);
        }
    }
}
