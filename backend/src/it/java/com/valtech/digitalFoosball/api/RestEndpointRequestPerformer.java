package com.valtech.digitalFoosball.api;

import com.google.gson.Gson;
import com.valtech.digitalFoosball.domain.common.constants.GameMode;
import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import com.valtech.digitalFoosball.domain.ranked.TeamDataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class RestEndpointRequestPerformer {
    private final Gson gson;
    private String json;

    @Autowired
    private MockMvc mockMvc;

    private MockHttpServletRequestBuilder builder;

    RestEndpointRequestPerformer() {
        gson = new Gson();
    }

    public void countGoalForTeam(Team... teams) throws Exception {
        builder = MockMvcRequestBuilders.post("/api/raise");
        for (Team team : teams) {
            int hardwareValueOfTeam = team.hardwareValue();
            builder.contentType(MediaType.APPLICATION_JSON_VALUE).content(String.valueOf(hardwareValueOfTeam));
            mockMvc.perform(builder);
        }
    }

    public void initializeGame(GameMode gameMode) throws Exception {
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

    public void startANewRound() throws Exception {
        builder = MockMvcRequestBuilders.post("/api/newRound");
        mockMvc.perform(builder);
    }

    public void resetValues() throws Exception {
        builder = MockMvcRequestBuilders.delete("/api/reset");
        mockMvc.perform(builder);
    }

    public void undoLastGoal() throws Exception {
        builder = MockMvcRequestBuilders.put("/api/undo");
        mockMvc.perform(builder);
    }

    public void redoLastUndoneGoal() throws Exception {
        builder = MockMvcRequestBuilders.put("/api/redo");
        mockMvc.perform(builder);

    }

    public String getGameValues() throws Exception {
        builder = MockMvcRequestBuilders.get("/data/game");
        MvcResult result = mockMvc.perform(builder).andExpect(status().isOk()).andReturn();

        return result.getResponse().getContentAsString();
    }

    public void prepareTeamsForInitialization(TeamDataModel teamOne, TeamDataModel teamTwo) {
        InitDataModel initDataModel = new InitDataModel();
        List<TeamDataModel> teams = new ArrayList<>();

        teams.add(teamOne);
        teams.add(teamTwo);

        initDataModel.setTeams(teams);

        json = gson.toJson(initDataModel);
    }
}
