package com.valtech.digitalFoosball.api.ranked;

import com.valtech.digitalFoosball.Application;
import com.valtech.digitalFoosball.api.helper.ComparableOutputModelCreator;
import com.valtech.digitalFoosball.api.helper.RestEndpointRequestPerformer;
import com.valtech.digitalFoosball.api.usercommands.RankedAPI;
import com.valtech.digitalFoosball.domain.common.models.GameDataModel;
import com.valtech.digitalFoosball.domain.common.models.TeamDataModel;
import com.valtech.digitalFoosball.domain.common.session.SessionIdentifier;
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
import java.util.UUID;

import static com.valtech.digitalFoosball.domain.common.constants.GameMode.RANKED;
import static com.valtech.digitalFoosball.domain.common.constants.Team.ONE;
import static com.valtech.digitalFoosball.domain.common.constants.Team.TWO;
import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = Application.class)
@SpringBootTest(classes = RankedAPI.class)
@SpringBootConfiguration
@Import(RestEndpointRequestPerformer.class)
public class RankedAPIShould {
    ObjectMapper
            mapper;
    ComparableOutputModelCreator
            comparableOutput;
    @Autowired
    RestEndpointRequestPerformer
            endpointRequestPerformer;
    private String raspberryPi;
    private SessionIdentifier identifier;

    @BeforeEach
    void setUp() throws Exception {
        mapper = new ObjectMapper();
        List<TeamDataModel> teams = new ArrayList<>();
        comparableOutput = new ComparableOutputModelCreator();
        GameDataModel gameDataModel = new GameDataModel();
        raspberryPi = endpointRequestPerformer.registerRaspberryPi();
        identifier = new SessionIdentifier();

        identifier.setId(UUID.fromString(raspberryPi));

        TeamDataModel teamOne = new TeamDataModel("FC Barcelona",
                                                  "Marc-Andre ter Stegen", "Lionel Messi");
        TeamDataModel teamTwo = new TeamDataModel("FC Madrid",
                                                  "Thibaut Courtois", "Gareth Bale");

        teams.add(teamOne);
        teams.add(teamTwo);
        gameDataModel.setTeams(teams);
        endpointRequestPerformer.prepareTeamsForInitialization(teamOne, teamTwo, identifier);

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
                = endpointRequestPerformer.getGameValues(RANKED, identifier);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void reset_game_with_empty_team_and_player_names_and_zero_scores() throws Exception {
        comparableOutput = new ComparableOutputModelCreator();
        String expected =
                mapper.writeValueAsString(comparableOutput);
        endpointRequestPerformer
                .initializeGame(RANKED);
        endpointRequestPerformer.countGoalForTeam(identifier,
                                                  ONE,
                                                  TWO,
                                                  ONE, ONE, ONE);

        endpointRequestPerformer.resetValues(RANKED, identifier);

        String actual
                = endpointRequestPerformer.getGameValues(RANKED, identifier);
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
        endpointRequestPerformer.countGoalForTeam(identifier,
                                                  ONE,
                                                  TWO, TWO,
                                                  ONE, ONE,
                                                  TWO,
                                                  ONE, ONE, ONE);

        String actual = endpointRequestPerformer.getGameValues(RANKED, identifier);

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
        endpointRequestPerformer.countGoalForTeam(identifier,
                                                  ONE,
                                                  TWO, TWO,
                                                  ONE,
                                                  TWO,
                                                  ONE, ONE,
                                                  TWO, TWO, TWO);
        endpointRequestPerformer.countGoalForTeam(identifier,
                                                  ONE, ONE, ONE,
                                                  TWO, TWO,
                                                  ONE, ONE,
                                                  TWO,
                                                  ONE);
        endpointRequestPerformer
                .startANewRound(RANKED, identifier);
        endpointRequestPerformer.countGoalForTeam(identifier,
                                                  TWO, TWO,
                                                  ONE, ONE, ONE,
                                                  TWO,
                                                  ONE,
                                                  TWO, TWO, TWO);

        String actual = endpointRequestPerformer.getGameValues(RANKED, identifier);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void start_a_new_round_with_same_names_but_scores_are_zero() throws Exception {
        String expected =
                mapper.writeValueAsString(comparableOutput);
        endpointRequestPerformer
                .initializeGame(RANKED);
        endpointRequestPerformer
                .countGoalForTeam(identifier,
                                  ONE, ONE, ONE,
                                  TWO,
                                  ONE, ONE,
                                  TWO, TWO, TWO,
                                  ONE);

        endpointRequestPerformer.startANewRound(RANKED, identifier);

        String actual
                = endpointRequestPerformer.getGameValues(RANKED, identifier);
        assertThat(actual).isEqualTo(expected);
    }
}
