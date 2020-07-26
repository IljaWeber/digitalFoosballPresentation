package com.valtech.digitalFoosball.api;

import com.valtech.digitalFoosball.Application;
import com.valtech.digitalFoosball.api.driver.usercommands.DigitalFoosballUserCommandAPI;
import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.models.output.team.RegularTeamOutputModel;
import com.valtech.digitalFoosball.domain.common.models.output.team.TeamOutputModel;
import com.valtech.digitalFoosball.domain.ranked.RankedGameDataModel;
import com.valtech.digitalFoosball.domain.ranked.RankedTeamDataModel;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;

import static com.valtech.digitalFoosball.domain.common.constants.GameMode.AD_HOC;
import static com.valtech.digitalFoosball.domain.common.constants.GameMode.RANKED;
import static com.valtech.digitalFoosball.domain.common.constants.Team.*;
import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = Application.class)
@SpringBootTest(classes = DigitalFoosballUserCommandAPI.class)
@SpringBootConfiguration
@Import(com.valtech.digitalFoosball.api.RestEndpointRequestPerformer.class)
public class DigitalFoosballRestApiShould {
    private ObjectMapper mapper;
    private CompareRankedGameOutputModel comparableOutput;

    @Autowired
    private RestEndpointRequestPerformer endpointRequestPerformer;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
        List<RankedTeamDataModel> teams = new ArrayList<>();
        comparableOutput = new CompareRankedGameOutputModel();
        RankedGameDataModel gameDataModel = new RankedGameDataModel();
        RankedTeamDataModel teamOne = new RankedTeamDataModel("T1", "P1", "P2");
        RankedTeamDataModel teamTwo = new RankedTeamDataModel("T2", "P3", "P4");

        teams.add(teamOne);
        teams.add(teamTwo);
        gameDataModel.setTeams(teams);
        endpointRequestPerformer.prepareTeamsForInitialization(teamOne, teamTwo);

