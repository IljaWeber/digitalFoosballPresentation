package com.valtech.digitalFoosball.domain.game;

import com.valtech.digitalFoosball.api.driven.notification.INotifyAboutStateChanges;
import com.valtech.digitalFoosball.api.driven.persistence.PlayerService;
import com.valtech.digitalFoosball.api.driven.persistence.TeamService;
import com.valtech.digitalFoosball.api.driven.persistence.repository.PlayerRepository;
import com.valtech.digitalFoosball.api.driven.persistence.repository.TeamRepository;
import com.valtech.digitalFoosball.domain.GameController;
import com.valtech.digitalFoosball.domain.constants.Team;
import com.valtech.digitalFoosball.domain.gameModes.InitService;
import com.valtech.digitalFoosball.domain.gameModes.InitServiceProvider;
import com.valtech.digitalFoosball.domain.gameModes.models.GameOutputModel;
import com.valtech.digitalFoosball.domain.gameModes.models.InitDataModel;
import com.valtech.digitalFoosball.domain.gameModes.models.TeamOutput;
import com.valtech.digitalFoosball.domain.gameModes.regular.adhoc.AdHocInitService;
import com.valtech.digitalFoosball.domain.gameModes.regular.models.PlayerDataModel;
import com.valtech.digitalFoosball.domain.gameModes.regular.models.TeamDataModel;
import com.valtech.digitalFoosball.domain.gameModes.regular.ranked.RankedInitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.valtech.digitalFoosball.domain.constants.GameMode.RANKED;
import static com.valtech.digitalFoosball.domain.constants.Team.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

class GameControllerShould {

    public GameController game;

    protected InitDataModel initDataModel;
    private TeamDataModel teamDataModelOne;
    private TeamDataModel teamDataModelTwo;
    private final UUID id = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        FakeClientUpdater clientUpdater = new FakeClientUpdater();
        InitService initService = createInitService();
        game = new GameController(clientUpdater, initService);
    }

    private InitService createInitService() {
        TeamService teamDataPort = createTeamServiceWithFakes();
        AdHocInitService adHocInitService = new AdHocInitService(teamDataPort);
        RankedInitService rankedInitService = new RankedInitService(teamDataPort);
        InitServiceProvider provider = new InitServiceProvider(rankedInitService,
                                                               adHocInitService);
        return new InitService(provider);
    }

    private TeamService createTeamServiceWithFakes() {
        TeamRepositoryFake teamRepository = new TeamRepositoryFake(id);
        PlayerRepositoryFake playerRepository = new PlayerRepositoryFake();
        PlayerService playerService = new PlayerService(playerRepository);
        return new TeamService(teamRepository,
                               playerService);
    }

    @Test
    public void sum_up_all_relevant_game_data_in_a_gameDataModel() {
        setUpTeams();
        raiseScoreOf(ONE, TWO);

        GameOutputModel gameOutputModel = game.getGameData();

        List<TeamOutput> actual = gameOutputModel.getTeams();
        assertThat(actual).extracting(TeamOutput::getName,
                                      TeamOutput::getPlayerOne,
                                      TeamOutput::getPlayerTwo,
                                      TeamOutput::getScore).containsExactly(
                tuple("T1", "P1", "P2", 1),
                tuple("T2", "P3", "P4", 1));
    }

    private void setUpTeams() {
        teamDataModelOne = new TeamDataModel("T1", "P1", "P2");
        teamDataModelTwo = new TeamDataModel("T2", "P3", "P4");
        initDataModel = new InitDataModel(teamDataModelOne, teamDataModelTwo);
        initDataModel.setMode(RANKED);
        game.initGame(initDataModel);
    }

    @Test
    public void return_empty_model_when_no_teams_are_set_up() {
        GameOutputModel actual = game.getGameData();

        List<TeamOutput> teams = actual.getTeams();
        Team matchWinner = actual.getMatchWinner();
        Team winnerOfSet = actual.getWinnerOfSet();
        assertThat(teams).isEmpty();
        assertThat(matchWinner).isEqualTo(NO_TEAM);
        assertThat(winnerOfSet).isEqualTo(NO_TEAM);
    }

    @Test
    public void delete_all_values_of_the_past_game() {
        setUpTeams();
        raiseScoreOf(ONE, TWO);

        game.resetMatch();

        GameOutputModel gameData = game.getGameData();
        Team winnerOfSet = gameData.getWinnerOfSet();
        Team matchWinner = gameData.getMatchWinner();
        List<TeamOutput> teams = gameData.getTeams();
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
        List<TeamOutput> teams = gameData.getTeams();
        assertThat(teams).extracting(TeamOutput::getScore).containsExactly(0, 0);
        assertThat(teams).extracting(TeamOutput::getName).containsExactly("T1", "T2");
        assertThat(teams).extracting(TeamOutput::getPlayerOne).containsExactly("P1", "P3");
        assertThat(teams).extracting(TeamOutput::getPlayerTwo).containsExactly("P2", "P4");
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

    private class FakeClientUpdater implements INotifyAboutStateChanges {

        @Override
        public void notifyAboutStateChange(GameOutputModel gameData) {
        }

        @Override
        public void update(GameOutputModel gameOutputModel) {

        }
    }
}
