package com.valtech.digitalFoosball.service.game;

import com.valtech.digitalFoosball.constants.Team;
import com.valtech.digitalFoosball.model.GameDataModel;
import com.valtech.digitalFoosball.model.internal.TeamDataModel;
import com.valtech.digitalFoosball.service.game.modes.TimeGameManipulator;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.valtech.digitalFoosball.constants.Team.ONE;
import static com.valtech.digitalFoosball.constants.Team.TWO;
import static org.assertj.core.api.Assertions.assertThat;

public class TimeGameManipulatorShouldCount {

    private TimeGameManipulator timeGame;
    private GameDataModel gameDataModel;
    private TeamDataModel teamDataModelOne;
    private TeamDataModel teamDataModelTwo;

    public TimeGameManipulatorShouldCount() {
        timeGame = new TimeGameManipulator(null);
        teamDataModelOne = new TeamDataModel("T1", "P1", "P2");
        teamDataModelTwo = new TeamDataModel("T2", "P3", "P4");

        List<TeamDataModel> teams;
        teams = new ArrayList<>();
        teams.add(teamDataModelOne);
        teams.add(teamDataModelTwo);

        gameDataModel = new GameDataModel();
        gameDataModel.setTeams(teams);
    }

    @Test
    void goal() {
        int expected = 1;

        timeGame.countGoalFor(ONE, gameDataModel);

        int actual = teamDataModelOne.getScore();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void no_goal_if_score_limit_is_reached() {

        countGoalForTeam(ONE, ONE, ONE, ONE,
                         TWO, TWO,
                         ONE, ONE, ONE,
                         TWO, TWO, TWO,
                         ONE, ONE, ONE, ONE);

        int actualScoreOfTeamOne = teamDataModelOne.getScore();
        int actualScoreOfTeamTwo = teamDataModelTwo.getScore();
        assertThat(actualScoreOfTeamOne).isEqualTo(10);
        assertThat(actualScoreOfTeamTwo).isEqualTo(5);
    }

    @Disabled
    @Test
    void no_goals_if_time_limit_has_been_reached() throws InterruptedException {
        countGoalForTeam(ONE, ONE, ONE, ONE);
        startAndAwaitTheEndOfTheTimer();

        countGoalForTeam(ONE);

        assertThat(teamDataModelOne.getScore()).isEqualTo(4);
    }

    private void startAndAwaitTheEndOfTheTimer() throws InterruptedException {
        timeGame.setTimer(1);
        Thread.sleep(5);
    }

    private void countGoalForTeam(Team... teams) {
        for (Team team : teams) {
            timeGame.countGoalFor(team, gameDataModel);
        }
    }
}
