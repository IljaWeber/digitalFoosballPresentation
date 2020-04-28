package com.valtech.digitalFoosball.service.manager;

import com.valtech.digitalFoosball.constants.Team;
import com.valtech.digitalFoosball.model.GameDataModel;
import com.valtech.digitalFoosball.model.internal.TeamDataModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.valtech.digitalFoosball.constants.Team.ONE;
import static org.assertj.core.api.Assertions.assertThat;

public class TimeManagerShouldCount {

    private TimeManager timeManager;
    private GameDataModel gameDataModel;
    private TeamDataModel teamDataModelOne;
    private TeamDataModel teamDataModelTwo;

    @BeforeEach
    void setUp() {
        timeManager = new TimeManager();
        teamDataModelOne = new TeamDataModel("T1", "P1", "P2");
        teamDataModelTwo = new TeamDataModel("T2", "P3", "P4");

        List<TeamDataModel> teams;
        teams = new ArrayList<>();
        teams.add(teamDataModelOne);
        teams.add(teamDataModelTwo);

        gameDataModel = new GameDataModel(teams);
    }

    @Test
    void goal() {
        Team team = ONE;

        timeManager.countGoalFor(team, gameDataModel);

        assertThat(teamDataModelOne.getScore()).isEqualTo(1);
    }

    @Test
    void no_goal_if_score_limit_is_reached() {
        countGoalForTeam(ONE, ONE, ONE, ONE, ONE, ONE, ONE, ONE, ONE, ONE, ONE);

        assertThat(teamDataModelOne.getScore()).isEqualTo(10);
    }

    @Test
    void no_goals_if_time_limit_has_been_reached() throws InterruptedException {
        countGoalForTeam(ONE, ONE, ONE, ONE);
        startAndAwaitTheEndOfTheTimer();

        countGoalForTeam(ONE);

        assertThat(teamDataModelOne.getScore()).isEqualTo(4);
    }

    private void startAndAwaitTheEndOfTheTimer() throws InterruptedException {
        timeManager.setTimer(1);
        Thread.sleep(2);
    }

    private void countGoalForTeam(Team... teams) {
        for (Team team : teams) {
            timeManager.countGoalFor(team, gameDataModel);
        }
    }
}