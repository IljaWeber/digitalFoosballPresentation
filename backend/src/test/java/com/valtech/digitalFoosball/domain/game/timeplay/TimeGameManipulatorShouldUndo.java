package com.valtech.digitalFoosball.domain.game.timeplay;

import com.valtech.digitalFoosball.domain.constants.Team;
import com.valtech.digitalFoosball.domain.gameModes.regular.models.RankedGameDataModel;
import com.valtech.digitalFoosball.domain.gameModes.regular.models.RankedTeamDataModel;
import com.valtech.digitalFoosball.domain.gameModes.timePlay.TimeGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.valtech.digitalFoosball.domain.constants.Team.ONE;
import static com.valtech.digitalFoosball.domain.constants.Team.TWO;
import static org.assertj.core.api.Assertions.assertThat;

public class TimeGameManipulatorShouldUndo {

    private TimeGame timeGame;
    private RankedGameDataModel gameDataModel;

    @BeforeEach
    void setUp() {
        timeGame = new TimeGame(null);
        RankedTeamDataModel teamDataModelOne = new RankedTeamDataModel("T1", "P1", "P2");
        RankedTeamDataModel teamDataModelTwo = new RankedTeamDataModel("T2", "P3", "P4");

        List<RankedTeamDataModel> teams;
        teams = new ArrayList<>();
        teams.add(teamDataModelOne);
        teams.add(teamDataModelTwo);

        gameDataModel = new RankedGameDataModel();
        gameDataModel.setTeams(teams);

    }

    @Test
    void in_the_reversed_order_of_scoring() {
        countGoalForTeam(ONE, TWO, ONE);

        timeGame.undoGoal(gameDataModel);

        int actual = getScoreOfTeam(ONE);
        assertThat(actual).isEqualTo(1);
    }

    private int getScoreOfTeam(Team team) {
        RankedTeamDataModel teamOne = gameDataModel.getTeam(team);
        return teamOne.getScore();
    }

    @Test
    void but_if_no_scores_have_been_made_then_do_nothing() {
        timeGame.undoGoal(gameDataModel);

        int actualScoreTeamOne = getScoreOfTeam(ONE);
        int actualScoreTeamTwo = getScoreOfTeam(TWO);
        assertThat(actualScoreTeamOne).isEqualTo(0);
        assertThat(actualScoreTeamTwo).isEqualTo(0);
    }

    @Test
    void and_decrease_the_number_of_won_sets_when_win_condition_has_been_fulfilled() {
        countGoalForTeam(ONE, ONE, ONE, ONE, ONE, ONE, ONE, ONE, ONE, ONE);

        timeGame.undoGoal(gameDataModel);

        int actual = getNumberOfWonSets(ONE);
        assertThat(actual).isEqualTo(0);
    }

    private void countGoalForTeam(Team... teams) {
        for (Team team : teams) {
            timeGame.countGoalFor(team, gameDataModel);
        }
    }

    private int getNumberOfWonSets(Team team) {
        RankedTeamDataModel teamOne = gameDataModel.getTeam(team);
        return teamOne.getWonSets();
    }
}