package com.valtech.digitalFoosball.api.ranked;

import com.valtech.digitalFoosball.Application;
import com.valtech.digitalFoosball.api.helper.ComparableOutputModelCreator;
import com.valtech.digitalFoosball.api.helper.RestEndpointRequestPerformer;
import com.valtech.digitalFoosball.api.usercommands.RankedAPI;
import com.valtech.digitalFoosball.domain.common.models.GameDataModel;
import com.valtech.digitalFoosball.domain.common.models.TeamDataModel;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;

import static com.valtech.digitalFoosball.domain.common.constants.GameMode.RANKED;
import static com.valtech.digitalFoosball.domain.common.constants.Team.ONE;
import static com.valtech.digitalFoosball.domain.common.constants.Team.TWO;
import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = Application.class)
@SpringBootTest(classes = RankedAPI.class)
@SpringBootConfiguration
@Import(RestEndpointRequestPerformer.class)
public class RankedAPIShouldRedo {

    private ObjectMapper mapper;
    private ComparableOutputModelCreator comparableOutput;

    @Autowired
    private RestEndpointRequestPerformer endpointRequestPerformer;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
        List<TeamDataModel> teams = new ArrayList<>();
        comparableOutput = new ComparableOutputModelCreator();
        GameDataModel gameDataModel = new GameDataModel();
        TeamDataModel teamOne = new TeamDataModel("FC Barcelona",
                                                  "Marc-Andre ter Stegen", "Lionel Messi");
        TeamDataModel teamTwo = new TeamDataModel("FC Madrid",
                                                  "Thibaut Courtois", "Gareth Bale");

        teams.add(teamOne);
        teams.add(teamTwo);
        gameDataModel.setTeams(teams);
        endpointRequestPerformer.prepareTeamsForInitialization(teamOne, teamTwo);

        comparableOutput.prepareCompareTeamOneWithValues("FC Barcelona",
                                                         "Marc-Andre ter Stegen", "Lionel Messi");
        comparableOutput.prepareCompareTeamTwoWithValues("FC Madrid",
                                                         "Thibaut Courtois", "Gareth Bale");
    }

    @Test
    public void undone_goals() throws Exception {
        comparableOutput
                .prepareScoreOfTeamOne(2);
        comparableOutput
                .prepareScoreOfTeamTwo(2);
        String expected =
                mapper.writeValueAsString(comparableOutput);
        endpointRequestPerformer
                .initializeGame(RANKED);
        endpointRequestPerformer.countGoalForTeam(ONE,
                                                  TWO, TWO,
                                                  ONE);
        endpointRequestPerformer.undoLastGoal(RANKED);

        endpointRequestPerformer.redoLastUndoneGoal(RANKED);

        String actual
                = endpointRequestPerformer.getGameValues(RANKED);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void an_undone_set_win() throws Exception {
        comparableOutput
                .prepareScoreOfTeamOne(3);
        comparableOutput
                .prepareScoreOfTeamTwo(6);
        comparableOutput
                .setWinnerOfSet(TWO);
        String expected
                = mapper.writeValueAsString(comparableOutput);
        endpointRequestPerformer
                .initializeGame(RANKED);
        endpointRequestPerformer.countGoalForTeam(ONE, ONE,
                                                  TWO,
                                                  ONE,
                                                  TWO, TWO, TWO, TWO, TWO);
        endpointRequestPerformer.undoLastGoal(RANKED);

        endpointRequestPerformer.redoLastUndoneGoal(RANKED);

        String actual
                = endpointRequestPerformer.getGameValues(RANKED);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void an_undone_match_win() throws Exception {
        comparableOutput
                .prepareScoreOfTeamOne(7);
        comparableOutput
                .prepareScoreOfTeamTwo(9);
        comparableOutput
                .setWinnerOfSet(TWO);
        comparableOutput
                .setMatchWinner(TWO);
        String expected
                = mapper.writeValueAsString(comparableOutput);
        endpointRequestPerformer
                .initializeGame(RANKED);
        endpointRequestPerformer.countGoalForTeam(ONE, ONE,
                                                  TWO,
                                                  ONE, ONE, ONE,
                                                  TWO, TWO,
                                                  ONE);
        endpointRequestPerformer
                .startANewRound(RANKED);
        endpointRequestPerformer.countGoalForTeam(TWO, TWO, TWO,
                                                  ONE,
                                                  TWO, TWO,
                                                  ONE, ONE,
                                                  TWO);
        endpointRequestPerformer
                .startANewRound(RANKED);
        endpointRequestPerformer.countGoalForTeam(TWO,
                                                  ONE, ONE,
                                                  TWO, TWO, TWO,
                                                  ONE, ONE,
                                                  TWO,
                                                  ONE, ONE,
                                                  TWO, TWO,
                                                  ONE,
                                                  TWO, TWO);
        endpointRequestPerformer.undoLastGoal(RANKED);

        endpointRequestPerformer.redoLastUndoneGoal(RANKED);

        String actual
                = endpointRequestPerformer.getGameValues(RANKED);
        assertThat(actual).isEqualTo(expected);
    }
}
