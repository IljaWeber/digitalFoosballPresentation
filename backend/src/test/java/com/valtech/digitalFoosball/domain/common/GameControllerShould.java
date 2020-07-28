package com.valtech.digitalFoosball.domain.common;

import com.valtech.digitalFoosball.GameBuilder;
import com.valtech.digitalFoosball.api.driven.notification.INotifyAboutStateChanges;
import com.valtech.digitalFoosball.api.driven.persistence.repository.PlayerRepository;
import com.valtech.digitalFoosball.api.driven.persistence.repository.TeamRepository;
import com.valtech.digitalFoosball.domain.adhoc.AdHocGame;
import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import com.valtech.digitalFoosball.domain.common.models.PlayerDataModel;
import com.valtech.digitalFoosball.domain.common.models.output.game.EmptyGameOutputModel;
import com.valtech.digitalFoosball.domain.common.models.output.game.GameOutputModel;
import com.valtech.digitalFoosball.domain.common.models.output.team.TeamOutputModel;
import com.valtech.digitalFoosball.domain.ranked.RankedGame;
import com.valtech.digitalFoosball.domain.ranked.TeamDataModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.valtech.digitalFoosball.domain.common.constants.GameMode.RANKED;
import static com.valtech.digitalFoosball.domain.common.constants.Team.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

class GameControllerShould {

    public GameController game;
    private final UUID id = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        TeamDataModel teamDataModelOne = new TeamDataModel("FC Barcelona",
                                                           "Marc-Andre ter Stegen",
                                                           "Lionel Messi");
        TeamDataModel teamDataModelTwo = new TeamDataModel("FC Madrid",
                                                           "Thibaut Courtois",
                                                           "Gareth Bale");

        RankedGame rankedGame = GameBuilder.buildRankedGameWith(new TeamRepositoryFake(id,
                                                                                       teamDataModelOne,
                                                                                       teamDataModelTwo),
                                                                new PlayerRepositoryFake());
        AdHocGame adHocGame = GameBuilder.buildAdHocGameWith(new TeamRepositoryFake(id,
                                                                                    new TeamDataModel(),
                                                                                    new TeamDataModel()),
                                                             new PlayerRepositoryFake());
        game = new GameController(new GameProvider(rankedGame,
                                                   adHocGame),
                                  new FakeClientUpdater());

        InitDataModel initDataModel
                = new InitDataModel(teamDataModelOne,
                                    teamDataModelTwo);
        initDataModel.setMode(RANKED);

        game.initGame(initDataModel);
    }

    @Test
    public void show_all_team_and_player_names() {
        raiseScoreOf(ONE,
                     TWO);

        GameOutputModel gameOutputModel = game.getGameData();

        List<TeamOutputModel> actual = gameOutputModel.getTeams();
        assertThat(actual).extracting(TeamOutputModel::getName,
                                      TeamOutputModel::getPlayerOne,
                                      TeamOutputModel::getPlayerTwo,
                                      TeamOutputModel::getScore)
                          .containsExactly(
                                  tuple("FC Barcelona",
                                        "Marc-Andre ter Stegen",
                                        "Lionel Messi",
                                        1),
                                  tuple("FC Madrid",
                                        "Thibaut Courtois",
                                        "Gareth Bale",
                                        1));
    }

    // OPTIMIZE: create on 22.07.20 by iljaweber: reconsider next use case

    @Test
    @Disabled("Might be deprecated")
    public void return_empty_model_when_no_teams_are_set_up() {
        GameOutputModel actual = game.getGameData();

        assertThat(actual).isInstanceOf(EmptyGameOutputModel.class);
    }

    @Test
    public void delete_all_values_of_the_past_game() {
        raiseScoreOf(ONE, TWO);

        game.resetMatch();

        GameOutputModel gameData = game.getGameData();
        Team winnerOfSet = gameData.getWinnerOfSet();
        Team matchWinner = gameData.getMatchWinner();
        List<TeamOutputModel> teams = gameData.getTeams();
        assertThat(winnerOfSet).isEqualTo(NO_TEAM);
        assertThat(matchWinner).isEqualTo(NO_TEAM);
        assertThat(teams).isEmpty();
    }

    @Test
    public void set_the_match_winner_when_a_team_won_two_sets() {
        raiseScoreOf(ONE, ONE, ONE, ONE, ONE, ONE);
        game.changeover();
        raiseScoreOf(ONE, ONE, ONE, ONE, ONE, ONE);

        GameOutputModel gameData = game.getGameData();
        Team actualMatchWinner = gameData.getMatchWinner();

        assertThat(actualMatchWinner).isEqualTo(ONE);
    }

    @Test
    public void reset_the_scores_to_zero_but_keep_the_names_saved() {
        raiseScoreOf(ONE, TWO);

        game.changeover();

        GameOutputModel gameData = game.getGameData();
        List<TeamOutputModel> teams = gameData.getTeams();
        assertThat(teams).extracting(TeamOutputModel::getName)
                         .containsExactly("FC Barcelona",
                                          "FC Madrid");
        assertThat(teams).extracting(TeamOutputModel::getPlayerOne)
                         .containsExactly("Marc-Andre ter Stegen",
                                          "Thibaut Courtois");
        assertThat(teams).extracting(TeamOutputModel::getPlayerTwo)
                         .containsExactly("Lionel Messi",
                                          "Gareth Bale");
        assertThat(teams).extracting(TeamOutputModel::getScore)
                         .containsExactly(0, 0);
    }

    private void raiseScoreOf(Team... teams) {
        for (Team team : teams) {
            game.countGoalFor(team);
        }
    }

    private class TeamRepositoryFake implements TeamRepository {
        private final UUID id;
        private final TeamDataModel teamDataModelTwo;
        private final TeamDataModel teamDataModelOne;

        public TeamRepositoryFake(UUID id,
                                  TeamDataModel teamDataModelOne,
                                  TeamDataModel teamDataModelTwo) {
            this.teamDataModelOne = teamDataModelOne;
            this.teamDataModelTwo = teamDataModelTwo;
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
