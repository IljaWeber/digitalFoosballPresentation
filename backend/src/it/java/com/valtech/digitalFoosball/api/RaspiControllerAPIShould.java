package com.valtech.digitalFoosball.api;

import com.google.gson.Gson;
import com.valtech.digitalFoosball.Application;
import com.valtech.digitalFoosball.model.input.InitDataModel;
import com.valtech.digitalFoosball.model.internal.TeamDataModel;
import com.valtech.digitalFoosball.model.output.GameOutputModel;
import com.valtech.digitalFoosball.model.output.TeamOutput;
import com.valtech.digitalFoosball.service.game.GameController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.valtech.digitalFoosball.constants.Team.ONE;
import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureMockMvc
@ContextConfiguration(classes = Application.class)
@SpringBootTest(classes = DigitalFoosballAPI.class)
public class RaspiControllerAPIShould {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GameController game;

    private MockHttpServletRequestBuilder builder;

    private String json;
    private Gson gson;

    @BeforeEach
    public void setUp() throws Exception {
        gson = new Gson();
        TeamDataModel teamDataModelOne = new TeamDataModel("T1", "P1", "P2");
        TeamDataModel teamDataModelTwo = new TeamDataModel("T2", "P3", "P4");
        InitDataModel initDataModel = new InitDataModel(teamDataModelOne, teamDataModelTwo);

        builder = MockMvcRequestBuilders.post("/api/initialize/{gameModeId}", 0);
        json = gson.toJson(initDataModel);
        builder.contentType(MediaType.APPLICATION_JSON_VALUE).content(json);

        mockMvc.perform(builder);
    }

    @Test
    void raise_score() throws Exception {
        builder = MockMvcRequestBuilders.post("/raspi/raise");
        json = gson.toJson("1");
        builder.contentType(MediaType.APPLICATION_JSON_VALUE).content(json);

        mockMvc.perform(builder);
        GameOutputModel gameData = game.getGameData();
        TeamOutput team = gameData.getTeam(ONE);
        int actual = team.getScore();
        assertThat(actual).isEqualTo(1);
    }
}
