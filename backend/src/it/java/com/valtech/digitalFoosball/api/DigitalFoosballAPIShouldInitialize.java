package com.valtech.digitalFoosball.api;

import com.valtech.digitalFoosball.Application;
import com.valtech.digitalFoosball.constants.Team;
import com.valtech.digitalFoosball.model.GameDataModel;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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

    @BeforeEach
    void setUp() throws Exception {
        mapper = new ObjectMapper();
        GameDataModel gameDataModel = new GameDataModel();
        TeamDataModel teamOne = new TeamDataModel("Orange", "Goalie", "Striker");
        TeamDataModel teamTwo = new TeamDataModel("Green", "Goalie", "Striker");
        gameDataModel.setTeam(ONE, teamOne);
        gameDataModel.setTeam(TWO, teamTwo);
        gameDataModel.setSetWinner(NO_TEAM);
        GameOutputModel expectedValues = new GameOutputModel(gameDataModel);
        expectedValues.setMatchWinner(Team.NO_TEAM);

        expectedResponseBody = mapper.writeValueAsString(expectedValues);

        result = mockMvc.perform(
                MockMvcRequestBuilders.post("/api/initAdHoc").contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andReturn();
    }

    @Test
    void an_ad_hoc_match_with_default_values_for_the_teams() throws Exception {

        String actualResponseBody = result.getResponse().getContentAsString();
        assertThat(actualResponseBody).isEqualTo(expectedResponseBody);
    }
}
