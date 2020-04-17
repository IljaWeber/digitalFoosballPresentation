package com.valtech.digitalFoosball.service.manager;

import com.valtech.digitalFoosball.constants.Team;
import com.valtech.digitalFoosball.exceptions.NameDuplicateException;
import com.valtech.digitalFoosball.model.input.InitDataModel;
import com.valtech.digitalFoosball.model.internal.PlayerDataModel;
import com.valtech.digitalFoosball.model.internal.TeamDataModel;
import com.valtech.digitalFoosball.model.output.GameDataModel;
import com.valtech.digitalFoosball.model.output.TeamOutput;
import com.valtech.digitalFoosball.storage.PlayerService;
import com.valtech.digitalFoosball.storage.TeamService;
import com.valtech.digitalFoosball.storage.repository.PlayerRepository;
import com.valtech.digitalFoosball.storage.repository.TeamRepository;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.valtech.digitalFoosball.constants.Team.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.groups.Tuple.tuple;

public class GameManagerShould {
    private final UUID id = UUID.randomUUID();
    public GameManager gameManager;
    protected InitDataModel initDataModel;
    private TeamDataModel teamDataModelOne;
    private TeamDataModel teamDataModelTwo;

    public GameManagerShould() {
        initDataModel = new InitDataModel();
        TeamRepositoryFake teamRepository = new TeamRepositoryFake(id);
        PlayerRepositoryFake playerRepository = new PlayerRepositoryFake();
        PlayerService playerService = new PlayerService(playerRepository);
        TeamService teamService = new TeamService(teamRepository, playerService);
//        gameManager = new GameManager(teamService, new );
    }

    private void setUpTeams() {
        teamDataModelOne = new TeamDataModel("T1", "P1", "P2");
        teamDataModelTwo = new TeamDataModel("T2", "P3", "P4");
        initDataModel = new InitDataModel(teamDataModelOne, teamDataModelTwo);
        gameManager.initGame(initDataModel);
    }

    @Test
    public void throw_name_duplicate_exception_when_a_name_is_used_twice() {
        teamDataModelOne = new TeamDataModel("T1", "P1", "P2");
        teamDataModelTwo = new TeamDataModel("T2", "P3", "P1");
        initDataModel = new InitDataModel(teamDataModelOne, teamDataModelTwo);

        assertThatExceptionOfType(NameDuplicateException.class).isThrownBy(() -> {
            gameManager.initGame(initDataModel);
        });
    }

    @Test
    public void sum_up_all_relevant_game_data_in_a_gameDataModel() {
        setUpTeams();
        raiseScoreOf(ONE, TWO);

        GameDataModel gameDataModel = gameManager.getGameData();

        List<TeamOutput> actual = gameDataModel.getTeams();
        assertThat(actual).extracting(TeamOutput::getName,
                                      TeamOutput::getPlayerOne,
                                      TeamOutput::getPlayerTwo,
                                      TeamOutput::getScore).containsExactly(
                tuple("T1", "P1", "P2", 1),
                tuple("T2", "P3", "P4", 1));
    }

    @Test
    public void return_empty_model_when_no_teams_are_set_up() {
        GameDataModel actual = gameManager.getGameData();

        List<TeamOutput> teams = actual.getTeams();
        Team matchWinner = actual.getMatchWinner();
        Team winnerOfSet = actual.getWinnerOfSet();
        assertThat(teams).isEmpty();
        assertThat(matchWinner).isEqualTo(NO_TEAM);
        assertThat(winnerOfSet).isEqualTo(NO_TEAM);
    }

    @Test
    public void delete_the_names_and_set_scores_to_zero() {
        setUpTeams();
        raiseScoreOf(ONE, TWO);

        gameManager.resetMatch();

        GameDataModel gameData = gameManager.getGameData();
        List<TeamOutput> actual = gameData.getTeams();
        assertThat(actual).extracting(TeamOutput::getName,
                                      TeamOutput::getPlayerOne,
                                      TeamOutput::getPlayerTwo,
                                      TeamOutput::getScore).containsExactly(
                tuple("", "", "", 0),
                tuple("", "", "", 0));
    }

