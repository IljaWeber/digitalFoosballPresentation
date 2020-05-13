package com.valtech.digitalFoosball.domain.game.verifier;

import com.valtech.digitalFoosball.domain.constants.Team;
import com.valtech.digitalFoosball.domain.gameModes.regular.models.TeamDataModel;
import com.valtech.digitalFoosball.domain.gameModes.timePlay.TimeGameDataModel;
import com.valtech.digitalFoosball.domain.gameModes.timePlay.TimeGameManipulator;
import com.valtech.digitalFoosball.domain.gameModes.timePlay.TimeGameWinApprover;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.valtech.digitalFoosball.domain.constants.Team.ONE;
import static com.valtech.digitalFoosball.domain.constants.Team.TWO;
import static org.assertj.core.api.Assertions.assertThat;

class TimeGameWinApproverShould {
    private TimeGameWinApprover timeGameWinApprover;
    private TimeGameDataModel gameDataModel;
    private TimeGameManipulator timeGame;

    @BeforeEach
    void setUp() {
        timeGame = new TimeGameManipulator();
        timeGameWinApprover = new TimeGameWinApprover();
        TeamDataModel teamDataModelOne = new TeamDataModel("T1", "P1", "P2");
        TeamDataModel teamDataModelTwo = new TeamDataModel("T2", "P3", "P4");

        List<TeamDataModel> teams;
        teams = new ArrayList<>();
        teams.add(teamDataModelOne);
        teams.add(teamDataModelTwo);

        gameDataModel = new TimeGameDataModel();
        gameDataModel.setTeams(teams);
    }

    @Test
    public void show_no_winner_when_no_team_scored_ten_goals() {
        countGoalsFor(ONE, ONE);
        Team actual = timeGameWinApprover.getWinner(gameDataModel);

        assertThat(actual).isEqualTo(Team.NO_TEAM);
    }

    @Test
    public void show_winner_when_the_team_scored_ten_goals() {
        countGoalsFor(ONE, ONE, ONE, ONE, ONE, ONE, ONE, ONE, ONE, ONE);
        Team actual = timeGameWinApprover.getWinner(gameDataModel);

        assertThat(actual).isEqualTo(ONE);
    }

    @Test
    public void show_winner_when_time_limit_is_reached_and_one_team_is_leading() {
        countGoalsFor(ONE, ONE, TWO, TWO, ONE, TWO, ONE, ONE, TWO, TWO, ONE);
        gameDataModel.timeLimitReached();
        Team actual = timeGameWinApprover.getWinner(gameDataModel);

        assertThat(actual).isEqualTo(ONE);
    }

    private void countGoalsFor(Team... teams) {
        for (Team team : teams) {
            timeGame.countGoalFor(team, gameDataModel);
        }
    }
}
