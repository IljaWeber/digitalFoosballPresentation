package com.valtech.digitalFoosball.api;

import com.valtech.digitalFoosball.Application;
import com.valtech.digitalFoosball.api.driver.usercommands.RankedAPI;
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
@Import(com.valtech.digitalFoosball.api.RestEndpointRequestPerformer.class)
public class RankedAPIShould {
    ObjectMapper
            mapper;
    ComparableOutputModelCreator
            comparableOutput;
    @Autowired
    RestEndpointRequestPerformer
            endpointRequestPerformer;

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
    void initialise_a_ranked_game_with_individual_team_and_player_names() throws Exception {
        String expected = mapper.writeValueAsString(comparableOutput);

        endpointRequestPerformer.initializeGame(RANKED);

        String actual
                = endpointRequestPerformer.getGameValues(RANKED);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void reset_game_with_empty_team_and_player_names_and_zero_scores() throws Exception {
        comparableOutput = new ComparableOutputModelCreator();
        String expected =
                mapper.writeValueAsString(comparableOutput);
        endpointRequestPerformer
                .initializeGame(RANKED);
        endpointRequestPerformer.countGoalForTeam(ONE,
                                                  TWO,
                                                  ONE, ONE, ONE);

        endpointRequestPerformer.resetValues(RANKED);

        String actual
                = endpointRequestPerformer.getGameValues(RANKED);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void return_a_won_set() throws Exception {
        comparableOutput
                .prepareScoreOfTeamOne(6);
        comparableOutput
                .prepareScoreOfTeamTwo(3);
        comparableOutput
                .setWinnerOfSet(ONE);
        String expected
                = mapper.writeValueAsString(comparableOutput);
        endpointRequestPerformer
                .initializeGame(RANKED);
        endpointRequestPerformer.countGoalForTeam(ONE,
                                                  TWO, TWO,
                                                  ONE, ONE,
                                                  TWO,
                                                  ONE, ONE, ONE);

        String actual = endpointRequestPerformer.getGameValues(RANKED);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void return_a_match_winner() throws Exception {
        comparableOutput
                .prepareScoreOfTeamOne(4);
        comparableOutput
                .prepareScoreOfTeamTwo(6);
        comparableOutput
                .setMatchWinner(TWO);
        comparableOutput
                .setWinnerOfSet(TWO);
        String expected =
                mapper.writeValueAsString(comparableOutput);
        endpointRequestPerformer
                .initializeGame(RANKED);
        endpointRequestPerformer.countGoalForTeam(ONE,
                                                  TWO, TWO,
                                                  ONE,
                                                  TWO,
                                                  ONE, ONE,
                                                  TWO, TWO, TWO);
        endpointRequestPerformer.countGoalForTeam(ONE, ONE, ONE,
                                                  TWO, TWO,
                                                  ONE, ONE,
                                                  TWO,
                                                  ONE);
        endpointRequestPerformer
                .startANewRound(RANKED);
        endpointRequestPerformer.countGoalForTeam(TWO, TWO,
                                                  ONE, ONE, ONE,
                                                  TWO,
                                                  ONE,
                                                  TWO, TWO, TWO);

        String actual = endpointRequestPerformer.getGameValues(RANKED);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void start_a_new_round_with_same_names_but_scores_are_zero() throws Exception {
        String expected =
                mapper.writeValueAsString(comparableOutput);
        endpointRequestPerformer
                .initializeGame(RANKED);
        endpointRequestPerformer
                .countGoalForTeam(ONE, ONE, ONE,
                                  TWO,
                                  ONE, ONE,
                                  TWO, TWO, TWO,
                                  ONE);

        endpointRequestPerformer.startANewRound(RANKED);

        String actual
                = endpointRequestPerformer.getGameValues(RANKED);
        assertThat(actual).isEqualTo(expected);
    }
}