        comparableOutput.prepareCompareTeamOneWithValues("T1", "P1", "P2");
        comparableOutput.prepareCompareTeamTwoWithValues("T2", "P3", "P4");
    }

    @Test
    void initialise_an_ad_hoc_match_with_default_values_for_the_teams() throws Exception {
        endpointRequestPerformer
                .prepareTeamsForInitialization(
                        new RankedTeamDataModel("Orange", "Goalie", "Striker"),
                        new RankedTeamDataModel("Green", "Goalie", "Striker"));
        comparableOutput = new CompareRankedGameOutputModel();
        comparableOutput.prepareCompareTeamOneWithValues("Orange", "Goalie", "Striker");
        comparableOutput.prepareCompareTeamTwoWithValues("Green", "Goalie", "Striker");
        String expected = mapper.writeValueAsString(comparableOutput);

        endpointRequestPerformer.initializeGame(AD_HOC);

        String actual = endpointRequestPerformer.getGameValues();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void initialise_a_ranked_game_with_individual_team_and_player_names() throws Exception {
        String expected = mapper.writeValueAsString(comparableOutput);

        endpointRequestPerformer.initializeGame(RANKED);

        String actual = endpointRequestPerformer.getGameValues();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void undo_scored_goal() throws Exception {
        comparableOutput.prepareScoreOfTeamOne(2);
        String expected = mapper.writeValueAsString(comparableOutput);
        endpointRequestPerformer.initializeGame(RANKED);
        endpointRequestPerformer.countGoalForTeam(ONE, ONE,
                                                  TWO);

        endpointRequestPerformer.undoLastGoal();

        String actual = endpointRequestPerformer.getGameValues();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void undo_a_won_set_when_winning_goal_was_undone() throws Exception {
        comparableOutput.prepareScoreOfTeamOne(5);
        comparableOutput.prepareScoreOfTeamTwo(3);
        comparableOutput.setWinnerOfSet(NO_TEAM);
        String expected = mapper.writeValueAsString(comparableOutput);
        endpointRequestPerformer.initializeGame(RANKED);
        endpointRequestPerformer.countGoalForTeam(ONE,
                                                  TWO, TWO,
                                                  ONE, ONE,
                                                  TWO,
                                                  ONE, ONE, ONE);

        endpointRequestPerformer.undoLastGoal();

        String actual = endpointRequestPerformer.getGameValues();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void undo_a_won_match_when_winning_goal_was_undone() throws Exception {
        comparableOutput.prepareScoreOfTeamOne(5);
        comparableOutput.prepareScoreOfTeamTwo(3);
        comparableOutput.setWinnerOfSet(NO_TEAM);
        comparableOutput.setMatchWinner(NO_TEAM);
        String expected = mapper.writeValueAsString(comparableOutput);
        endpointRequestPerformer.initializeGame(RANKED);
        endpointRequestPerformer.countGoalForTeam(TWO, TWO,
                                                  ONE, ONE,
                                                  TWO,
                                                  ONE, ONE, ONE,
                                                  TWO,
                                                  ONE);
        endpointRequestPerformer.startANewRound();
        endpointRequestPerformer.countGoalForTeam(ONE,
                                                  TWO, TWO,
                                                  ONE, ONE, ONE, ONE,
                                                  TWO,
                                                  ONE);

        endpointRequestPerformer.undoLastGoal();

        String actual = endpointRequestPerformer.getGameValues();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void redo_undone_goals() throws Exception {
        comparableOutput.prepareScoreOfTeamOne(2);
        comparableOutput.prepareScoreOfTeamTwo(2);
        String expected = mapper.writeValueAsString(comparableOutput);
        endpointRequestPerformer.initializeGame(RANKED);
        endpointRequestPerformer.countGoalForTeam(ONE,
                                                  TWO, TWO,
                                                  ONE);
        endpointRequestPerformer.undoLastGoal();

        endpointRequestPerformer.redoLastUndoneGoal();

        String actual = endpointRequestPerformer.getGameValues();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void reset_game_with_empty_team_and_player_names_and_zero_scores() throws Exception {
        comparableOutput = new CompareRankedGameOutputModel();
        String expected = mapper.writeValueAsString(comparableOutput);
        endpointRequestPerformer.initializeGame(RANKED);
        endpointRequestPerformer.countGoalForTeam(ONE,
                                                  TWO,
                                                  ONE, ONE, ONE);

        endpointRequestPerformer.resetValues();

        String actual = endpointRequestPerformer.getGameValues();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void return_a_won_set() throws Exception {
        comparableOutput.prepareScoreOfTeamOne(6);
        comparableOutput.prepareScoreOfTeamTwo(3);
        comparableOutput.setWinnerOfSet(ONE);
        String expected = mapper.writeValueAsString(comparableOutput);

        endpointRequestPerformer.initializeGame(RANKED);
        endpointRequestPerformer.countGoalForTeam(ONE,
                                                  TWO, TWO,
                                                  ONE, ONE,
                                                  TWO,
                                                  ONE, ONE, ONE);

        String actual = endpointRequestPerformer.getGameValues();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void return_a_match_winner() throws Exception {
        comparableOutput.prepareScoreOfTeamOne(4);
        comparableOutput.prepareScoreOfTeamTwo(6);
        comparableOutput.setMatchWinner(TWO);
        comparableOutput.setWinnerOfSet(TWO);
        String expected = mapper.writeValueAsString(comparableOutput);

        endpointRequestPerformer.initializeGame(RANKED);
        endpointRequestPerformer.countGoalForTeam(ONE,
                                                  TWO, TWO,
                                                  ONE,
                                                  TWO,
                                                  ONE, ONE,
                                                  TWO, TWO, TWO);

        endpointRequestPerformer.countGoalForTeam(ONE, ONE, ONE,
                                                  TWO, TWO,
                                                  ONE, ONE,
                                                  TWO,
                                                  ONE);
        endpointRequestPerformer.startANewRound();
        endpointRequestPerformer.countGoalForTeam(TWO, TWO,
                                                  ONE, ONE, ONE,
                                                  TWO,
                                                  ONE,
                                                  TWO, TWO, TWO);

        String actual = endpointRequestPerformer.getGameValues();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void start_a_new_round_with_same_names_but_scores_are_zero() throws Exception {
        String expected = mapper.writeValueAsString(comparableOutput);
        endpointRequestPerformer.initializeGame(RANKED);
        endpointRequestPerformer.countGoalForTeam(ONE, ONE, ONE,
                                                  TWO,
                                                  ONE, ONE,
                                                  TWO, TWO, TWO,
                                                  ONE);

        endpointRequestPerformer.startANewRound();

        String actual = endpointRequestPerformer.getGameValues();
        assertThat(actual).isEqualTo(expected);
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
