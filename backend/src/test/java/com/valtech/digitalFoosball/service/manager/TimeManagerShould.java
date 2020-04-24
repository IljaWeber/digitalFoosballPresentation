package com.valtech.digitalFoosball.service.manager;

import com.valtech.digitalFoosball.constants.Team;
import com.valtech.digitalFoosball.model.internal.TeamDataModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.SortedMap;
import java.util.TreeMap;

import static org.assertj.core.api.Assertions.assertThat;

public class TimeManagerShould {

    Team one = Team.ONE;
    private TimeManager timeManager;
    private TeamDataModel teamDataModelOne;
    private TeamDataModel teamDataModelTwo;
    private final SortedMap<Team, TeamDataModel> teams = new TreeMap<>();

    @BeforeEach
    void setUp() {
        timeManager = new TimeManager();

        teamDataModelOne = new TeamDataModel("Orange", "Goalie", "Striker");
        teamDataModelTwo = new TeamDataModel("Green", "Goalie", "Striker");

        teams.put(Team.ONE, teamDataModelOne);
        teams.put(Team.TWO, teamDataModelTwo);

        timeManager.setTeams(teams);
    }

    @Test
    void count_goal() {
        Team team = Team.ONE;

        timeManager.countGoalFor(team);

        assertThat(teamDataModelOne.getScore()).isEqualTo(1);
    }

    @Test
    void not_count_goal_if_score_limit_is_reached() {
        countGoalForTeam(one, one, one, one, one, one, one, one, one, one, one);

        assertThat(teamDataModelOne.getScore()).isEqualTo(10);
    }

    @Test
    void not_count_goals_if_time_limit_has_been_reached() throws InterruptedException {
        countGoalForTeam(one, one, one, one);
        startAndExpireTimer();

        countGoalForTeam(one);

        assertThat(teamDataModelOne.getScore()).isEqualTo(4);
    }

    private void startAndExpireTimer() throws InterruptedException {
        timeManager.setTimer(1);
        Thread.sleep(2);
    }

    private void countGoalForTeam(Team... teams) {
        for (Team team : teams) {
            timeManager.countGoalFor(team);
        }
    }
}
