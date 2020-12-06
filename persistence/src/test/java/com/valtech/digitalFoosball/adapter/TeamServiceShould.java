package com.valtech.digitalFoosball.adapter;

import com.valtech.digitalFoosball.domain.common.models.PlayerDataModel;
import com.valtech.digitalFoosball.domain.common.models.TeamDataModel;
import com.valtech.digitalFoosball.entity.PlayerDataEntity;
import com.valtech.digitalFoosball.entity.TeamDataEntity;
import com.valtech.digitalFoosball.repository.PlayerRepository;
import com.valtech.digitalFoosball.repository.TeamRepository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class TeamServiceShould {
    private TeamDataModel data;
    private final UUID uuid = UUID.randomUUID();
    private TeamRepositoryFake teamRepo;
    private PlayerService playerService;
    private PlayerRepositoryFake playerRepo;
    private final TeamService teamService;

    public TeamServiceShould() {
        teamRepo = new TeamRepositoryFake(uuid);
        playerRepo = new PlayerRepositoryFake();
        playerService = new PlayerService(playerRepo);
        data = new TeamDataModel();
        teamService = new TeamService(teamRepo, playerService);
    }

    @Test
    public void load_team_ignoring_case_when_team_name_already_exists() {
        data.setName("TeamOne");
        data.setNameOfPlayerOne("PlayerOne");
        data.setNameOfPlayerTwo("PlayerTwo");

        TeamDataModel actual = teamService.loadOrSaveIntoDatabase(data);

        assertThat(actual.getName()).isEqualTo("TeamOne");
    }

    @Test
    public void save_team_into_database_when_team_does_not_exist() {
        data.setName("x");
        data.setNameOfPlayerOne("y");
        data.setNameOfPlayerTwo("z");

        TeamDataModel actual = teamService.loadOrSaveIntoDatabase(data);

        assertThat(actual.getName()).isEqualTo("x");
    }

    @Test
    public void load_players_when_existing_ignoring_in_what_team_they_are() {
        data.setName("x");
        data.setNameOfPlayerOne("y");
        data.setNameOfPlayerTwo("z");

        TeamDataModel actual = teamService.loadOrSaveIntoDatabase(data);

        List<PlayerDataModel> players = actual.getPlayers();
        assertThat(players).extracting(PlayerDataModel::getName).contains("y", "z");
    }

    private class TeamRepositoryFake implements TeamRepository {
        private final UUID id;
        private List<TeamDataEntity> teamDataModels;

        public TeamRepositoryFake(UUID id) {
            this.id = id;
        }

        @Override
        public TeamDataEntity save(TeamDataEntity teamDataEntity) {
            teamDataEntity.setId(id);
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
}
