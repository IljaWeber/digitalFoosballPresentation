package com.valtech.digitalFoosball.adapter;

import com.valtech.digitalFoosball.domain.common.models.TeamDataModel;
import com.valtech.digitalFoosball.domain.ports.RankedGamePersistencePort;
import com.valtech.digitalFoosball.entity.PlayerDataEntity;
import com.valtech.digitalFoosball.entity.TeamDataEntity;
import com.valtech.digitalFoosball.repository.PlayerRepository;
import com.valtech.digitalFoosball.repository.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class TeamServiceShouldGetAll {

    private TeamDataModel data;
    private final UUID uuid = UUID.randomUUID();
    private TeamRepositoryFake teamRepo;
    private PlayerService playerService;
    private PlayerRepositoryFake playerRepo;
    private RankedGamePersistencePort teamService;

    @BeforeEach
    public void setUp() {
        teamRepo = new TeamRepositoryFake(uuid);
        playerRepo = new PlayerRepositoryFake();
        playerService = new PlayerService(playerRepo);
        data = new TeamDataModel();
        teamService = new TeamService(teamRepo, playerService);
    }

    @Test
    public void when_teams_were_found_ignoring_case() {
        data.setName("Gelb");
        teamService.loadOrSaveIntoDatabase(data);
        data.setName("Geld");
        teamService.loadOrSaveIntoDatabase(data);

        List<TeamDataModel> actual = teamService.getAllTeamsFromDatabase();

        assertThat(actual).extracting(TeamDataModel::getName).containsExactly("Gelb", "Geld");
    }

    @Test
    public void when_no_teams_were_found_then_load_none() {
        teamService = new TeamService(new TeamRepositoryFakeTwo(uuid), playerService);
        List<TeamDataModel> actual = teamService.getAllTeamsFromDatabase();

        assertThat(actual).isEmpty();
    }

    private class TeamRepositoryFake implements TeamRepository {
        private final UUID id;
        private List<TeamDataEntity> teamDataModels = new ArrayList<>();

        public TeamRepositoryFake(UUID id) {
            this.id = id;
        }

        @Override
        public TeamDataEntity save(TeamDataEntity teamDataEntity) {
            teamDataEntity.setId(id);
            teamDataModels.add(teamDataEntity);
            return teamDataEntity;
        }

        @Override
        public <S extends TeamDataEntity> Iterable<S> saveAll(Iterable<S> iterable) {
            return null;
        }

        @Override
        public Optional<TeamDataEntity> findById(UUID uuid) {
            return Optional.empty();
        }

        @Override
        public boolean existsById(UUID uuid) {
            return false;
        }

        @Override
        public Iterable<TeamDataEntity> findAllById(Iterable<UUID> iterable) {
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
        public void delete(TeamDataEntity teamDataModel) {

        }

        @Override
        public void deleteAll(Iterable<? extends TeamDataEntity> iterable) {

        }

        @Override
        public void deleteAll() {

        }

        @Override
        public Optional<TeamDataEntity> findByNameIgnoreCase(String teamName) {
            return Optional.empty();
        }

        @Override
        public List<TeamDataEntity> findAll() {

            return teamDataModels;
        }

        public void insertTeamDataModel(TeamDataEntity teamOne, TeamDataEntity teamTwo) {
            teamDataModels = new ArrayList<>();
            teamDataModels.add(teamOne);
            teamDataModels.add(teamTwo);
        }
    }

    private class PlayerRepositoryFake implements PlayerRepository {

        @Override
        public Optional<PlayerDataEntity> findByName(String name) {
            return Optional.empty();
        }

        public PlayerDataEntity save(PlayerDataEntity s) {
            s.setId(uuid);

            return s;
        }

        @Override
        public <S extends PlayerDataEntity> Iterable<S> saveAll(Iterable<S> iterable) {
            return null;
        }

        @Override
        public Optional<PlayerDataEntity> findById(UUID uuid) {

            return Optional.empty();
        }

        @Override
        public boolean existsById(UUID uuid) {
            return false;
        }

        @Override
        public Iterable<PlayerDataEntity> findAll() {
            return null;
        }

        @Override
        public Iterable<PlayerDataEntity> findAllById(Iterable<UUID> iterable) {
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
        public void delete(PlayerDataEntity playerDataModel) {

        }

        @Override
        public void deleteAll(Iterable<? extends PlayerDataEntity> iterable) {

        }

        @Override
        public void deleteAll() {

        }
    }

    private class TeamRepositoryFakeTwo implements TeamRepository {
        private final UUID id;

        public TeamRepositoryFakeTwo(UUID id) {
            this.id = id;
        }

        @Override
        public TeamDataEntity save(TeamDataEntity teamDataModel) {
            teamDataModel.setId(id);
            return teamDataModel;
        }

        @Override
        public <S extends TeamDataEntity> Iterable<S> saveAll(Iterable<S> iterable) {
            return null;
        }

        @Override
        public Optional<TeamDataEntity> findById(UUID uuid) {
            return Optional.empty();
        }

        @Override
        public boolean existsById(UUID uuid) {
            return false;
        }

        @Override
        public Iterable<TeamDataEntity> findAllById(Iterable<UUID> iterable) {
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
        public void delete(TeamDataEntity teamDataModel) {

        }

        @Override
        public void deleteAll(Iterable<? extends TeamDataEntity> iterable) {

        }

        @Override
        public void deleteAll() {

        }

        @Override
        public Optional<TeamDataEntity> findByNameIgnoreCase(String teamName) {
            return Optional.empty();
        }

        @Override
        public List<TeamDataEntity> findAll() {

            return new ArrayList<>();
        }
    }
}
