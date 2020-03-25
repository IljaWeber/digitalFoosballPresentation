package com.valtech.digitalFoosball.api;

import com.google.gson.Gson;
import com.valtech.digitalFoosball.Application;
import com.valtech.digitalFoosball.model.input.InitDataModel;
import com.valtech.digitalFoosball.model.internal.TeamDataModel;
import com.valtech.digitalFoosball.model.output.AdHocGameOutput;
import com.valtech.digitalFoosball.service.GameManager;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ContextConfiguration(classes = Application.class)
@SpringBootTest(classes = DigitalFoosballAPI.class)
public class DigitalFoosballAPITest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GameManager gameManager;
    private MockHttpServletRequestBuilder builder;

    private String json;
    private Gson gson;

    @BeforeEach
    public void setUp() {
        gson = new Gson();
        InitDataModel initDataModel = new InitDataModel();
        TeamDataModel teamDataModelOne = new TeamDataModel();
        TeamDataModel teamDataModelTwo = new TeamDataModel();
        List<TeamDataModel> teamDataModels = new ArrayList<>();// 45 - 60 in builder ..robert c martin

        teamDataModelOne.setName("T1");
        teamDataModelOne.setNameOfPlayerOne("P1");
        teamDataModelOne.setNameOfPlayerTwo("P2");

        teamDataModelTwo.setName("T2");
        teamDataModelTwo.setNameOfPlayerOne("P3");
        teamDataModelTwo.setNameOfPlayerTwo("P4");

        teamDataModels.add(teamDataModelOne);
        teamDataModels.add(teamDataModelTwo);

        initDataModel.setTeams(teamDataModels);
        builder = MockMvcRequestBuilders.post("/api/init");
        json = gson.toJson(initDataModel);
    }

    @Test
    public void init_whenPostMethodIsCalled_thenReturnHttpStatus200() throws Exception {
        builder.contentType(MediaType.APPLICATION_JSON_VALUE).content(json);

        mockMvc.perform(builder).andExpect(status().isOk());
    }

    @Test
    public void init_whenPostCallIsMadeWithJsonBody_thenReturnCurrentGameStatus() throws Exception {
        builder.contentType(MediaType.APPLICATION_JSON_VALUE).content(json);

        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.jsonPath("$.teams[0].name").value("T1"));
    }

    @Test
    public void undoLastGoal_whenSeveralGoalsWereShotAndTheLastGetsUndid_thenScoreIsSameAsWithoutTheLastGoal() throws Exception {
        builder.contentType(MediaType.APPLICATION_JSON_VALUE).content(json);
        mockMvc.perform(builder);
        gameManager.raiseScore(1);
        gameManager.raiseScore(1);
        builder = MockMvcRequestBuilders.put("/api/undo");

        mockMvc.perform(builder).andExpect(MockMvcResultMatchers.jsonPath("$.teams[0].score").value("1"));
    }

    @Test
    public void redoLastGoal_whenSeveralGoalsWereUndid_thenRedoThem() throws Exception {
        builder.contentType(MediaType.APPLICATION_JSON_VALUE).content(json);
        mockMvc.perform(builder);
        gameManager.raiseScore(1);
        gameManager.raiseScore(1);
        gameManager.raiseScore(2);
        gameManager.undoLastGoal();
        gameManager.undoLastGoal();
        gameManager.undoLastGoal();

        builder = MockMvcRequestBuilders.put("/api/redo");
        mockMvc.perform(builder);
        mockMvc.perform(builder);

        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.jsonPath("$.teams[0].score").value("2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.teams[1].score").value("1"));
    }

    @Test
    public void resetGameValues_whenResetGameValuesIsCalled_thenSetEmptyTeamNamesAndScoreToZero() throws Exception {
        builder.contentType(MediaType.APPLICATION_JSON_VALUE).content(json);
        mockMvc.perform(builder);
        gameManager.raiseScore(1);
        gameManager.raiseScore(2);

        builder = MockMvcRequestBuilders.delete("/api/reset");

        MvcResult result = mockMvc.perform(builder).andReturn();
        String actual = result.getResponse().getContentAsString();
        assertThat(actual).isEqualTo("true");
    }

    @Test
    public void raiseScore_whenScoreForTeamOneIsRaised_thenScoreOfTeamOneIsOne() throws Exception {
        builder.contentType(MediaType.APPLICATION_JSON_VALUE).content(json);
        mockMvc.perform(builder);
        builder = MockMvcRequestBuilders.post("/api/raise");
        builder.contentType(MediaType.APPLICATION_JSON_VALUE).content(gson.toJson(1));

        mockMvc.perform(builder).andReturn();

        builder = MockMvcRequestBuilders.get("/api/game");
        mockMvc.perform(builder).andExpect(MockMvcResultMatchers.jsonPath("$.teams[0].score").value("1"));
    }

    @Test
    public void raiseScore_whenScoreForTeamTwoIsRaised_thenScoreOfTeamTwoIsOne() throws Exception {
        builder.contentType(MediaType.APPLICATION_JSON_VALUE).content(json);
        mockMvc.perform(builder);
        builder = MockMvcRequestBuilders.post("/api/raise");
        builder.contentType(MediaType.APPLICATION_JSON_VALUE).content(gson.toJson(2));

        mockMvc.perform(builder);

        builder = MockMvcRequestBuilders.get("/api/game");
        mockMvc.perform(builder).andExpect(MockMvcResultMatchers.jsonPath("$.teams[1].score").value("1"));
    }

    @Test
    public void getGameData_whenRoundWinConditionIsFulfilled_thenReturnTheCorrespondingGameDataModel() throws Exception {
        builder.contentType(MediaType.APPLICATION_JSON_VALUE).content(json);
        mockMvc.perform(builder);
        builder = MockMvcRequestBuilders.post("/api/raise");
        builder.contentType(MediaType.APPLICATION_JSON_VALUE).content(gson.toJson(2));

        performBuilderGivenTimes(6);

        builder = MockMvcRequestBuilders.get("/api/game");
        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.jsonPath("$.roundWinner").value("2"));
    }

    private void performBuilderGivenTimes(int times) throws Exception {
        for (int i = 0; i < times; i++) {
            mockMvc.perform(builder);
        }
    }

    @Test
    public void getGameData_whenGameWinConditionIsFulfilled_thenReturnTheCorrespondingGameDataModel() throws Exception {
        builder.contentType(MediaType.APPLICATION_JSON_VALUE).content(json);
        mockMvc.perform(builder);
        builder = MockMvcRequestBuilders.post("/api/raise");
        builder.contentType(MediaType.APPLICATION_JSON_VALUE).content(gson.toJson(1));
        performBuilderGivenTimes(6);
        builder = MockMvcRequestBuilders.post("/api/newRound");
        mockMvc.perform(builder);
        builder = MockMvcRequestBuilders.post("/api/raise");
        builder.contentType(MediaType.APPLICATION_JSON_VALUE).content(gson.toJson(1));
        performBuilderGivenTimes(6);

        builder = MockMvcRequestBuilders.get("/api/game");
        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.jsonPath("$.matchWinner").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.teams[0].roundWins").value("2"));
    }

    @Test
    public void newRound_whenNewRoundIsStarted_thenNamesAndPlayersAreSameButScoresAreZero() throws Exception {
        builder.contentType(MediaType.APPLICATION_JSON_VALUE).content(json);
        mockMvc.perform(builder);
        builder = MockMvcRequestBuilders.post("/api/raise");
        builder.contentType(MediaType.APPLICATION_JSON_VALUE).content(gson.toJson(1));
        mockMvc.perform(builder);

        builder = MockMvcRequestBuilders.post("/api/newRound");
        mockMvc.perform(builder)
                .andExpect(MockMvcResultMatchers.jsonPath("$.teams[0].score").value("0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.teams[1].score").value("0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.teams[0].name").value("T1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.teams[1].name").value("T2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.teams[0].playerOne").value("P1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.teams[0].playerTwo").value("P2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.teams[1].playerOne").value("P3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.teams[1].playerTwo").value("P4"));
    }

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


    //---------------------- Ad Hoc Initialization -------------------------------


    @Test
    void initAdHocMatch_whenAnAdHocGameIsCalled_thenReturnGenericTeamModels() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        AdHocGameOutput adHocTeamOne = new AdHocGameOutput();
        AdHocGameOutput adHocTeamTwo = new AdHocGameOutput();
        List<AdHocGameOutput> expectedValues = new ArrayList<>();
        adHocTeamOne.setName("Orange");
        adHocTeamOne.setScore(0);
        adHocTeamOne.setWonRounds(0);
        adHocTeamTwo.setName("Green");
        adHocTeamTwo.setScore(0);
        adHocTeamTwo.setWonRounds(0);
        expectedValues.add(adHocTeamOne);
        expectedValues.add(adHocTeamTwo);

        MvcResult result =
                mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/initAdHocMatch").contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andReturn();

        String actualResponseBody = result.getResponse().getContentAsString();
        String expectedResponseBody = mapper.writeValueAsString(expectedValues);
        assertThat(actualResponseBody).isEqualTo(expectedResponseBody);
    }

    @Test
    void raiseScore_whenAnAdHocTeamScoresAGoal_thenReturnNewGameValues() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        AdHocGameOutput adHocTeamOne = new AdHocGameOutput();
        AdHocGameOutput adHocTeamTwo = new AdHocGameOutput();
        List<AdHocGameOutput> expectedValues = new ArrayList<>();
        adHocTeamOne.setName("Orange");
        adHocTeamOne.setScore(1);
        adHocTeamOne.setWonRounds(0);
        adHocTeamTwo.setName("Green");
        adHocTeamTwo.setScore(0);
        adHocTeamTwo.setWonRounds(0);
        expectedValues.add(adHocTeamOne);
        expectedValues.add(adHocTeamTwo);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/initAdHocMatch").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/raise").contentType(MediaType.APPLICATION_JSON).content(gson.toJson(1)))
                .andExpect(status().isOk());

        MvcResult result =
                mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/gameDataOfAdHocGame").contentType(MediaType.APPLICATION_JSON).content(gson.toJson(1)))
                        .andExpect(status().isOk())
                        .andReturn();

        String actualResponseBody = result.getResponse().getContentAsString();
        String expectedResponseBody = mapper.writeValueAsString(expectedValues);
        assertThat(actualResponseBody).isEqualTo(expectedResponseBody);


    }
}
