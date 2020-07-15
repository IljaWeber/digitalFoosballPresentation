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
import com.valtech.digitalFoosball.domain.ranked.RankedTeamDataModel;
import org.junit.jupiter.api.BeforeEach;
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

    private InitDataModel initDataModel;
    private RankedTeamDataModel teamDataModelOne;
    private RankedTeamDataModel teamDataModelTwo;
    private final UUID id = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        RankedGame rankedGame = GameBuilder.buildRankedGameWith(new TeamRepositoryFake(id),
                                                                new PlayerRepositoryFake());
        AdHocGame adHocGame = GameBuilder.buildAdHocGameWith(new TeamRepositoryFake(id),
                                                             new PlayerRepositoryFake());
        game = new GameController(new GameProvider(rankedGame, adHocGame),
                                  new FakeClientUpdater());

    }

    @Test
    public void sum_up_all_relevant_game_data_in_a_gameDataModel() {
        setUpTeams();
        raiseScoreOf(ONE, TWO);

        GameOutputModel gameOutputModel = game.getGameData();

        List<TeamOutputModel> actual = gameOutputModel.getTeams();
        assertThat(actual).extracting(TeamOutputModel::getName,
                                      TeamOutputModel::getPlayerOne,
                                      TeamOutputModel::getPlayerTwo,
                                      TeamOutputModel::getScore).containsExactly(
                tuple("T1", "P1", "P2", 1),
                tuple("T2", "P3", "P4", 1));
    }

    private void setUpTeams() {
        teamDataModelOne = new RankedTeamDataModel("T1", "P1", "P2");
        teamDataModelTwo = new RankedTeamDataModel("T2", "P3", "P4");
        initDataModel = new InitDataModel(teamDataModelOne, teamDataModelTwo);
        initDataModel.setMode(RANKED);
        game.initGame(initDataModel);
    }

    // todo reconsider this use case

    @Test
    public void return_empty_model_when_no_teams_are_set_up() {
        GameOutputModel actual = game.getGameData();

        assertThat(actual).isInstanceOf(EmptyGameOutputModel.class);
    }

    @Test
    public void delete_all_values_of_the_past_game() {
        setUpTeams();
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
        List<TeamOutputModel> teams = gameData.getTeams();
        assertThat(teams).extracting(TeamOutputModel::getScore).containsExactly(0, 0);
        assertThat(teams).extracting(TeamOutputModel::getName).containsExactly("T1", "T2");
        assertThat(teams).extracting(TeamOutputModel::getPlayerOne).containsExactly("P1", "P3");
        assertThat(teams).extracting(TeamOutputModel::getPlayerTwo).containsExactly("P2", "P4");
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
        public RankedTeamDataModel save(RankedTeamDataModel teamDataModel) {
            teamDataModel.setId(id);
            return teamDataModel;
        }

        @Override
        public <S extends RankedTeamDataModel> Iterable<S> saveAll(Iterable<S> iterable) {
            return null;
        }

        @Override
        public Optional<RankedTeamDataModel> findById(UUID uuid) {
            return Optional.empty();
        }

        @Override
        public boolean existsById(UUID uuid) {
            return false;
        }

        @Override
        public Iterable<RankedTeamDataModel> findAllById(Iterable<UUID> iterable) {
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
        public void delete(RankedTeamDataModel teamDataModel) {

        }

        @Override
        public void deleteAll(Iterable<? extends RankedTeamDataModel> iterable) {

        }

        @Override
        public void deleteAll() {

        }

        @Override
        public Optional<RankedTeamDataModel> findByNameIgnoreCase(String teamName) {
            return Optional.empty();
        }

        @Override
        public List<RankedTeamDataModel> findAll() {
            List<RankedTeamDataModel> teamDataModels = new ArrayList<>();
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
