package com.valtech.digitalFoosball.ranked;

import com.valtech.digitalFoosball.Application;
import com.valtech.digitalFoosball.domain.common.constants.GameMode;
import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.models.GameDataModel;
import com.valtech.digitalFoosball.domain.common.models.TeamDataModel;
import com.valtech.digitalFoosball.domain.common.session.SessionIdentifier;
import com.valtech.digitalFoosball.helper.ComparableOutputModelCreator;
import com.valtech.digitalFoosball.helper.RestEndpointRequestPerformer;
import com.valtech.digitalFoosball.rest.RankedAPI;
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
        endpointRequestPerformer.initializeGame(GameMode.RANKED);

        String actual
                = endpointRequestPerformer.getGameValues(GameMode.RANKED, identifier);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void reset_game_with_empty_team_and_player_names_and_zero_scores() throws Exception {
        comparableOutput = new ComparableOutputModelCreator();
        String expected =
                mapper.writeValueAsString(comparableOutput);
        endpointRequestPerformer
                .initializeGame(GameMode.RANKED);
        endpointRequestPerformer.countGoalForTeam(identifier,
                                                  Team.ONE,
                                                  Team.TWO,
                                                  Team.ONE, Team.ONE, Team.ONE);

        endpointRequestPerformer.resetValues(GameMode.RANKED, identifier);

        String actual
                = endpointRequestPerformer.getGameValues(GameMode.RANKED, identifier);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void return_a_won_set() throws Exception {
        comparableOutput
                .prepareScoreOfTeamOne(6);
        comparableOutput
                .prepareScoreOfTeamTwo(3);
        comparableOutput
                .setWinnerOfSet(Team.ONE);
        String expected
                = mapper.writeValueAsString(comparableOutput);
        endpointRequestPerformer
                .initializeGame(GameMode.RANKED);
        endpointRequestPerformer.countGoalForTeam(identifier,
                                                  Team.ONE,
                                                  Team.TWO, Team.TWO,
                                                  Team.ONE, Team.ONE,
                                                  Team.TWO,
                                                  Team.ONE, Team.ONE, Team.ONE);

        String actual = endpointRequestPerformer.getGameValues(GameMode.RANKED, identifier);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void return_a_match_winner() throws Exception {
        comparableOutput
                .prepareScoreOfTeamOne(4);
        comparableOutput
                .prepareScoreOfTeamTwo(6);
        comparableOutput
                .setMatchWinner(Team.TWO);
        comparableOutput
                .setWinnerOfSet(Team.TWO);
        String expected =
                mapper.writeValueAsString(comparableOutput);
        endpointRequestPerformer
                .initializeGame(GameMode.RANKED);
        endpointRequestPerformer.countGoalForTeam(identifier,
                                                  Team.ONE,
                                                  Team.TWO, Team.TWO,
                                                  Team.ONE,
                                                  Team.TWO,
                                                  Team.ONE, Team.ONE,
                                                  Team.TWO, Team.TWO, Team.TWO);
        endpointRequestPerformer.countGoalForTeam(identifier,
                                                  Team.ONE, Team.ONE, Team.ONE,
                                                  Team.TWO, Team.TWO,
                                                  Team.ONE, Team.ONE,
                                                  Team.TWO,
                                                  Team.ONE);
        endpointRequestPerformer
                .startANewRound(GameMode.RANKED, identifier);
        endpointRequestPerformer.countGoalForTeam(identifier,
                                                  Team.TWO, Team.TWO,
                                                  Team.ONE, Team.ONE, Team.ONE,
                                                  Team.TWO,
                                                  Team.ONE,
                                                  Team.TWO, Team.TWO, Team.TWO);

        String actual = endpointRequestPerformer.getGameValues(GameMode.RANKED, identifier);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void start_a_new_round_with_same_names_but_scores_are_zero() throws Exception {
        String expected =
                mapper.writeValueAsString(comparableOutput);
        endpointRequestPerformer
                .initializeGame(GameMode.RANKED);
        endpointRequestPerformer
                .countGoalForTeam(identifier,
                                  Team.ONE, Team.ONE, Team.ONE,
                                  Team.TWO,
                                  Team.ONE, Team.ONE,
                                  Team.TWO, Team.TWO, Team.TWO,
                                  Team.ONE);

        endpointRequestPerformer.startANewRound(GameMode.RANKED, identifier);

        String actual
                = endpointRequestPerformer.getGameValues(GameMode.RANKED, identifier);
        assertThat(actual).isEqualTo(expected);
    }
}
