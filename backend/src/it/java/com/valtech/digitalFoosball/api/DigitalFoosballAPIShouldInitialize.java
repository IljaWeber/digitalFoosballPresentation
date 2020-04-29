package com.valtech.digitalFoosball.api;

import com.google.gson.Gson;
import com.valtech.digitalFoosball.Application;
import com.valtech.digitalFoosball.constants.Team;
import com.valtech.digitalFoosball.model.GameDataModel;
import com.valtech.digitalFoosball.model.input.InitDataModel;
import com.valtech.digitalFoosball.model.internal.TeamDataModel;
import com.valtech.digitalFoosball.model.output.GameOutputModel;
import org.codehaus.jackson.map.ObjectMapper;
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

import static com.valtech.digitalFoosball.constants.Team.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ContextConfiguration(classes = Application.class)
@SpringBootTest(classes = DigitalFoosballAPI.class)
public class DigitalFoosballAPIShouldInitialize {

    @Autowired
    private MockMvc mockMvc;

    private Gson gson;
    private String json;
    private MvcResult result;
    private ObjectMapper mapper;
    private List<TeamDataModel> teams;
    private GameDataModel gameDataModel;
    private InitDataModel initDataModel;
    private String expectedResponseBody;
    private GameOutputModel expectedValues;
    private MockHttpServletRequestBuilder builder;

    public DigitalFoosballAPIShouldInitialize() {
        gson = new Gson();
        teams = new ArrayList<>();
        mapper = new ObjectMapper();
        gameDataModel = new GameDataModel();
        initDataModel = new InitDataModel();
    }

    @Test
    void an_ad_hoc_match_with_default_values_for_the_teams() throws Exception {
        prepareTeamsForInitialization(new TeamDataModel("Orange", "Goalie", "Striker"),
                                      new TeamDataModel("Green", "Goalie", "Striker"));

        result = performRequestForGameModeInitialisation(0);

        String actualResponseBody = result.getResponse().getContentAsString();
        assertThat(actualResponseBody).isEqualTo(expectedResponseBody);
    }

    @Test
    void a_ranked_game_with_individual_team_and_player_names() throws Exception {
        prepareTeamsForInitialization(new TeamDataModel("T1", "P1", "P2"), new TeamDataModel("T2", "P3", "P4"));

        result = performRequestForGameModeInitialisation(1);

        String actualResponseBody = result.getResponse().getContentAsString();
        assertThat(actualResponseBody).isEqualTo(expectedResponseBody);
    }

    private MvcResult performRequestForGameModeInitialisation(int gameMode) throws Exception {

        builder = MockMvcRequestBuilders.post("/api/initialize/{gameModeId}", gameMode);
        builder.contentType(MediaType.APPLICATION_JSON_VALUE).content(json);

        return mockMvc.perform(builder)
                      .andExpect(status().isOk())
                      .andReturn();
    }

    private void prepareTeamsForInitialization(TeamDataModel teamOne, TeamDataModel teamTwo) throws IOException {
        this.initDataModel = new InitDataModel();
        this.teams = new ArrayList<>();
        teams.add(teamOne);
        teams.add(teamTwo);

        initDataModel.setTeams(teams);
        json = gson.toJson(initDataModel);

        prepareExpectedResponse(teamOne, teamTwo);
    }

    private void prepareExpectedResponse(TeamDataModel teamOne, TeamDataModel teamTwo) throws IOException {
        this.gameDataModel.setTeam(ONE, teamOne);
        this.gameDataModel.setTeam(TWO, teamTwo);
        this.gameDataModel.setSetWinner(NO_TEAM);

        expectedValues = new GameOutputModel(this.gameDataModel);
        expectedValues.setMatchWinner(Team.NO_TEAM);

        expectedResponseBody = mapper.writeValueAsString(expectedValues);
    }

}
