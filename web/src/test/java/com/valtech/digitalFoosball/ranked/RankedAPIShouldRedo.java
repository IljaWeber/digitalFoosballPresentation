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
public class RankedAPIShouldRedo {

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
        raspberryPi = endpointRequestPerformer.registerRaspberryPi();
        identifier = new SessionIdentifier();
        identifier.setId(UUID.fromString(raspberryPi));
        GameDataModel gameDataModel = new GameDataModel();
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
    public void undone_goals() throws Exception {
        comparableOutput
                .prepareScoreOfTeamOne(2);
        comparableOutput
                .prepareScoreOfTeamTwo(2);
        String expected =
                mapper.writeValueAsString(comparableOutput);
        endpointRequestPerformer
                .initializeGame(GameMode.RANKED);
        endpointRequestPerformer.countGoalForTeam(identifier,
                                                  Team.ONE,
                                                  Team.TWO, Team.TWO,
                                                  Team.ONE);
        endpointRequestPerformer.undoLastGoal(GameMode.RANKED, identifier);

        endpointRequestPerformer.redoLastUndoneGoal(GameMode.RANKED, identifier);

        String actual
                = endpointRequestPerformer.getGameValues(GameMode.RANKED, identifier);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void an_undone_set_win() throws Exception {
        comparableOutput
                .prepareScoreOfTeamOne(3);
        comparableOutput
                .prepareScoreOfTeamTwo(6);
        comparableOutput
                .setWinnerOfSet(Team.TWO);
        String expected
                = mapper.writeValueAsString(comparableOutput);
        endpointRequestPerformer
                .initializeGame(GameMode.RANKED);
        endpointRequestPerformer.countGoalForTeam(identifier,
                                                  Team.ONE, Team.ONE,
                                                  Team.TWO,
                                                  Team.ONE,
                                                  Team.TWO, Team.TWO, Team.TWO, Team.TWO, Team.TWO);
        endpointRequestPerformer.undoLastGoal(GameMode.RANKED, identifier);

        endpointRequestPerformer.redoLastUndoneGoal(GameMode.RANKED, identifier);

        String actual
                = endpointRequestPerformer.getGameValues(GameMode.RANKED, identifier);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void an_undone_match_win() throws Exception {
        comparableOutput
                .prepareScoreOfTeamOne(7);
        comparableOutput
                .prepareScoreOfTeamTwo(9);
        comparableOutput
                .setWinnerOfSet(Team.TWO);
        comparableOutput
                .setMatchWinner(Team.TWO);
        String expected
                = mapper.writeValueAsString(comparableOutput);
        endpointRequestPerformer
                .initializeGame(GameMode.RANKED);
        endpointRequestPerformer.countGoalForTeam(identifier,
                                                  Team.ONE, Team.ONE,
                                                  Team.TWO,
                                                  Team.ONE, Team.ONE, Team.ONE,
                                                  Team.TWO, Team.TWO,
                                                  Team.ONE);
        endpointRequestPerformer
                .startANewRound(GameMode.RANKED, identifier);
        endpointRequestPerformer.countGoalForTeam(identifier,
                                                  Team.TWO, Team.TWO, Team.TWO,
                                                  Team.ONE,
                                                  Team.TWO, Team.TWO,
                                                  Team.ONE, Team.ONE,
                                                  Team.TWO);
        endpointRequestPerformer
                .startANewRound(GameMode.RANKED, identifier);
        endpointRequestPerformer.countGoalForTeam(identifier,
                                                  Team.TWO,
                                                  Team.ONE, Team.ONE,
                                                  Team.TWO, Team.TWO, Team.TWO,
                                                  Team.ONE, Team.ONE,
                                                  Team.TWO,
                                                  Team.ONE, Team.ONE,
                                                  Team.TWO, Team.TWO,
                                                  Team.ONE,
                                                  Team.TWO, Team.TWO);
        endpointRequestPerformer.undoLastGoal(GameMode.RANKED, identifier);

        endpointRequestPerformer.redoLastUndoneGoal(GameMode.RANKED, identifier);

        String actual
                = endpointRequestPerformer.getGameValues(GameMode.RANKED, identifier);
        assertThat(actual).isEqualTo(expected);
    }
}