    @Test
    public void forget_about_shot_and_undid_goals_from_the_past_match() {
        setUpTeams();
        raiseScoreOf(ONE);

        gameManager.resetMatch();

        gameManager.undoGoal();
        GameDataModel gameData = gameManager.getGameData();
        assertThatThereAreNoGoalsForTeam(ONE, gameData);
        assertThatThereAreNoGoalsForTeam(TWO, gameData);
    }

    private void assertThatThereAreNoGoalsForTeam(Team team, GameDataModel gameData) {
        TeamOutput teamOne = gameData.getTeam(team);
        int actual = teamOne.getScore();
        assertThat(actual).isEqualTo(0);
    }

    @Test
    public void set_the_match_winner_when_a_team_won_two_sets() {
        setUpTeams();
        raiseScoreOf(ONE, ONE, ONE, ONE, ONE, ONE);
        gameManager.changeover();
        raiseScoreOf(ONE, ONE, ONE, ONE, ONE, ONE);

        GameDataModel gameData = gameManager.getGameData();
        Team actualMatchWinner = gameData.getMatchWinner();

        assertThat(actualMatchWinner).isEqualTo(ONE);
    }

    @Test
    public void reset_the_scores_to_zero_but_keep_the_names_saved() {
        setUpTeams();
        raiseScoreOf(ONE, TWO);

        gameManager.changeover();

        GameDataModel gameData = gameManager.getGameData();
        List<TeamOutput> teams = gameData.getTeams();
        assertThat(teams).extracting(TeamOutput::getScore).containsExactly(0, 0);
        assertThat(teams).extracting(TeamOutput::getName).containsExactly("T1", "T2");
        assertThat(teams).extracting(TeamOutput::getPlayerOne).containsExactly("P1", "P3");
        assertThat(teams).extracting(TeamOutput::getPlayerTwo).containsExactly("P2", "P4");
    }

    @Test
    public void forget_about_shot_goals_from_the_past_set() {
        setUpTeams();
        raiseScoreOf(ONE);
        gameManager.undoGoal();
        gameManager.changeover();

        gameManager.undoGoal();

        GameDataModel gameData = gameManager.getGameData();
        TeamOutput team = gameData.getTeam(ONE);
        int actual = team.getScore();
        assertThat(actual).isEqualTo(0);
    }

    @Test
    public void forget_about_undid_goals_from_the_past_set() {
        setUpTeams();
        raiseScoreOf(ONE);
        gameManager.undoGoal();
        gameManager.changeover();

        gameManager.redoGoal();

        GameDataModel gameData = gameManager.getGameData();
        TeamOutput team = gameData.getTeam(ONE);
        int actual = team.getScore();
        assertThat(actual).isEqualTo(0);
    }

    @Test
    public void load_all_teams_ignoring_case() {
        setUpTeams();
        teamDataModelOne.setName("Roto");
        teamDataModelTwo.setName("Rototo");

        List<TeamOutput> actual = gameManager.getAllTeamsFromDatabase();

        assertThat(actual).extracting(TeamOutput::getName).containsExactly("Roto", "Rototo");
    }

    @Test
    public void load_nothing_when_there_are_no_teams_starting_with_given_letters() {
        setUpTeams();
        TeamRepositoryFakeTwo teamRepository = new TeamRepositoryFakeTwo(id);
        PlayerRepositoryFake playerRepository = new PlayerRepositoryFake();
        PlayerService playerService = new PlayerService(playerRepository);
        TeamService teamService = new TeamService(teamRepository,
                                                  playerService);
//        gameManager = new GameManager(teamService);
        gameManager.initGame(initDataModel);

        List<TeamOutput> actual = gameManager.getAllTeamsFromDatabase();

        assertThat(actual).isEmpty();
    }

    private void raiseScoreOf(Team... teams) {
        for (Team team : teams) {
            gameManager.countGoalFor(team);
        }
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



