package com.valtech.digitalFoosball.storage;

import com.valtech.digitalFoosball.model.internal.PlayerDataModel;
import com.valtech.digitalFoosball.model.internal.TeamDataModel;
import com.valtech.digitalFoosball.storage.repository.PlayerRepository;
import com.valtech.digitalFoosball.storage.repository.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class TeamServiceTest {
    private TeamDataModel data;
    private final UUID uuid = UUID.randomUUID();
    private TeamRepositoryFake teamRepo;
    private PlayerService playerService;
    private PlayerRepositoryFake playerRepo;
    private TeamService teamService;

    @BeforeEach
    public void setUp() {
        teamRepo = new TeamRepositoryFake(uuid);
        playerRepo = new PlayerRepositoryFake();
        playerService = new PlayerService(playerRepo);
        data = new TeamDataModel();
        teamService = new TeamService(teamRepo, playerService);
    }

    @Test
    public void setUp_whenSearchingForExistingTeam_thenLoadThisTeam() {
        data.setName("TeamOne");
        data.setNameOfPlayerOne("PlayerOne");
        data.setNameOfPlayerTwo("PlayerTwo");

        TeamDataModel actual = teamService.setUp(data);

        assertThat(actual).extracting(TeamDataModel::getId, TeamDataModel::getName, TeamDataModel::getNameOfPlayerOne, TeamDataModel::getNameOfPlayerTwo).containsExactly(uuid, "TeamOne", "PlayerOne", "PlayerTwo");
    }

    @Test
    public void setUp_whenSearchingForNonExistingTeam_thenCreateAndLoadThisTeam() {
        data.setName("x");
        data.setNameOfPlayerOne("y");
        data.setNameOfPlayerTwo("z");

        TeamDataModel actual = teamService.setUp(data);

        assertThat(actual).extracting(TeamDataModel::getId, TeamDataModel::getName, TeamDataModel::getNameOfPlayerOne, TeamDataModel::getNameOfPlayerTwo).containsExactly(uuid, "x", "y", "z");
    }

    @Test
    public void setUp_whenPlayersAlreadyExists_thenLoadThem() {
        data.setName("x");
        data.setNameOfPlayerOne("y");
        data.setNameOfPlayerTwo("z");

        TeamDataModel actual = teamService.setUp(data);

        List<PlayerDataModel> players = actual.getPlayers();
        assertThat(players).extracting(PlayerDataModel::getId).containsExactly(uuid, uuid);
    }

    @Test
    public void getAll_whenNoTeamIsFound_thenLoadNoTeam() {
        teamService = new TeamService(new TeamRepositoryFakeTwo(uuid), playerService);
        List<TeamDataModel> actual = teamService.getAll();

        assertThat(actual).isEmpty();
    }

    @Test
    public void getAll_whenTeamsWereFound_thenLoadTheseTeams() {
        List<TeamDataModel> expected = new ArrayList<>();
        data.setName("Gelb");
        expected.add(data);
        data.setName("Geld");
        expected.add(data);

        List<TeamDataModel> actual = teamService.getAll();

        assertThat(actual).extracting(TeamDataModel::getName).containsExactly("Gelb", "Geld");
    }

    @Test
    void saveMatchWin_whenATeamHasWonAMatch_thenSaveIt() {

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
            if (teamName.equals("x")) {
                return Optional.empty();
            }
            data.setId(id);
            return Optional.of(data);
        }

        @Override
        public List<TeamDataModel> findAll() {
            List<TeamDataModel> teamDataModels = new ArrayList<>();

            TeamDataModel teamDataModel = new TeamDataModel();
            teamDataModel.setName("Gelb");
            teamDataModels.add(teamDataModel);

            TeamDataModel teamDataModel1 = new TeamDataModel();
            teamDataModel1.setName("Geld");
            teamDataModels.add(teamDataModel1);

            return teamDataModels;
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
            if (teamName.equals("x")) {
                return Optional.empty();
            }
            data.setId(id);
            return Optional.of(data);
        }

        @Override
        public List<TeamDataModel> findAll() {
            return new ArrayList<>();
        }
    }

    private class PlayerRepositoryFake implements PlayerRepository {

        @Override
        public Optional<PlayerDataModel> findByName(String name) {
            PlayerDataModel playerDataModel = new PlayerDataModel();
            playerDataModel.setName(name);
            playerDataModel.setId(uuid);
            return Optional.of(playerDataModel);
        }

        @Override
        public <S extends PlayerDataModel> S save(S entity) {
            return null;
        }

        @Override
        public <S extends PlayerDataModel> Iterable<S> saveAll(Iterable<S> entities) {
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
        public Iterable<PlayerDataModel> findAllById(Iterable<UUID> uuids) {
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
        public void delete(PlayerDataModel entity) {

        }

        @Override
        public void deleteAll(Iterable<? extends PlayerDataModel> entities) {

        }

        @Override
        public void deleteAll() {

        }
    }
}
