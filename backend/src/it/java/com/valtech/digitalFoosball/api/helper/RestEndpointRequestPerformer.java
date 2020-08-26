package com.valtech.digitalFoosball.api.helper;

import com.google.gson.Gson;
import com.jayway.jsonpath.JsonPath;
import com.valtech.digitalFoosball.domain.RaiseScoreIdentifier;
import com.valtech.digitalFoosball.domain.common.constants.GameMode;
import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import com.valtech.digitalFoosball.domain.common.models.TeamDataModel;
import com.valtech.digitalFoosball.domain.common.session.SessionIdentifier;
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

    public String registerRaspberryPi() throws Exception {
        builder = MockMvcRequestBuilders.post("/raspi/register");
        String response = mockMvc.perform(builder).andReturn().getResponse().getContentAsString();

        String result = JsonPath.read(response, "$.identifier");

        return result;
    }

    public void countGoalForTeam(SessionIdentifier identifier, Team... teams) throws Exception {
        RaiseScoreIdentifier scoreIdentifier = new RaiseScoreIdentifier();
        scoreIdentifier.setIdentifier(identifier);
        builder = MockMvcRequestBuilders.post("/raspi/raise");

        for (Team team : teams) {
            int hardwareValueOfTeam = team.hardwareValue();
            scoreIdentifier.setTeamNo(hardwareValueOfTeam);
            json = gson.toJson(scoreIdentifier);
            builder.contentType(MediaType.APPLICATION_JSON_VALUE).content(json);
            mockMvc.perform(builder);
        }
    }

    public void initializeGame(GameMode gameMode) throws Exception {
        String mode = gameMode.toString();

        builder = MockMvcRequestBuilders.post(mode + "/init");
        builder.contentType(MediaType.APPLICATION_JSON_VALUE).content(json);

        mockMvc.perform(builder);
    }

    public void startANewRound(GameMode gameMode, SessionIdentifier identifier) throws Exception {
        json = gson.toJson(identifier);
        String mode = gameMode.toString();

        builder = MockMvcRequestBuilders.post(mode + "/newRound");
        builder.contentType(MediaType.APPLICATION_JSON_VALUE).content(json);
        mockMvc.perform(builder);
    }

    public void resetValues(GameMode gameMode, SessionIdentifier identifier) throws Exception {
        json = gson.toJson(identifier);
        String mode = gameMode.toString();

        builder = MockMvcRequestBuilders.delete(mode + "/reset");
        builder.contentType(MediaType.APPLICATION_JSON_VALUE).content(json);
        mockMvc.perform(builder);
    }

    public void undoLastGoal(GameMode gameMode, SessionIdentifier identifier) throws Exception {
        json = gson.toJson(identifier);
        String mode = gameMode.toString();

        builder = MockMvcRequestBuilders.put(mode + "/undo");
        builder.contentType(MediaType.APPLICATION_JSON_VALUE).content(json);
        mockMvc.perform(builder);
    }

    public void redoLastUndoneGoal(GameMode gameMode, SessionIdentifier identifier) throws Exception {
        json = gson.toJson(identifier);
        String mode = gameMode.toString();

        builder = MockMvcRequestBuilders.put(mode + "/redo");
        builder.contentType(MediaType.APPLICATION_JSON_VALUE).content(json);
        mockMvc.perform(builder);

    }

    public String getGameValues(GameMode gameMode, SessionIdentifier identifier) throws Exception {
        json = gson.toJson(identifier);
        String mode = gameMode.toString();

        builder = MockMvcRequestBuilders.get(mode + "/game");
        builder.contentType(MediaType.APPLICATION_JSON_VALUE).content(json);
        MvcResult result = mockMvc.perform(builder).andExpect(status().isOk()).andReturn();

        return result.getResponse().getContentAsString();
    }

    public void prepareTeamsForInitialization(TeamDataModel teamOne,
                                              TeamDataModel teamTwo,
                                              SessionIdentifier identifier) {
        InitDataModel initDataModel = new InitDataModel();
        List<TeamDataModel> teams = new ArrayList<>();

        teams.add(teamOne);
        teams.add(teamTwo);

        initDataModel.setTeams(teams);
        initDataModel.setIdentifier(identifier);

        json = gson.toJson(initDataModel);
    }

    public void prepareAdHocInitialization(SessionIdentifier identifier) {
        json = gson.toJson(identifier);
    }
}
