package com.valtech.digitalFoosball.api;

import com.google.gson.Gson;
import com.valtech.digitalFoosball.Application;
import com.valtech.digitalFoosball.api.driver.usercommands.DigitalFoosballUserCommandAPI;
import com.valtech.digitalFoosball.domain.common.GameController;
import com.valtech.digitalFoosball.domain.common.constants.GameMode;
import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import com.valtech.digitalFoosball.domain.common.models.output.team.RegularTeamOutputModel;
import com.valtech.digitalFoosball.domain.common.models.output.team.TeamOutputModel;
import com.valtech.digitalFoosball.domain.ranked.RankedGameDataModel;
import com.valtech.digitalFoosball.domain.ranked.RankedTeamDataModel;
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

import static com.valtech.digitalFoosball.domain.common.constants.GameMode.AD_HOC;
import static com.valtech.digitalFoosball.domain.common.constants.GameMode.RANKED;
import static com.valtech.digitalFoosball.domain.common.constants.Team.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ContextConfiguration(classes = Application.class)
@SpringBootTest(classes = DigitalFoosballUserCommandAPI.class)
public class DigitalFoosballRestApiShould {

    private Gson gson;
    private ObjectMapper mapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private GameController game;
    private String json;
    private RankedGameDataModel gameDataModel;
    private InitDataModel initDataModel;
    private MockHttpServletRequestBuilder builder;
    private CompareRankedGameOutputModel comparable;

    @BeforeEach
    void setUp() {
        gson = new Gson();
        mapper = new ObjectMapper();
        initDataModel = new InitDataModel();
        gameDataModel = new RankedGameDataModel();
        comparable = new CompareRankedGameOutputModel();
        List<RankedTeamDataModel> teams = new ArrayList<>();
        RankedTeamDataModel teamOne = new RankedTeamDataModel("T1", "P1", "P2");
        RankedTeamDataModel teamTwo = new RankedTeamDataModel("T2", "P3", "P4");

        teams.add(teamOne);
        teams.add(teamTwo);
        gameDataModel.setTeams(teams);
        prepareTeamsForInitialization(teamOne, teamTwo);

        comparable.prepareCompareTeamOneWithValues("T1", "P1", "P2");
        comparable.prepareCompareTeamTwoWithValues("T2", "P3", "P4");
    }

    @Test
    void initialise_an_ad_hoc_match_with_default_values_for_the_teams() throws Exception {
        prepareTeamsForInitialization(new RankedTeamDataModel("Orange", "Goalie", "Striker"),
                                      new RankedTeamDataModel("Green", "Goalie", "Striker"));
        prepareComparableAdHocInitialisation();

        comparable = new CompareRankedGameOutputModel();
        comparable.prepareCompareTeamOneWithValues("Orange", "Goalie", "Striker");
        comparable.prepareCompareTeamTwoWithValues("Green", "Goalie", "Striker");
        String expected = mapper.writeValueAsString(comparable);

        prepareGameWithMode(AD_HOC);

        String actual = getGameStatus();
        assertThat(actual).isEqualTo(expected);
    }

    private void prepareComparableAdHocInitialisation() {
        List<RankedTeamDataModel> teams = new ArrayList<>();
        teams.add(new RankedTeamDataModel("Orange", "Goalie", "Striker"));
        teams.add(new RankedTeamDataModel("Green", "Goalie", "Striker"));
        gameDataModel.setTeams(teams);
    }

