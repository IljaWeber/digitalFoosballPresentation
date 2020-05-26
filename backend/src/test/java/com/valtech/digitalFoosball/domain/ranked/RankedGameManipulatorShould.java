package com.valtech.digitalFoosball.domain.ranked;

import com.valtech.digitalFoosball.GameBuilder;
import com.valtech.digitalFoosball.api.driven.persistence.IObtainTeams;
import com.valtech.digitalFoosball.api.driven.persistence.PlayerService;
import com.valtech.digitalFoosball.api.driven.persistence.TeamService;
import com.valtech.digitalFoosball.api.driven.persistence.repository.PlayerRepository;
import com.valtech.digitalFoosball.api.driven.persistence.repository.TeamRepository;
import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.exceptions.NameDuplicateException;
import com.valtech.digitalFoosball.domain.common.models.GameDataModel;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import com.valtech.digitalFoosball.domain.common.models.PlayerDataModel;
import com.valtech.digitalFoosball.domain.common.models.output.team.TeamOutputModel;
import org.junit.jupiter.api.Test;

import java.util.*;

import static com.valtech.digitalFoosball.domain.common.constants.Team.ONE;
import static com.valtech.digitalFoosball.domain.common.constants.Team.TWO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class RankedGameManipulatorShould {
    private final UUID id = UUID.randomUUID();

    public RankedGame game;

    protected InitDataModel initDataModel;
    private RankedTeamDataModel teamDataModelOne;
    private RankedTeamDataModel teamDataModelTwo;
    private GameDataModel gameData;

    public RankedGameManipulatorShould() {
        TeamRepositoryFake teamRepository = new TeamRepositoryFake(id);
        PlayerRepositoryFake playerRepository = new PlayerRepositoryFake();
        game = GameBuilder.buildRankedGameWith(teamRepository, playerRepository);
        initDataModel = new InitDataModel();
    }

    private void setUpTeams() {
        teamDataModelOne = new RankedTeamDataModel("T1", "P1", "P2");
        teamDataModelTwo = new RankedTeamDataModel("T2", "P3", "P4");
        initDataModel = new InitDataModel(teamDataModelOne, teamDataModelTwo);
        gameData = game.initGame(initDataModel);
    }

    @Test
    public void throw_name_duplicate_exception_when_a_name_is_used_twice() {
        teamDataModelOne = new RankedTeamDataModel("T1", "P1", "P2");
        teamDataModelTwo = new RankedTeamDataModel("T2", "P3", "P1");
        initDataModel = new InitDataModel(teamDataModelOne, teamDataModelTwo);

        assertThatExceptionOfType(NameDuplicateException.class).isThrownBy(() -> game.initGame(initDataModel));
    }

    private List<RankedTeamDataModel> getTeamDataModels() {
        SortedMap<Team, RankedTeamDataModel> teams = gameData.getTeams();
        List<RankedTeamDataModel> actual = new ArrayList<>();
        teams.forEach((k, v) -> actual.add(v));
        return actual;
    }

    @Test
    public void reset_the_scores_to_zero_but_keep_the_names_saved() {
        setUpTeams();
        raiseScoreOf(ONE, TWO);

        game.changeover(gameData);

        List<RankedTeamDataModel> teams = getTeamDataModels();
        assertThat(teams).extracting(RankedTeamDataModel::getScore).containsExactly(0, 0);
        assertThat(teams).extracting(RankedTeamDataModel::getName).containsExactly("T1", "T2");
        assertThat(teams).extracting(RankedTeamDataModel::getNameOfPlayerOne).containsExactly("P1", "P3");
        assertThat(teams).extracting(RankedTeamDataModel::getNameOfPlayerTwo).containsExactly("P2", "P4");
    }

    @Test
    public void forget_about_shot_goals_from_the_past_set() {
        setUpTeams();
        raiseScoreOf(ONE);
        game.undoGoal(gameData);
        game.changeover(gameData);

        game.undoGoal(gameData);

        RankedTeamDataModel team = gameData.getTeam(ONE);
        int actual = team.getScore();
        assertThat(actual).isEqualTo(0);
    }

    @Test
    public void forget_about_undid_goals_from_the_past_set() {
        setUpTeams();
        raiseScoreOf(ONE);
        game.undoGoal(gameData);
        game.changeover(gameData);

        game.redoGoal(gameData);

        RankedTeamDataModel team = gameData.getTeam(ONE);
        int actual = team.getScore();
        assertThat(actual).isEqualTo(0);
    }

    @Test
    public void load_all_teams_ignoring_case() {
        setUpTeams();
        teamDataModelOne.setName("Roto");
        teamDataModelTwo.setName("Rototo");

        List<TeamOutputModel> actual = game.getAllTeamsFromDatabase();

        assertThat(actual).extracting(TeamOutputModel::getName).containsExactly("Roto", "Rototo");
    }

    @Test
    public void load_nothing_when_there_are_no_teams_starting_with_given_letters() {
        setUpTeams();
        setUpTestDoubles();

        List<TeamOutputModel> actual = game.getAllTeamsFromDatabase();

        assertThat(actual).isEmpty();
    }

    private void setUpTestDoubles() {
        TeamRepositoryFakeTwo teamRepository = new TeamRepositoryFakeTwo(id);
        PlayerRepositoryFake playerRepository = new PlayerRepositoryFake();
        PlayerService playerService = new PlayerService(playerRepository);
        IObtainTeams iObtainTeams = new TeamService(teamRepository, playerService);
        game = new RankedGame(new RankedInitService(iObtainTeams));
    }

    private void raiseScoreOf(Team... teams) {
        for (Team team : teams) {
            game.countGoalFor(team, gameData);
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

    private class TeamRepositoryFakeTwo implements TeamRepository {
        private final UUID id;

        public TeamRepositoryFakeTwo(UUID id) {
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

            return new ArrayList<>();
        }
    }
}



