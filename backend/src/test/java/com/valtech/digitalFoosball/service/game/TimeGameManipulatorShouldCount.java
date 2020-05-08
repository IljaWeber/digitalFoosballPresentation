package com.valtech.digitalFoosball.service.game;

import com.valtech.digitalFoosball.constants.Team;
import com.valtech.digitalFoosball.model.internal.RegularGameDataModel;
import com.valtech.digitalFoosball.model.internal.TeamDataModel;
import com.valtech.digitalFoosball.service.game.manipulator.TimeGameManipulator;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.valtech.digitalFoosball.constants.Team.ONE;
import static com.valtech.digitalFoosball.constants.Team.TWO;
import static org.assertj.core.api.Assertions.assertThat;

public class TimeGameManipulatorShouldCount {

    private final TimeGameManipulator timeGame;
    private final RegularGameDataModel gameDataModel;
    private final TeamDataModel teamDataModelOne;
    private final TeamDataModel teamDataModelTwo;

    public TimeGameManipulatorShouldCount() {
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

    @Test
    void no_goals_if_time_limit_has_been_reached() {
        countGoalForTeam(ONE, ONE,
                         TWO, TWO,
                         ONE, ONE);
        startAndAwaitTheEndOfTheTimer();

        countGoalForTeam(ONE);

        int actualScoreOfTeamOne = teamDataModelOne.getScore();
        int actualScoreOfTeamTwo = teamDataModelTwo.getScore();
        assertThat(actualScoreOfTeamOne).isEqualTo(4);
        assertThat(actualScoreOfTeamTwo).isEqualTo(2);
    }

    private void startAndAwaitTheEndOfTheTimer() {
        long loopExit = 100000;
        long loopStart = 0;
        timeGame.setTimer(1, gameDataModel);

        while (timeGame.isTimeOver() && loopStart < loopExit) {
            loopStart++;
        }
    }

    private void countGoalForTeam(Team... teams) {
        for (Team team : teams) {
            timeGame.countGoalFor(team, gameDataModel);
        }
    }
}
