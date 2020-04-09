package com.valtech.digitalFoosball.service;

import com.valtech.digitalFoosball.constants.Team;
import com.valtech.digitalFoosball.helper.extractor.GameDataExtractor;
import com.valtech.digitalFoosball.helper.factories.TeamDataModelFactory;
import com.valtech.digitalFoosball.model.input.InitDataModel;
import com.valtech.digitalFoosball.model.internal.PlayerDataModel;
import com.valtech.digitalFoosball.model.internal.TeamDataModel;
import com.valtech.digitalFoosball.storage.PlayerService;
import com.valtech.digitalFoosball.storage.TeamService;
import com.valtech.digitalFoosball.storage.repository.PlayerRepository;
import com.valtech.digitalFoosball.storage.repository.TeamRepository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class GameManagerShouldUndoLastGoal {
    private final UUID id = UUID.randomUUID();
    public GameManager gameManager;
    protected InitDataModel initDataModel;
    GameDataExtractor dataExtractor = new GameDataExtractor();
    private TeamDataModel teamDataModelOne;
    private TeamDataModel teamDataModelTwo;
    private TeamDataModelFactory teamDataModelFactory;

    public GameManagerShouldUndoLastGoal() {
        teamDataModelFactory = new TeamDataModelFactory();
        initDataModel = new InitDataModel();

        TeamRepositoryFake teamRepository = new TeamRepositoryFake(id);
        PlayerRepositoryFake playerRepository = new PlayerRepositoryFake();
        PlayerService playerService = new PlayerService(playerRepository);
        TeamService teamService = new TeamService(teamRepository, playerService);
        gameManager = new GameManager(teamService);
    }

    private void setUpTeams() {
        List<TeamDataModel> teamDataModels = new ArrayList<>();
        teamDataModelOne = teamDataModelFactory.getInstanceWithNames("T1", "P1", "P2");
        teamDataModelTwo = teamDataModelFactory.getInstanceWithNames("T2", "P3", "P4");
        teamDataModels.add(teamDataModelOne);
        teamDataModels.add(teamDataModelTwo);
        initDataModel.setTeams(teamDataModels);
        gameManager.initGame(initDataModel);
    }

    @Test
    void in_the_reversed_order_of_scoring() {
        setUpTeams();
        raiseScoreOf(Team.ONE, Team.TWO, Team.ONE);

        gameManager.undoGoal();

        dataExtractor.setGameManager(gameManager);
        int actual = dataExtractor.extractScoreOf(Team.ONE);
        assertThat(actual).isEqualTo(1);
    }

    @Test
    void but_if_no_scores_have_been_made_then_do_nothing() {
        setUpTeams();

        gameManager.undoGoal();

        dataExtractor.setGameManager(gameManager);
        int actualScoreTeamOne = dataExtractor.extractScoreOf(Team.ONE);
        int actualScoreTeamTwo = dataExtractor.extractScoreOf(Team.TWO);
        assertThat(actualScoreTeamOne).isEqualTo(0);
        assertThat(actualScoreTeamTwo).isEqualTo(0);
    }

    @Test
    void and_decrease_the_number_of_won_sets_when_win_condition_has_been_fulfilled() {
        setUpTeams();
        raiseScoreOf(Team.ONE, Team.ONE, Team.ONE, Team.ONE, Team.ONE, Team.ONE);

        gameManager.undoGoal();

        dataExtractor.setGameManager(gameManager);
        int actual = dataExtractor.extractNumberOfWonSetsOf(Team.ONE);
        assertThat(actual).isEqualTo(0);
    }

    private void raiseScoreOf(Team... teams) {
        for (Team team : teams) {
            gameManager.countGoalFor(team);
        }
    }

    private class TeamRepositoryFake implements TeamRepository {
        private UUID id;

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

    private class TeamRepositoryFakeTwo implements TeamRepository {
        private UUID id;

        public TeamRepositoryFakeTwo(UUID id) {
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

            return new ArrayList<>();
        }
    }
}
