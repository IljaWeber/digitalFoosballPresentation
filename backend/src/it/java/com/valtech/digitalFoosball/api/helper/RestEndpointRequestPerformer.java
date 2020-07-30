package com.valtech.digitalFoosball.api.helper;

import com.google.gson.Gson;
import com.valtech.digitalFoosball.domain.common.constants.GameMode;
import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import com.valtech.digitalFoosball.domain.common.models.TeamDataModel;
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
    private String json = "";

    @Autowired
    private MockMvc mockMvc;

    private MockHttpServletRequestBuilder builder;

    RestEndpointRequestPerformer() {
        gson = new Gson();
    }

    public void countGoalForTeam(Team... teams) throws Exception {
        builder = MockMvcRequestBuilders.post("/raspi/raise");
        for (Team team : teams) {
            int hardwareValueOfTeam = team.hardwareValue();
            builder.contentType(MediaType.APPLICATION_JSON_VALUE).content(String.valueOf(hardwareValueOfTeam));
            mockMvc.perform(builder);
        }
    }

    public void initializeGame(GameMode gameMode) throws Exception {
        String mode = gameMode.toString();

        builder = MockMvcRequestBuilders.post(mode + "/init");
        builder.contentType(MediaType.APPLICATION_JSON_VALUE).content(json);

        mockMvc.perform(builder);
    }

    public void startANewRound(GameMode gameMode) throws Exception {
        String mode = gameMode.toString();

        builder = MockMvcRequestBuilders.post(mode + "/newRound");
        mockMvc.perform(builder);
    }

    public void resetValues(GameMode gameMode) throws Exception {
        String mode = gameMode.toString();

        builder = MockMvcRequestBuilders.delete(mode + "/reset");
        mockMvc.perform(builder);
    }

    public void undoLastGoal(GameMode gameMode) throws Exception {
        String mode = gameMode.toString();

        builder = MockMvcRequestBuilders.put(mode + "/undo");
        mockMvc.perform(builder);
    }

    public void redoLastUndoneGoal(GameMode gameMode) throws Exception {
        String mode = gameMode.toString();

        builder = MockMvcRequestBuilders.put(mode + "/redo");
        mockMvc.perform(builder);

    }

    public String getGameValues(GameMode gameMode) throws Exception {
        String mode = gameMode.toString();

        builder = MockMvcRequestBuilders.get(mode + "/game");
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