package com.valtech.digitalFoosball.api;

import com.google.gson.Gson;
import com.valtech.digitalFoosball.Application;
import com.valtech.digitalFoosball.api.driver.usercommands.DigitalFoosballRestAPI;
import com.valtech.digitalFoosball.domain.GameController;
import com.valtech.digitalFoosball.domain.constants.GameMode;
import com.valtech.digitalFoosball.domain.constants.Team;
import com.valtech.digitalFoosball.domain.gameModes.models.InitDataModel;
import com.valtech.digitalFoosball.domain.gameModes.models.output.game.GameOutputModel;
import com.valtech.digitalFoosball.domain.gameModes.models.output.game.RegularGameOutputModel;
import com.valtech.digitalFoosball.domain.gameModes.regular.models.game.BaseGameDataModel;
import com.valtech.digitalFoosball.domain.gameModes.regular.models.team.RankedTeamDataModel;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.valtech.digitalFoosball.domain.constants.GameMode.AD_HOC;
import static com.valtech.digitalFoosball.domain.constants.GameMode.RANKED;
import static com.valtech.digitalFoosball.domain.constants.Team.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ContextConfiguration(classes = Application.class)
@SpringBootTest(classes = DigitalFoosballRestAPI.class)
public class DigitalFoosballRestApiShould {

    private final Gson gson;
    private final ObjectMapper mapper;
    private final RankedTeamDataModel teamOne;
    private final RankedTeamDataModel teamTwo;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private GameController game;
    private String json;
    private MvcResult result;
    private List<RankedTeamDataModel> teams;
    private BaseGameDataModel gameDataModel;
    private InitDataModel initDataModel;
    private MockHttpServletRequestBuilder builder;

    public DigitalFoosballRestApiShould() {
        gson = new Gson();
        teams = new ArrayList<>();
        mapper = new ObjectMapper();
        initDataModel = new InitDataModel();
        teamOne = new RankedTeamDataModel("T1", "P1", "P2");
        teamTwo = new RankedTeamDataModel("T2", "P3", "P4");
    }

    @BeforeEach
    void setUp() {
        gameDataModel = new BaseGameDataModel();
        gameDataModel.setTeam(ONE, teamOne);
        gameDataModel.setTeam(TWO, teamTwo);
        gameDataModel.setSetWinner(NO_TEAM);
        prepareTeamsForInitialization(teamOne, teamTwo);
    }

    @Test
    void initialise_an_ad_hoc_match_with_default_values_for_the_teams() throws Exception {
        prepareTeamsForInitialization(new RankedTeamDataModel("Orange", "Goalie", "Striker"),
                                      new RankedTeamDataModel("Green", "Goalie", "Striker"));
        prepareComparableAdHocInitialisation();
        String expected = prepareComparableValuesWithMatchWinner(NO_TEAM);

        prepareGameWithMode(AD_HOC);

        String actual = getGameStatus();
        assertThat(actual).isEqualTo(expected);
    }

    private void prepareComparableAdHocInitialisation() {
        gameDataModel.setTeam(ONE, new RankedTeamDataModel("Orange", "Goalie", "Striker"));
        gameDataModel.setTeam(TWO, new RankedTeamDataModel("Green", "Goalie", "Striker"));
    }

