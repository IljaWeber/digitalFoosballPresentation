package com.valtech.digitalFoosball.domain.game.init;

import com.valtech.digitalFoosball.api.driven.persistence.IObtainTeams;
import com.valtech.digitalFoosball.api.driven.persistence.PlayerService;
import com.valtech.digitalFoosball.api.driven.persistence.TeamService;
import com.valtech.digitalFoosball.api.driven.persistence.repository.PlayerRepository;
import com.valtech.digitalFoosball.api.driven.persistence.repository.TeamRepository;
import com.valtech.digitalFoosball.domain.exceptions.NameDuplicateException;
import com.valtech.digitalFoosball.domain.gameModes.models.GameDataModel;
import com.valtech.digitalFoosball.domain.gameModes.models.InitDataModel;
import com.valtech.digitalFoosball.domain.gameModes.models.TeamOutput;
import com.valtech.digitalFoosball.domain.gameModes.regular.models.PlayerDataModel;
import com.valtech.digitalFoosball.domain.gameModes.regular.models.TeamDataModel;
import com.valtech.digitalFoosball.domain.gameModes.regular.ranked.RankedInitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.valtech.digitalFoosball.domain.constants.Team.ONE;
import static com.valtech.digitalFoosball.domain.constants.Team.TWO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class RankedInitServiceShould {

    private TeamDataModel teamDataModelOne;
    private TeamDataModel teamDataModelTwo;
    private final UUID id = UUID.randomUUID();

    private RankedInitService initService;
    private InitDataModel initDataModel;

    @BeforeEach
    void setUp() {
        PlayerRepository playerRepository = new PlayerRepositoryFake();
        PlayerService playerService = new PlayerService(
                playerRepository);
        TeamRepositoryFake teamRepositoryFake = new TeamRepositoryFake(id);

        IObtainTeams iObtainTeams = createTeamServiceWith(playerService, teamRepositoryFake);
        initService = new RankedInitService(iObtainTeams);
    }

    private IObtainTeams createTeamServiceWith(PlayerService playerService,
                                               TeamRepository teamRepositoryFake) {
        return new TeamService(teamRepositoryFake,
                               playerService);
    }

    @Test
    void init_game_with_the_names_from_the_initDataModel() {
        teamDataModelOne = new TeamDataModel("T1", "P1", "P2");
        teamDataModelTwo = new TeamDataModel("T2", "P3", "P4");
        InitDataModel initDataModel = new InitDataModel(teamDataModelOne, teamDataModelTwo);

        GameDataModel gameDataModel = initService.init(initDataModel);
        TeamDataModel teamOne = gameDataModel.getTeam(ONE);
        TeamDataModel teamTwo = gameDataModel.getTeam(TWO);

        assertThat(teamOne).isEqualTo(teamDataModelOne);
        assertThat(teamTwo).isEqualTo(teamDataModelTwo);
    }

    @Test
    public void throw_name_duplicate_exception_when_a_name_is_used_twice() {
        teamDataModelOne = new TeamDataModel("T1", "P1", "P2");
        teamDataModelTwo = new TeamDataModel("T2", "P3", "P1");
        initDataModel = new InitDataModel(teamDataModelOne, teamDataModelTwo);

        assertThatExceptionOfType(NameDuplicateException.class).isThrownBy(() -> initService.init(initDataModel));
    }

    @Test
    public void load_all_teams_ignoring_case() {
        List<TeamOutput> actual = initService.getAllTeams();

        assertThat(actual).extracting(TeamOutput::getName).containsExactly("Roto", "Rototo");
    }

    @Test
    public void load_nothing_when_there_are_no_teams_starting_with_given_letters() {
        useDifferentTestDoubles();

        List<TeamOutput> actual = initService.getAllTeams();

        assertThat(actual).isEmpty();
    }

    private void useDifferentTestDoubles() {
        TeamRepositoryFakeTwo teamRepository = new TeamRepositoryFakeTwo(id);
        PlayerRepositoryFake playerRepository = new PlayerRepositoryFake();
        PlayerService playerService = new PlayerService(playerRepository);
        IObtainTeams teamService = createTeamServiceWith(playerService, teamRepository);
        initService = new RankedInitService(teamService);
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
            TeamDataModel teamOne = new TeamDataModel("Roto", "P1", "P2");
            teamDataModels.add(teamOne);
            TeamDataModel teamTwo = new TeamDataModel("Rototo", "P3", "P4");
            teamDataModels.add(teamTwo);

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
        private final UUID id;

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
