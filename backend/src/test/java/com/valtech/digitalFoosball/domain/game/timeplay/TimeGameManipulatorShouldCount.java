package com.valtech.digitalFoosball.domain.game.timeplay;

import com.valtech.digitalFoosball.domain.constants.Team;
import com.valtech.digitalFoosball.domain.gameModes.regular.models.game.BaseGameDataModel;
import com.valtech.digitalFoosball.domain.gameModes.regular.models.team.RankedTeamDataModel;
import com.valtech.digitalFoosball.domain.gameModes.timePlay.TimeGame;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.valtech.digitalFoosball.domain.constants.Team.ONE;
import static com.valtech.digitalFoosball.domain.constants.Team.TWO;
import static org.assertj.core.api.Assertions.assertThat;

public class TimeGameManipulatorShouldCount {

    private final TimeGame timeGame;
    private final BaseGameDataModel gameDataModel;
    private final RankedTeamDataModel teamDataModelOne;
    private final RankedTeamDataModel teamDataModelTwo;

    public TimeGameManipulatorShouldCount() {
        timeGame = new TimeGame(null);
        teamDataModelOne = new RankedTeamDataModel("T1", "P1", "P2");
        teamDataModelTwo = new RankedTeamDataModel("T2", "P3", "P4");

        List<RankedTeamDataModel> teams;
        teams = new ArrayList<>();
        teams.add(teamDataModelOne);
        teams.add(teamDataModelTwo);

        gameDataModel = new BaseGameDataModel();
        gameDataModel.setTeams(teams);
    }

    @Test
    void goal() {
        int expected = 1;

        timeGame.countGoalFor(ONE, gameDataModel);

        int actual = teamDataModelOne.getScore();
        assertThat(actual).isEqualTo(expected);
    }

    @Disabled
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
