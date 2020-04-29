package com.valtech.digitalFoosball.api;

import com.google.gson.Gson;
import com.valtech.digitalFoosball.Application;
import com.valtech.digitalFoosball.constants.Team;
import com.valtech.digitalFoosball.model.GameDataModel;
import com.valtech.digitalFoosball.model.input.InitDataModel;
import com.valtech.digitalFoosball.model.internal.TeamDataModel;
import com.valtech.digitalFoosball.model.output.GameOutputModel;
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

    private ObjectMapper mapper;
    private String expectedResponseBody;
    private MvcResult result;
    private MockHttpServletRequestBuilder builder;
    private Gson gson;
    private InitDataModel initDataModel;

    @BeforeEach
    void setUp() throws Exception {
        gson = new Gson();
        mapper = new ObjectMapper();
        GameDataModel gameDataModel = new GameDataModel();
        TeamDataModel teamOne = new TeamDataModel("Orange", "Goalie", "Striker");
        TeamDataModel teamTwo = new TeamDataModel("Green", "Goalie", "Striker");
        List<TeamDataModel> teams = new ArrayList<>();
        teams.add(teamOne);
        teams.add(teamTwo);

        initDataModel = new InitDataModel();
        initDataModel.setTeams(teams);

        gameDataModel.setTeam(ONE, teamOne);
        gameDataModel.setTeam(TWO, teamTwo);
        gameDataModel.setSetWinner(NO_TEAM);
        GameOutputModel expectedValues = new GameOutputModel(gameDataModel);
        expectedValues.setMatchWinner(Team.NO_TEAM);

        expectedResponseBody = mapper.writeValueAsString(expectedValues);

    }

    @Test
    void an_ad_hoc_match_with_default_values_for_the_teams() throws Exception {
        String json = gson.toJson(initDataModel);
        builder = MockMvcRequestBuilders.post("/api/initialize/{gameModeId}", 0);
        builder.contentType(MediaType.APPLICATION_JSON_VALUE).content(json);

        result = mockMvc.perform(builder)
                        .andExpect(status().isOk())
                        .andReturn();

        String actualResponseBody = result.getResponse().getContentAsString();
        assertThat(actualResponseBody).isEqualTo(expectedResponseBody);
    }
}
