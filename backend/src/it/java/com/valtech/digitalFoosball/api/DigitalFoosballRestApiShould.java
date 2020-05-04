package com.valtech.digitalFoosball.api;

import com.google.gson.Gson;
import com.valtech.digitalFoosball.Application;
import com.valtech.digitalFoosball.constants.Team;
import com.valtech.digitalFoosball.model.GameDataModel;
import com.valtech.digitalFoosball.model.input.InitDataModel;
import com.valtech.digitalFoosball.model.internal.TeamDataModel;
import com.valtech.digitalFoosball.model.output.GameOutputModel;
import com.valtech.digitalFoosball.service.game.GameController;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.valtech.digitalFoosball.constants.Team.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ContextConfiguration(classes = Application.class)
@SpringBootTest(classes = DigitalFoosballAPI.class)
public class DigitalFoosballRestApiShould {

    private final int ADHOC_GAME = 0;
    private final int RANKED_GAME = 1;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GameController game;

    private final Gson gson;
    private String json;
    private MvcResult result;
    private final ObjectMapper mapper;
    private final TeamDataModel teamOne;
    private final TeamDataModel teamTwo;
    private List<TeamDataModel> teams;
    private GameDataModel gameDataModel;
    private InitDataModel initDataModel;
    private MockHttpServletRequestBuilder builder;

    public DigitalFoosballRestApiShould() {
        gson = new Gson();
        teams = new ArrayList<>();
        mapper = new ObjectMapper();
        gameDataModel = new GameDataModel();
        initDataModel = new InitDataModel();
        teamOne = new TeamDataModel("T1", "P1", "P2");
        teamTwo = new TeamDataModel("T2", "P3", "P4");
    }

    @BeforeEach
    void setUp() throws IOException {
        gameDataModel = new GameDataModel();
        gameDataModel.setTeam(ONE, teamOne);
        gameDataModel.setTeam(TWO, teamTwo);
        gameDataModel.setSetWinner(NO_TEAM);
        prepareTeamsForInitialization(teamOne, teamTwo);
    }

    @Test
    void initialise_an_ad_hoc_match_with_default_values_for_the_teams() throws Exception {
        prepareTeamsForInitialization(new TeamDataModel("Orange", "Goalie", "Striker"),
                                      new TeamDataModel("Green", "Goalie", "Striker"));

        gameDataModel.setTeam(ONE, new TeamDataModel("Orange", "Goalie", "Striker"));
        gameDataModel.setTeam(TWO, new TeamDataModel("Green", "Goalie", "Striker"));

        performInitialisationRequestFor(ADHOC_GAME);

        builder = MockMvcRequestBuilders.get("/api/game");
        result = mockMvc.perform(builder).andExpect(status().isOk()).andReturn();
        assertThat(getActualResponseBody()).isEqualTo(getExpectedBody(NO_TEAM));
    }

    @Test
    void initialise_a_ranked_game_with_individual_team_and_player_names() throws Exception {
        performInitialisationRequestFor(RANKED_GAME);
        builder = MockMvcRequestBuilders.get("/api/game");

        result = mockMvc.perform(builder).andExpect(status().isOk()).andReturn();
        assertThat(getActualResponseBody()).isEqualTo(getExpectedBody(NO_TEAM));
    }

    @Test
    public void undo_scored_goal() throws Exception {
        performInitialisationRequestFor(RANKED_GAME);
        countGoalForExpectedTeam(ONE);
        countGoalForTeam(ONE, ONE);

        builder = MockMvcRequestBuilders.put("/api/undo");
        mockMvc.perform(builder);

        assertThat(getActualResponseBody()).isEqualTo(getExpectedBody(NO_TEAM));
    }

    @Test
    public void redo_undone_goals() throws Exception {
        performInitialisationRequestFor(RANKED_GAME);
        countGoalForExpectedTeam(ONE, TWO, TWO);
        countGoalForTeam(ONE, TWO, TWO);
        game.undoGoal();
        game.undoGoal();

        builder = MockMvcRequestBuilders.put("/api/redo");
        mockMvc.perform(builder);
        mockMvc.perform(builder);

        assertThat(getActualResponseBody()).isEqualTo(getExpectedBody(NO_TEAM));
    }

