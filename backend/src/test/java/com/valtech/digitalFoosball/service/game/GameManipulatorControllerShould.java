package com.valtech.digitalFoosball.service.game;

import com.valtech.digitalFoosball.Application;
import com.valtech.digitalFoosball.api.DigitalFoosballAPI;
import com.valtech.digitalFoosball.api.INotifyAboutStateChanges;
import com.valtech.digitalFoosball.constants.GameMode;
import com.valtech.digitalFoosball.constants.Team;
import com.valtech.digitalFoosball.model.input.InitDataModel;
import com.valtech.digitalFoosball.model.internal.PlayerDataModel;
import com.valtech.digitalFoosball.model.internal.TeamDataModel;
import com.valtech.digitalFoosball.model.output.GameOutputModel;
import com.valtech.digitalFoosball.model.output.TeamOutput;
import com.valtech.digitalFoosball.service.builder.GameBuilder;
import com.valtech.digitalFoosball.service.game.modes.AdHocGameManipulator;
import com.valtech.digitalFoosball.service.game.modes.RankedGameManipulator;
import com.valtech.digitalFoosball.service.game.modes.TimeGameManipulator;
import com.valtech.digitalFoosball.storage.repository.PlayerRepository;
import com.valtech.digitalFoosball.storage.repository.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.valtech.digitalFoosball.constants.Team.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

@ContextConfiguration(classes = Application.class)
@SpringBootTest(classes = DigitalFoosballAPI.class)
class GameManipulatorControllerShould {

    public GameController game;

