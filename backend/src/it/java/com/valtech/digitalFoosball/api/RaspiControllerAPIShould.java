package com.valtech.digitalFoosball.api;

import com.google.gson.Gson;
import com.valtech.digitalFoosball.Application;
import com.valtech.digitalFoosball.api.driver.usercommands.DigitalFoosballRestAPI;
import com.valtech.digitalFoosball.domain.GameController;
import com.valtech.digitalFoosball.domain.gameModes.models.BaseOutputModel;
import com.valtech.digitalFoosball.domain.gameModes.models.TeamOutputModel;
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

import static com.valtech.digitalFoosball.domain.constants.Team.ONE;
import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureMockMvc
@ContextConfiguration(classes = Application.class)
@SpringBootTest(classes = DigitalFoosballRestAPI.class)
public class RaspiControllerAPIShould {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GameController game;

    private MockHttpServletRequestBuilder builder;

    private Gson gson;

    @BeforeEach
    public void setUp() throws Exception {
        gson = new Gson();

        builder = MockMvcRequestBuilders.post("/api/init/adhoc");
        mockMvc.perform(builder);
    }

    @Test
    void raise_score() throws Exception {
        builder = MockMvcRequestBuilders.post("/raspi/raise");
        String json = gson.toJson("1");
        builder.contentType(MediaType.APPLICATION_JSON_VALUE).content(json);

        mockMvc.perform(builder);
        BaseOutputModel gameData = game.getGameData();
        TeamOutputModel team = gameData.getTeam(ONE);
        int actual = team.getScore();
        assertThat(actual).isEqualTo(1);
    }
}