    @Test
    public void reset_game_with_empty_team_and_player_names_and_zero_scores() throws Exception {
        performInitialisationRequestFor(RANKED_GAME);
        gameDataModel.setTeam(ONE, new TeamDataModel("", "", ""));
        gameDataModel.setTeam(TWO, new TeamDataModel("", "", ""));
        countGoalForTeam(ONE, TWO);
        builder = MockMvcRequestBuilders.delete("/api/reset");

        mockMvc.perform(builder);

        assertThat(getActualResponseBody()).isEqualTo(getExpectedBody(NO_TEAM));
    }

    @Test
    public void return_a_won_set() throws Exception {
        performInitialisationRequestFor(RANKED_GAME);
        countGoalForExpectedTeam(ONE, ONE, ONE, ONE, ONE, ONE);
        gameDataModel.getTeam(ONE).increaseWonSets();
        gameDataModel.setSetWinner(ONE);

        countGoalForTeam(ONE, ONE, ONE, ONE, ONE, ONE);

        assertThat(getActualResponseBody()).isEqualTo(getExpectedBody(NO_TEAM));
    }

    @Test
    public void return_a_match_winner() throws Exception {
        performInitialisationRequestFor(RANKED_GAME);
        countGoalForExpectedTeam(ONE, ONE, ONE, ONE, ONE, ONE);
        gameDataModel.getTeam(ONE).increaseWonSets();
        gameDataModel.getTeam(ONE).increaseWonSets();
        gameDataModel.getTeam(ONE).increaseWonMatches();
        gameDataModel.setSetWinner(ONE);

        countGoalForTeam(ONE, ONE, ONE, ONE, ONE, ONE);
        game.changeover();
        countGoalForTeam(ONE, ONE, ONE, ONE, ONE, ONE);

        assertThat(getActualResponseBody()).isEqualTo(getExpectedBody(ONE));
    }

    @Test
    public void reset_score_values_but_team_names_are_not_affected() throws Exception {
        performInitialisationRequestFor(RANKED_GAME);
        gameDataModel.getTeam(ONE).increaseWonSets();
        countGoalForTeam(ONE, ONE, ONE, ONE, ONE, ONE);

        builder = MockMvcRequestBuilders.post("/api/newRound");
        mockMvc.perform(builder);

        assertThat(getActualResponseBody()).isEqualTo(getExpectedBody(NO_TEAM));
    }

    //unnecessary?
    @Disabled
    @Test
    public void getAllTeams_whenAllTeamsWhereAsked_thenReturnTheseAsAList() throws Exception {

        builder.contentType(MediaType.APPLICATION_JSON_VALUE).content(json);
        mockMvc.perform(builder);

        builder = MockMvcRequestBuilders.get("/api/allTeams");
        mockMvc.perform(builder)
               .andExpect(status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name").value("T1"))
               .andExpect(MockMvcResultMatchers.jsonPath("$.[1].name").value("T2"));
    }

    private void prepareTeamsForInitialization(TeamDataModel teamOne, TeamDataModel teamTwo) {
        this.initDataModel = new InitDataModel();
        this.teams = new ArrayList<>();
        teams.add(teamOne);
        teams.add(teamTwo);

        initDataModel.setTeams(teams);
        json = gson.toJson(initDataModel);
    }

    private String getExpectedBody(Team matchWinner) throws IOException {
        GameOutputModel expectedValues = new GameOutputModel(gameDataModel);
        expectedValues.setMatchWinner(matchWinner);

        return mapper.writeValueAsString(expectedValues);
    }

    private void countGoalForExpectedTeam(Team... teams) {
        for (Team team : teams) {
            gameDataModel.getTeam(team).countGoal();
        }
    }

    private void performInitialisationRequestFor(int gameMode) throws Exception {
        builder = MockMvcRequestBuilders.post("/api/initialize/{gameModeId}", gameMode);
        builder.contentType(MediaType.APPLICATION_JSON_VALUE).content(json);

        mockMvc.perform(builder);
    }

    private String getActualResponseBody() throws Exception {
        builder = MockMvcRequestBuilders.get("/api/game");
        result = mockMvc.perform(builder).andExpect(status().isOk()).andReturn();

        return result.getResponse().getContentAsString();
    }

    private void countGoalForTeam(Team... teams) {
        for (Team team : teams) {
            game.countGoalFor(team);
        }
    }
}
