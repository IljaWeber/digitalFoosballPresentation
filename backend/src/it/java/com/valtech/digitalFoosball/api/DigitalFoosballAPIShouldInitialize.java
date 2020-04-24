package com.valtech.digitalFoosball.api;

import com.valtech.digitalFoosball.Application;
import com.valtech.digitalFoosball.constants.Team;
import com.valtech.digitalFoosball.model.output.GameOutputModel;
import com.valtech.digitalFoosball.model.output.TeamOutput;
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

import java.util.ArrayList;
import java.util.List;

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
        TeamOutput teamOne = new TeamOutput();
        TeamOutput teamTwo = new TeamOutput();
        GameOutputModel expectedValues = new GameOutputModel();
        List<TeamOutput> teams = new ArrayList<>();

        teamOne.setName("Orange");
        teamOne.setPlayerOne("Goalie");
        teamOne.setPlayerTwo("Striker");

        teamTwo.setName("Green");
        teamTwo.setPlayerOne("Goalie");
        teamTwo.setPlayerTwo("Striker");

        teams.add(teamOne);
        teams.add(teamTwo);

        expectedValues.setTeams(teams);
        expectedValues.setWinnerOfSet(Team.NO_TEAM);
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
