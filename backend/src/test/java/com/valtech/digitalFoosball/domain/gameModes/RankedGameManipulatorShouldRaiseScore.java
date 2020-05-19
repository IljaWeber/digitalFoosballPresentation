package com.valtech.digitalFoosball.domain.gameModes;

import com.valtech.digitalFoosball.domain.constants.Team;
import com.valtech.digitalFoosball.domain.gameModes.regular.models.RegularGameDataModel;
import com.valtech.digitalFoosball.domain.gameModes.regular.models.TeamDataModel;
import com.valtech.digitalFoosball.domain.gameModes.regular.ranked.RankedGameManipulator;
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
public class RankedGameManipulatorShouldRaiseScore {

    @Autowired
    public RankedGameManipulator gameManipulator;
    private RegularGameDataModel gameDataModel;

    @BeforeEach
    void setUp() {
        TeamDataModel teamDataModelOne = new TeamDataModel("T1", "P1", "P2");
        TeamDataModel teamDataModelTwo = new TeamDataModel("T2", "P3", "P4");

        List<TeamDataModel> teams;
        teams = new ArrayList<>();
        teams.add(teamDataModelOne);
        teams.add(teamDataModelTwo);

        gameDataModel = new RegularGameDataModel();
        gameDataModel.setTeams(teams);
    }

    @Test
    void in_the_order_of_scoring() {
        raiseScoreOf(ONE, ONE, ONE, TWO);

        int scoreOfTeamOne = getScoreOfTeam(ONE);
        int scoreOfTeamTwo = getScoreOfTeam(TWO);
        assertThat(scoreOfTeamOne).isEqualTo(3);
        assertThat(scoreOfTeamTwo).isEqualTo(1);
    }

    private int getScoreOfTeam(Team team) {
        TeamDataModel teamOne = gameDataModel.getTeam(team);
        return teamOne.getScore();
    }

    @Test
    void only_until_the_win_condition_is_fulfilled() {
        raiseScoreOf(ONE, ONE, ONE, ONE, ONE, ONE, ONE);

        int actual = getScoreOfTeam(ONE);
        assertThat(actual).isEqualTo(6);
    }

    private void raiseScoreOf(Team... teams) {
        for (Team team : teams) {
            gameManipulator.countGoalFor(team, gameDataModel);
        }
    }
}
