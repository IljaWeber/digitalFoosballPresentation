package com.valtech.digitalFoosball.domain.ranked;

import com.valtech.digitalFoosball.api.driven.persistence.IObtainTeams;
import com.valtech.digitalFoosball.api.driven.persistence.PlayerService;
import com.valtech.digitalFoosball.api.driven.persistence.TeamService;
import com.valtech.digitalFoosball.api.driven.persistence.repository.PlayerRepository;
import com.valtech.digitalFoosball.api.driven.persistence.repository.TeamRepository;
import com.valtech.digitalFoosball.domain.common.IPlayAGame;
import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.exceptions.NameDuplicateException;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import com.valtech.digitalFoosball.domain.common.models.PlayerDataModel;
import com.valtech.digitalFoosball.domain.common.models.TeamDataModel;
import com.valtech.digitalFoosball.domain.common.models.output.game.GameOutputModel;
import com.valtech.digitalFoosball.domain.common.models.output.team.TeamOutputModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.valtech.digitalFoosball.domain.common.constants.Team.ONE;
import static com.valtech.digitalFoosball.domain.common.constants.Team.TWO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class RankedGameManipulatorShould {
    private final UUID id = UUID.randomUUID();
    public IPlayAGame game;

    @BeforeEach
    void setUp() {
        TeamDataModel teamOne = new TeamDataModel("T1", "P1", "P2");
        TeamDataModel teamTwo = new TeamDataModel("T2", "P3", "P4");
        InitDataModel initDataModel = new InitDataModel(teamOne, teamTwo);

        TeamRepository teamRepository = new TeamRepositoryFake(id);
        PlayerRepository playerRepository = new PlayerRepositoryFake();
        game = new ClassicGame(new RankedInitService(new TeamService(teamRepository,
                                                                     new PlayerService(playerRepository))));
        game.initGame(initDataModel);
    }

    @Test
    public void throw_name_duplicate_exception_when_a_name_is_used_twice() {
        TeamDataModel teamDataModelOne = new TeamDataModel("T1", "P1", "P2");
        TeamDataModel teamDataModelTwo = new TeamDataModel("T2", "P3", "P1");

        InitDataModel initDataModel = new InitDataModel(teamDataModelOne, teamDataModelTwo);

        assertThatExceptionOfType(NameDuplicateException.class).isThrownBy(() -> game.initGame(initDataModel));
    }

    private List<TeamOutputModel> getTeamDataModels() {
        GameOutputModel gameData = game.getGameData();
        List<TeamOutputModel> teams = gameData.getTeams();
        return teams;
    }

    @Test
    public void reset_the_scores_to_zero_but_keep_the_names_saved() {
        raiseScoreOf(ONE, TWO);

        game.changeover();

        List<TeamOutputModel> teams = getTeamDataModels();
        assertThat(teams).extracting(TeamOutputModel::getScore).containsExactly(0, 0);
        assertThat(teams).extracting(TeamOutputModel::getName).containsExactly("T1", "T2");
        assertThat(teams).extracting(TeamOutputModel::getPlayerOne).containsExactly("P1", "P3");
        assertThat(teams).extracting(TeamOutputModel::getPlayerTwo).containsExactly("P2", "P4");
    }

    @Test
    public void forget_about_shot_goals_from_the_past_set() {
        raiseScoreOf(ONE);
        game.undoGoal();
        game.changeover();

        game.undoGoal();

        GameOutputModel gameData = game.getGameData();
        int actual = gameData.getTeam(ONE).getScore();
        assertThat(actual).isEqualTo(0);
    }

    @Test
    public void forget_about_undid_goals_from_the_past_set() {
        raiseScoreOf(ONE);
        game.undoGoal();
        game.changeover();

        game.redoGoal();

        GameOutputModel gameData = game.getGameData();
        int actual = gameData.getTeam(ONE).getScore();
        assertThat(actual).isEqualTo(0);
    }

    @Test
    public void load_all_teams_ignoring_case() {
        TeamDataModel teamOne = new TeamDataModel("Roto", "P1", "P2");
        TeamDataModel teamTwo = new TeamDataModel("Rototo", "P3", "P4");
        TeamRepositoryFake teamRepository = new TeamRepositoryFake(id);
        PlayerRepositoryFake playerRepository = new PlayerRepositoryFake();
        teamRepository.insertTeamDataModel(teamOne, teamTwo);
        game = new ClassicGame(new RankedInitService(new TeamService(teamRepository,
                                                                     new PlayerService(playerRepository))));

        List<TeamOutputModel> actual = game.getAllTeamsFromDatabase();

        assertThat(actual).extracting(TeamOutputModel::getName).containsExactly("Roto", "Rototo");
    }

    @Test
    public void load_nothing_when_there_are_no_teams_starting_with_given_letters() {
        setUpTestDoubles();

        List<TeamOutputModel> actual = game.getAllTeamsFromDatabase();

        assertThat(actual).isEmpty();
    }

    private void setUpTestDoubles() {
        TeamRepositoryFakeTwo teamRepository = new TeamRepositoryFakeTwo(id);
        PlayerRepositoryFake playerRepository = new PlayerRepositoryFake();
        PlayerService playerService = new PlayerService(playerRepository);
        IObtainTeams iObtainTeams = new TeamService(teamRepository, playerService);
        game = new ClassicGame(new RankedInitService(iObtainTeams));
    }

    private void raiseScoreOf(Team... teams) {
        for (Team team : teams) {
            game.countGoalFor(team);
        }
    }

    private class TeamRepositoryFake implements TeamRepository {
        private final UUID id;
        private List<TeamDataModel> teamDataModels;

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

            return teamDataModels;
        }

        public void insertTeamDataModel(TeamDataModel teamOne, TeamDataModel teamTwo) {
            teamDataModels = new ArrayList<>();
            teamDataModels.add(teamOne);
            teamDataModels.add(teamTwo);
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



