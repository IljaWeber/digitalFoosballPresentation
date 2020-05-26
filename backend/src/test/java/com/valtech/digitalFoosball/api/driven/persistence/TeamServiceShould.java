package com.valtech.digitalFoosball.api.driven.persistence;

import com.valtech.digitalFoosball.api.driven.persistence.repository.PlayerRepository;
import com.valtech.digitalFoosball.api.driven.persistence.repository.TeamRepository;
import com.valtech.digitalFoosball.domain.common.models.PlayerDataModel;
import com.valtech.digitalFoosball.domain.ranked.RankedTeamDataModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class TeamServiceShould {
    private RankedTeamDataModel data;
    private final UUID uuid = UUID.randomUUID();
    private TeamRepositoryFake teamRepo;
    private PlayerService playerService;
    private PlayerRepositoryFake playerRepo;
    private com.valtech.digitalFoosball.api.driven.persistence.IObtainTeams IObtainTeams;

    @BeforeEach
    public void setUp() {
        teamRepo = new TeamRepositoryFake(uuid);
        playerRepo = new PlayerRepositoryFake();
        playerService = new PlayerService(playerRepo);
        data = new RankedTeamDataModel();
        IObtainTeams = new TeamService(teamRepo, playerService);
    }

    @Test
    public void load_team_ignoring_case_when_team_name_already_exists() {
        data.setName("TeamOne");
        data.setNameOfPlayerOne("PlayerOne");
        data.setNameOfPlayerTwo("PlayerTwo");

        RankedTeamDataModel teamDataModel = IObtainTeams.loadOrSaveIntoDatabase(data);
        UUID actual = teamDataModel.getId();

        assertThat(actual).isEqualTo(uuid);
    }

    @Test
    public void save_team_into_database_when_team_does_not_exist() {
        data.setName("x");
        data.setNameOfPlayerOne("y");
        data.setNameOfPlayerTwo("z");

        RankedTeamDataModel teamDataModel = IObtainTeams.loadOrSaveIntoDatabase(data);
        UUID actual = teamDataModel.getId();

        assertThat(actual).isEqualTo(uuid);
    }

    @Test
    public void load_players_when_existing_ignoring_in_what_team_they_are() {
        data.setName("x");
        data.setNameOfPlayerOne("y");
        data.setNameOfPlayerTwo("z");

        RankedTeamDataModel actual = IObtainTeams.loadOrSaveIntoDatabase(data);

        List<PlayerDataModel> players = actual.getPlayers();
        assertThat(players).extracting(PlayerDataModel::getId).containsExactly(uuid, uuid);
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
            if (teamName.equals("x")) {
                return Optional.empty();
            }
            data.setId(id);
            return Optional.of(data);
        }

        @Override
        public List<RankedTeamDataModel> findAll() {
            List<RankedTeamDataModel> teamDataModels = new ArrayList<>();

            RankedTeamDataModel teamDataModel = new RankedTeamDataModel();
            teamDataModel.setName("Gelb");
            teamDataModels.add(teamDataModel);

            RankedTeamDataModel teamDataModel1 = new RankedTeamDataModel();
            teamDataModel1.setName("Geld");
            teamDataModels.add(teamDataModel1);

            return teamDataModels;
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