    @Test
    void initialise_a_ranked_game_with_individual_team_and_player_names() throws Exception {
        String expected = prepareComparableValuesWithMatchWinner(NO_TEAM);

        prepareGameWithMode(RANKED);

        String actual = getGameStatus();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void undo_scored_goal() throws Exception {
        countComparableScoreForTeam(ONE);
        String expected = prepareComparableValuesWithMatchWinner(NO_TEAM);
        MockHttpServletRequestBuilder undo = MockMvcRequestBuilders.put("/api/undo");
        prepareGameWithMode(RANKED);
        countGoalForTeam(ONE, ONE);

        prepareGameCommands(undo);

        String actual = getGameStatus();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void redo_undone_goals() throws Exception {
        countComparableScoreForTeam(ONE, TWO, TWO);
        String expected = prepareComparableValuesWithMatchWinner(NO_TEAM);
        MockHttpServletRequestBuilder redo = MockMvcRequestBuilders.put("/api/redo");
        prepareGameWithMode(RANKED);
        countGoalForTeam(ONE, TWO, TWO);
        game.undoGoal();
        game.undoGoal();

        prepareGameCommands(redo, redo);

        String actual = getGameStatus();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void reset_game_with_empty_team_and_player_names_and_zero_scores() throws Exception {
        gameDataModel = new BaseGameDataModel();
        String expected = prepareComparableValuesWithMatchWinner(NO_TEAM);
        MockHttpServletRequestBuilder reset = MockMvcRequestBuilders.delete("/api/reset");
        prepareGameWithMode(RANKED);
        countGoalForTeam(ONE, TWO);

        prepareGameCommands(reset);

        String actual = getGameStatus();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void return_a_won_set() throws Exception {
        countComparableScoreForTeam(ONE, ONE, ONE, ONE, ONE, ONE);
        prepareComparableSetWinValues(ONE, ONE);
        String expected = prepareComparableValuesWithMatchWinner(NO_TEAM);
        prepareGameWithMode(RANKED);
        countGoalForTeam(ONE, ONE, ONE, ONE, ONE, ONE);

        String actual = getGameStatus();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void return_a_match_winner() throws Exception {
        countComparableScoreForTeam(ONE, ONE, ONE, ONE, ONE, ONE);
        prepareComparableSetWinValues(ONE, ONE, ONE);
        String expected = prepareComparableValuesWithMatchWinner(ONE);
        prepareGameWithMode(RANKED);
        countGoalForTeam(ONE, ONE, ONE, ONE, ONE, ONE);
        game.changeover();
        countGoalForTeam(ONE, ONE, ONE, ONE, ONE, ONE);

        String actual = getGameStatus();

        assertThat(actual).isEqualTo(expected);
    }

    private void prepareComparableSetWinValues(Team lastSetWinner, Team... increasingSetForTeams) {
        for (Team increasingSetForTeam : increasingSetForTeams) {
            gameDataModel.getTeam(increasingSetForTeam).increaseWonSets();
        }
        gameDataModel.setSetWinner(lastSetWinner);
    }

    @Test
    public void reset_score_values_but_team_names_are_not_affected() throws Exception {
        MockHttpServletRequestBuilder newRound = MockMvcRequestBuilders.post("/api/newRound");
        gameDataModel.getTeam(ONE).increaseWonSets();
        String expected = prepareComparableValuesWithMatchWinner(NO_TEAM);
        prepareGameWithMode(RANKED);
        countGoalForTeam(ONE, ONE, ONE, ONE, ONE, ONE);

        prepareGameCommands(newRound);

        String actual = getGameStatus();
        assertThat(actual).isEqualTo(expected);
    }

    private void prepareGameCommands(MockHttpServletRequestBuilder... gameCommands) throws Exception {
        for (MockHttpServletRequestBuilder gameCommand : gameCommands) {
            mockMvc.perform(gameCommand);
        }
    }

    private void prepareTeamsForInitialization(RankedTeamDataModel teamOne, RankedTeamDataModel teamTwo) {
        this.initDataModel = new InitDataModel();
        this.teams = new ArrayList<>();
        teams.add(teamOne);
        teams.add(teamTwo);

        initDataModel.setTeams(teams);
        json = gson.toJson(initDataModel);
    }

    private String prepareComparableValuesWithMatchWinner(Team matchWinner) throws IOException {
        GameOutputModel expectedValues = new RegularGameOutputModel(gameDataModel);
        expectedValues.setMatchWinner(matchWinner);

        return mapper.writeValueAsString(expectedValues);
    }

    private void countComparableScoreForTeam(Team... teams) {
        for (Team team : teams) {
            gameDataModel.getTeam(team).countGoal();
        }
    }

    private void prepareGameWithMode(GameMode gameMode) throws Exception {
        String mode = "";

        switch (gameMode) {
            case AD_HOC:
                mode = "adhoc";
                break;
            case RANKED:
                mode = "ranked";
        }

        builder = MockMvcRequestBuilders.post("/api/init/" + mode);
        builder.contentType(MediaType.APPLICATION_JSON_VALUE).content(json);

        mockMvc.perform(builder);
    }

    private String getGameStatus() throws Exception {
        builder = MockMvcRequestBuilders.get("/api/game");
        result = mockMvc.perform(builder).andExpect(status().isOk()).andReturn();

        return result.getResponse().getContentAsString();
    }

    private void countGoalForTeam(Team... teams) throws Exception {
        builder = MockMvcRequestBuilders.post("/api/raise");
        for (Team team : teams) {
            int hardwareValueOfTeam = team.hardwareValue();
            builder.contentType(MediaType.APPLICATION_JSON_VALUE).content(String.valueOf(hardwareValueOfTeam));
            mockMvc.perform(builder);
        }
    }
}