    protected InitDataModel initDataModel;
    private TeamDataModel teamDataModelOne;
    private TeamDataModel teamDataModelTwo;
    private final UUID id = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        RankedGameManipulator rankedGame = GameBuilder.buildRankedGameWith(new TeamRepositoryFake(id),
                                                                           new PlayerRepositoryFake());
        AdHocGameManipulator adHocGame = GameBuilder.buildAdHocGameWith(new TeamRepositoryFake(id),
                                                                        new PlayerRepositoryFake());
        TimeGameManipulator timeGame = GameBuilder.buildTimeGame();
        game = new GameController(new GameManipulatorProvider(rankedGame, timeGame, adHocGame),
                                  new FakeClientUpdater());
    }

    @Test
    public void sum_up_all_relevant_game_data_in_a_gameDataModel() {
        setUpTeams();
        raiseScoreOf(ONE, TWO);

        GameOutputModel gameOutputModel = game.getGameData();

        List<TeamOutput> actual = gameOutputModel.getTeams();
        assertThat(actual).extracting(TeamOutput::getName,
                                      TeamOutput::getPlayerOne,
                                      TeamOutput::getPlayerTwo,
                                      TeamOutput::getScore).containsExactly(
                tuple("T1", "P1", "P2", 1),
                tuple("T2", "P3", "P4", 1));
    }

    private void setUpTeams() {
        teamDataModelOne = new TeamDataModel("T1", "P1", "P2");
        teamDataModelTwo = new TeamDataModel("T2", "P3", "P4");
        initDataModel = new InitDataModel(teamDataModelOne, teamDataModelTwo);
        game.initGame(initDataModel, GameMode.RANKED);
    }

    @Test
    public void return_empty_model_when_no_teams_are_set_up() {
        GameOutputModel actual = game.getGameData();

        List<TeamOutput> teams = actual.getTeams();
        Team matchWinner = actual.getMatchWinner();
        Team winnerOfSet = actual.getWinnerOfSet();
        assertThat(teams).isEmpty();
        assertThat(matchWinner).isEqualTo(NO_TEAM);
        assertThat(winnerOfSet).isEqualTo(NO_TEAM);
    }

    @Test
    public void delete_all_values_of_the_past_game() {
        setUpTeams();
        raiseScoreOf(ONE, TWO);

        game.resetMatch();

        GameOutputModel gameData = game.getGameData();
        Team winnerOfSet = gameData.getWinnerOfSet();
        Team matchWinner = gameData.getMatchWinner();
        List<TeamOutput> teams = gameData.getTeams();
        assertThat(winnerOfSet).isEqualTo(NO_TEAM);
        assertThat(matchWinner).isEqualTo(NO_TEAM);
        assertThat(teams).isEmpty();
    }

    @Test
    public void set_the_match_winner_when_a_team_won_two_sets() {
        setUpTeams();
        raiseScoreOf(ONE, ONE, ONE, ONE, ONE, ONE);
        game.changeover();
        raiseScoreOf(ONE, ONE, ONE, ONE, ONE, ONE);

        GameOutputModel gameData = game.getGameData();
        Team actualMatchWinner = gameData.getMatchWinner();

        assertThat(actualMatchWinner).isEqualTo(ONE);
    }

    @Test
    public void reset_the_scores_to_zero_but_keep_the_names_saved() {
        setUpTeams();
        raiseScoreOf(ONE, TWO);

        game.changeover();

        GameOutputModel gameData = game.getGameData();
        List<TeamOutput> teams = gameData.getTeams();
        assertThat(teams).extracting(TeamOutput::getScore).containsExactly(0, 0);
        assertThat(teams).extracting(TeamOutput::getName).containsExactly("T1", "T2");
        assertThat(teams).extracting(TeamOutput::getPlayerOne).containsExactly("P1", "P3");
        assertThat(teams).extracting(TeamOutput::getPlayerTwo).containsExactly("P2", "P4");
    }

    private void raiseScoreOf(Team... teams) {
        for (Team team : teams) {
            game.countGoalFor(team);
        }
    }

    private class TeamRepositoryFake implements TeamRepository {
        private final UUID id;

        public TeamRepositoryFake(UUID id) {
            this.id = id;
        }

        @Override
        public TeamDataModel save(TeamDataModel teamDataModel) {
            teamDataModel.setId(id);
            return teamDataModel;
        }

        @Override
        public <S extends TeamDataModel> Iterable<S> saveAll(Iterable<S> iterable) {
            return null;
        }

        @Override
        public Optional<TeamDataModel> findById(UUID uuid) {
            return Optional.empty();
        }

        @Override
        public boolean existsById(UUID uuid) {
            return false;
        }

        @Override
        public Iterable<TeamDataModel> findAllById(Iterable<UUID> iterable) {
            return null;
        }

        @Override
        public long count() {
            return 0;
        }

        @Override
        public void deleteById(UUID uuid) {

        }

        @Override
        public void delete(TeamDataModel teamDataModel) {

        }

        @Override
        public void deleteAll(Iterable<? extends TeamDataModel> iterable) {

        }

        @Override
        public void deleteAll() {

        }

        @Override
        public Optional<TeamDataModel> findByNameIgnoreCase(String teamName) {
            return Optional.empty();
        }

        @Override
        public List<TeamDataModel> findAll() {
            List<TeamDataModel> teamDataModels = new ArrayList<>();
            teamDataModels.add(teamDataModelOne);
            teamDataModels.add(teamDataModelTwo);

            return teamDataModels;
        }
    }

    private class PlayerRepositoryFake implements PlayerRepository {

        @Override
        public Optional<PlayerDataModel> findByName(String name) {
            return Optional.empty();
        }

        public PlayerDataModel save(PlayerDataModel s) {
            s.setId(id);

            return s;
        }

        @Override
        public <S extends PlayerDataModel> Iterable<S> saveAll(Iterable<S> iterable) {
            return null;
        }

        @Override
        public Optional<PlayerDataModel> findById(UUID uuid) {

            return Optional.empty();
        }

        @Override
        public boolean existsById(UUID uuid) {
            return false;
        }

        @Override
        public Iterable<PlayerDataModel> findAll() {
            return null;
        }

        @Override
        public Iterable<PlayerDataModel> findAllById(Iterable<UUID> iterable) {
            return null;
        }

        @Override
        public long count() {
            return 0;
        }

        @Override
        public void deleteById(UUID uuid) {

        }

        @Override
        public void delete(PlayerDataModel playerDataModel) {

        }

        @Override
        public void deleteAll(Iterable<? extends PlayerDataModel> iterable) {

        }

        @Override
        public void deleteAll() {

        }
    }

    private class FakeClientUpdater implements INotifyAboutStateChanges {

        @Override
        public void notifyAboutStateChange(GameOutputModel gameData) {
        }
    }
}