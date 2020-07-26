package com.valtech.digitalFoosball.api;

import com.valtech.digitalFoosball.Application;
import com.valtech.digitalFoosball.api.driver.usercommands.DigitalFoosballUserCommandAPI;
import com.valtech.digitalFoosball.domain.ranked.RankedGameDataModel;
import com.valtech.digitalFoosball.domain.ranked.RankedTeamDataModel;
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
import static com.valtech.digitalFoosball.domain.common.constants.Team.*;
import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = Application.class)
@SpringBootTest(classes = DigitalFoosballUserCommandAPI.class)
@SpringBootConfiguration
@Import(com.valtech.digitalFoosball.api.RestEndpointRequestPerformer.class)
public class DigitalFoosballUserCommandAPIShouldUndo {
    private ObjectMapper mapper;
    private ComparableOutputModelCreator comparableOutput;

    @Autowired
    private RestEndpointRequestPerformer endpointRequestPerformer;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
        List<RankedTeamDataModel> teams = new ArrayList<>();
        comparableOutput = new ComparableOutputModelCreator();
        RankedGameDataModel gameDataModel = new RankedGameDataModel();
        RankedTeamDataModel teamOne = new RankedTeamDataModel("FC Barcelona",
                                                              "Marc-Andre ter Stegen", "Lionel Messi");
        RankedTeamDataModel teamTwo = new RankedTeamDataModel("FC Madrid",
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
    public void a_scored_goal() throws Exception {
        comparableOutput
                .prepareScoreOfTeamOne(2);
        String expected
                = mapper.writeValueAsString(comparableOutput);
        endpointRequestPerformer
                .initializeGame(RANKED);
        endpointRequestPerformer.countGoalForTeam(ONE, ONE,
                                                  TWO);

        endpointRequestPerformer.undoLastGoal();

        String actual
                = endpointRequestPerformer.getGameValues();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void a_won_set_when_winning_goal_was_undone() throws Exception {
        comparableOutput
                .prepareScoreOfTeamOne(5);
        comparableOutput
                .prepareScoreOfTeamTwo(3);
        comparableOutput
                .setWinnerOfSet(NO_TEAM);
        String expected
                = mapper.writeValueAsString(comparableOutput);
        endpointRequestPerformer
                .initializeGame(RANKED);
        endpointRequestPerformer.countGoalForTeam(ONE,
                                                  TWO, TWO,
                                                  ONE, ONE,
                                                  TWO,
                                                  ONE, ONE, ONE);

        endpointRequestPerformer.undoLastGoal();

        String actual
                = endpointRequestPerformer.getGameValues();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void a_won_match_when_winning_goal_was_undone() throws Exception {
        comparableOutput
                .prepareScoreOfTeamOne(5);
        comparableOutput
                .prepareScoreOfTeamTwo(3);
        comparableOutput
                .setWinnerOfSet(NO_TEAM);
        comparableOutput
                .setMatchWinner(NO_TEAM);
        String expected
                = mapper.writeValueAsString(comparableOutput);
        endpointRequestPerformer
                .initializeGame(RANKED);
        endpointRequestPerformer.countGoalForTeam(TWO, TWO,
                                                  ONE, ONE,
                                                  TWO,
                                                  ONE, ONE, ONE,
                                                  TWO,
                                                  ONE);
        endpointRequestPerformer
                .startANewRound();
        endpointRequestPerformer.countGoalForTeam(ONE,
                                                  TWO, TWO,
                                                  ONE, ONE, ONE, ONE,
                                                  TWO,
                                                  ONE);

        endpointRequestPerformer.undoLastGoal();

        String actual
                = endpointRequestPerformer.getGameValues();
        assertThat(actual).isEqualTo(expected);
    }

}
