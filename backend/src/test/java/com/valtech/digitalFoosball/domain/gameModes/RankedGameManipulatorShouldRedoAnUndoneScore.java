package com.valtech.digitalFoosball.domain.gameModes;

import com.valtech.digitalFoosball.domain.constants.Team;
import com.valtech.digitalFoosball.domain.gameModes.regular.models.game.RankedGameDataModel;
import com.valtech.digitalFoosball.domain.gameModes.regular.models.team.RankedTeamDataModel;
import com.valtech.digitalFoosball.domain.gameModes.regular.ranked.RankedGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static com.valtech.digitalFoosball.domain.constants.Team.ONE;
import static com.valtech.digitalFoosball.domain.constants.Team.TWO;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class RankedGameManipulatorShouldRedoAnUndoneScore {

    @Autowired
    public RankedGame gameManipulator;
    private RankedGameDataModel gameDataModel;

    @BeforeEach
    void setUp() {
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
    void if_a_score_has_been_undone_recently() {
        raiseScoreOf(ONE);
        gameManipulator.undoGoal(gameDataModel);

        gameManipulator.redoGoal(gameDataModel);

        int actual = getScoreOfTeam(ONE);
        assertThat(actual).isEqualTo(1);
    }

    private int getScoreOfTeam(Team team) {
        RankedTeamDataModel teamOne = gameDataModel.getTeam(team);
        return teamOne.getScore();
    }

    @Test
    void only_when_a_goal_was_undid_otherwise_do_nothing() {
        gameManipulator.redoGoal(gameDataModel);

        int actualScoreTeamOne = getScoreOfTeam(ONE);
        int actualScoreTeamTwo = getScoreOfTeam(TWO);
        assertThat(actualScoreTeamOne).isEqualTo(0);
        assertThat(actualScoreTeamTwo).isEqualTo(0);
    }

    @Test
    void and_raise_the_won_sets_if_necessary() {
        raiseScoreOf(ONE, ONE, ONE, ONE, ONE, ONE);
        gameManipulator.undoGoal(gameDataModel);

        gameManipulator.redoGoal(gameDataModel);

        int actual = getNumberOfWonSets(ONE);
        assertThat(actual).isEqualTo(1);
    }

    private int getNumberOfWonSets(Team team) {
        RankedTeamDataModel teamOne = gameDataModel.getTeam(team);
        return teamOne.getWonSets();
    }

    private void raiseScoreOf(Team... teams) {
        for (Team team : teams) {
            gameManipulator.countGoalFor(team, gameDataModel);
        }
    }
}
