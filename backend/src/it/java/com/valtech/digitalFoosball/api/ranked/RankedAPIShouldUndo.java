package com.valtech.digitalFoosball.api.ranked;

import com.valtech.digitalFoosball.Application;
import com.valtech.digitalFoosball.api.helper.ComparableOutputModelCreator;
import com.valtech.digitalFoosball.api.helper.RestEndpointRequestPerformer;
import com.valtech.digitalFoosball.api.usercommands.RankedAPI;
import com.valtech.digitalFoosball.domain.SessionIdentifier;
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
import java.util.UUID;

import static com.valtech.digitalFoosball.domain.common.constants.GameMode.RANKED;
import static com.valtech.digitalFoosball.domain.common.constants.Team.*;
import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = Application.class)
@SpringBootTest(classes = RankedAPI.class)
@SpringBootConfiguration
@Import(RestEndpointRequestPerformer.class)
public class RankedAPIShouldUndo {
    private ObjectMapper mapper;
    private ComparableOutputModelCreator comparableOutput;

    @Autowired
    private RestEndpointRequestPerformer endpointRequestPerformer;
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
    public void a_scored_goal() throws Exception {
        comparableOutput
                .prepareScoreOfTeamOne(2);
        String expected
                = mapper.writeValueAsString(comparableOutput);
        endpointRequestPerformer
                .initializeGame(RANKED);
        endpointRequestPerformer.countGoalForTeam(identifier,
                                                  ONE, ONE,
                                                  TWO);

        endpointRequestPerformer.undoLastGoal(RANKED, identifier);

        String actual
                = endpointRequestPerformer.getGameValues(RANKED, identifier);
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
        endpointRequestPerformer.countGoalForTeam(identifier,
                                                  ONE,
                                                  TWO, TWO,
                                                  ONE, ONE,
                                                  TWO,
                                                  ONE, ONE, ONE);

        endpointRequestPerformer.undoLastGoal(RANKED, identifier);

        String actual
                = endpointRequestPerformer.getGameValues(RANKED, identifier);
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
        endpointRequestPerformer.countGoalForTeam(identifier,
                                                  TWO, TWO,
                                                  ONE, ONE,
                                                  TWO,
                                                  ONE, ONE, ONE,
                                                  TWO,
                                                  ONE);
        endpointRequestPerformer
                .startANewRound(RANKED, identifier);
        endpointRequestPerformer.countGoalForTeam(identifier,
                                                  ONE,
                                                  TWO, TWO,
                                                  ONE, ONE, ONE, ONE,
                                                  TWO,
                                                  ONE);

        endpointRequestPerformer.undoLastGoal(RANKED, identifier);

        String actual
                = endpointRequestPerformer.getGameValues(RANKED, identifier);
        assertThat(actual).isEqualTo(expected);
    }

}