    @Test
    void initialise_a_ranked_game_with_individual_team_and_player_names() throws Exception {
        String expected = mapper.writeValueAsString(comparable);

        prepareGameWithMode(RANKED);

        String actual = getGameStatus();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void undo_scored_goal() throws Exception {
        comparable.prepareScoreOfTeamOne(1);
        String expected = mapper.writeValueAsString(comparable);
        MockHttpServletRequestBuilder undo = MockMvcRequestBuilders.put("/api/undo");
        prepareGameWithMode(RANKED);
        countGoalForTeam(ONE, ONE);

        prepareGameCommands(undo);

        String actual = getGameStatus();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void redo_undone_goals() throws Exception {
        comparable.prepareScoreOfTeamOne(1);
        comparable.prepareScoreOfTeamTwo(2);
        String expected = mapper.writeValueAsString(comparable);

        MockHttpServletRequestBuilder redo = MockMvcRequestBuilders.put("/api/redo");
        prepareGameWithMode(RANKED);
        countGoalForTeam(ONE, TWO, TWO);
        game.undoGoal();
        game.undoGoal();

        prepareGameCommands(redo, redo);

        String actual = getGameStatus();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void reset_game_with_empty_team_and_player_names_and_zero_scores() throws Exception {
        comparable = new CompareRankedGameOutputModel();
        String expected = mapper.writeValueAsString(comparable);
        MockHttpServletRequestBuilder reset = MockMvcRequestBuilders.delete("/api/reset");
        prepareGameWithMode(RANKED);
        countGoalForTeam(ONE, TWO);

        prepareGameCommands(reset);

        String actual = getGameStatus();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void return_a_won_set() throws Exception {
        comparable.prepareScoreOfTeamOne(6);
        comparable.setWinnerOfSet(ONE);
        String expected = mapper.writeValueAsString(comparable);

        prepareGameWithMode(RANKED);
        countGoalForTeam(ONE, ONE, ONE, ONE, ONE, ONE);

        String actual = getGameStatus();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void return_a_match_winner() throws Exception {
        comparable.prepareScoreOfTeamOne(6);
        comparable.prepareScoreOfTeamTwo(0);

        comparable.setMatchWinner(ONE);
        comparable.setWinnerOfSet(ONE);
        String expected = mapper.writeValueAsString(comparable);

        prepareGameWithMode(RANKED);
        countGoalForTeam(ONE, ONE, ONE, ONE, ONE, ONE);
        game.changeover();
        countGoalForTeam(ONE, ONE, ONE, ONE, ONE, ONE);

        String actual = getGameStatus();

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void reset_score_values_but_team_names_are_not_affected() throws Exception {
        String expected = mapper.writeValueAsString(comparable);

        MockHttpServletRequestBuilder newRound = MockMvcRequestBuilders.post("/api/newRound");
        prepareGameWithMode(RANKED);
        countGoalForTeam(ONE, ONE, ONE, ONE, ONE, ONE);

        prepareGameCommands(newRound);

        String actual = getGameStatus();
        assertThat(actual).isEqualTo(expected);
    }

    private void prepareGameCommands(MockHttpServletRequestBuilder... gameCommands) throws Exception {
        for (MockHttpServletRequestBuilder gameCommand : gameCommands) {
            mockMvc.perform(gameCommand);
        }
    }

    private void prepareTeamsForInitialization(RankedTeamDataModel teamOne, RankedTeamDataModel teamTwo) {
        this.initDataModel = new InitDataModel();
        List<RankedTeamDataModel> teams = new ArrayList<>();
        teams.add(teamOne);
        teams.add(teamTwo);

        initDataModel.setTeams(teams);
        json = gson.toJson(initDataModel);
    }

    private void prepareGameWithMode(GameMode gameMode) throws Exception {
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

    private String getGameStatus() throws Exception {
        builder = MockMvcRequestBuilders.get("/data/game");
        MvcResult result = mockMvc.perform(builder).andExpect(status().isOk()).andReturn();

        return result.getResponse().getContentAsString();
    }

    private void countGoalForTeam(Team... teams) throws Exception {
        builder = MockMvcRequestBuilders.post("/api/raise");
        for (Team team : teams) {
            int hardwareValueOfTeam = team.hardwareValue();
            builder.contentType(MediaType.APPLICATION_JSON_VALUE).content(String.valueOf(hardwareValueOfTeam));
            mockMvc.perform(builder);
        }
    }

    private static class CompareRankedGameOutputModel {
        List<TeamOutputModel> teams;
        Team matchWinner;
        Team winnerOfSet;
        private TeamOutputModel teamTwo;
        private TeamOutputModel teamOne;

        private CompareRankedGameOutputModel() {
            teams = new ArrayList<>();
            matchWinner = NO_TEAM;
            winnerOfSet = NO_TEAM;
        }

        private void prepareCompareTeamOneWithValues(String name, String nameOfPlayerOne, String nameOfPlayerTwo) {
            teamOne = new RegularTeamOutputModel();
            teamOne.setName(name);
            teamOne.setPlayerOne(nameOfPlayerOne);
            teamOne.setPlayerTwo(nameOfPlayerTwo);
            teams.add(teamOne);
        }

        private void prepareCompareTeamTwoWithValues(String name, String nameOfPlayerOne, String nameOfPlayerTwo) {
            teamTwo = new RegularTeamOutputModel();
            teamTwo.setName(name);
            teamTwo.setPlayerOne(nameOfPlayerOne);
            teamTwo.setPlayerTwo(nameOfPlayerTwo);
            teams.add(teamTwo);
        }

        private void prepareScoreOfTeamOne(int score) {
            teamOne.setScore(score);
        }

        private void prepareScoreOfTeamTwo(int score) {
            teamTwo.setScore(score);
        }

        private void setMatchWinner(Team matchWinner) {
            this.matchWinner = matchWinner;
        }

        private void setWinnerOfSet(Team winnerOfSet) {
            this.winnerOfSet = winnerOfSet;
        }

        public List<TeamOutputModel> getTeams() {
            return teams;
        }

        public Team getMatchWinner() {
            return matchWinner;
        }

        public Team getWinnerOfSet() {
            return winnerOfSet;
        }
    }
}
