package com.valtech.digitalFoosball.domain.ranked;

import com.valtech.digitalFoosball.api.driven.persistence.repository.PlayerRepository;
import com.valtech.digitalFoosball.api.driven.persistence.repository.TeamRepository;
import com.valtech.digitalFoosball.domain.common.IPlayAGame;
import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.models.GameDataModel;
import com.valtech.digitalFoosball.domain.common.models.PlayerDataModel;
import com.valtech.digitalFoosball.domain.common.models.TeamDataModel;
import com.valtech.digitalFoosball.initializationFactory.RankedGameFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static com.valtech.digitalFoosball.domain.common.constants.Team.ONE;
import static com.valtech.digitalFoosball.domain.common.constants.Team.TWO;
import static org.assertj.core.api.Assertions.assertThat;

public class RankedGameManipulatorShouldRedoAnUndoneScore {

    public IPlayAGame game;
    private final UUID id = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        RankedTeamDataModel teamOne = new RankedTeamDataModel("T1", "P1", "P2");
        RankedTeamDataModel teamTwo = new RankedTeamDataModel("T2", "P3", "P4");

        TeamRepository teamRepository = new TeamRepositoryFake(id);
        PlayerRepository playerRepository = new PlayerRepositoryFake();
        RankedGameFactory rankedGame = new RankedGameFactory();
        rankedGame.prepareInitData(teamOne, teamTwo);
        game = rankedGame.getGame(teamRepository, playerRepository);
    }

    @Test
    void if_a_score_has_been_undone_recently() {
        raiseScoreOf(ONE);
        game.undoGoal();

        game.redoGoal();

        int actual = getScoreOfTeam(ONE);
        assertThat(actual).isEqualTo(1);
    }

    private int getScoreOfTeam(Team team) {
        GameDataModel gameData = game.getGameData();
        SortedMap<Team, TeamDataModel> teams = gameData.getTeams();
        TeamDataModel teamDataModel = teams.get(team);
        return teamDataModel.getScore();
    }

    @Test
    void only_when_a_goal_was_undid_otherwise_do_nothing() {
        game.redoGoal();

        int actualScoreTeamOne = getScoreOfTeam(ONE);
        int actualScoreTeamTwo = getScoreOfTeam(TWO);
        assertThat(actualScoreTeamOne).isEqualTo(0);
        assertThat(actualScoreTeamTwo).isEqualTo(0);
    }

    @Test
    void and_raise_the_won_sets_if_necessary() {
        raiseScoreOf(ONE, ONE, ONE, ONE, ONE, ONE);
        game.undoGoal();

        game.redoGoal();

        int actual = getNumberOfWonSets(ONE);
        assertThat(actual).isEqualTo(1);
    }

    private int getNumberOfWonSets(Team team) {
        GameDataModel gameData = game.getGameData();
        SortedMap<Team, TeamDataModel> teams = gameData.getTeams();
        TeamDataModel teamDataModel = teams.get(team);
        return teamDataModel.getWonSets();
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

            return teamDataModels;
        }

        private List<RankedTeamDataModel> teamDataModels;

        public void insertTeamDataModel(RankedTeamDataModel teamOne, RankedTeamDataModel teamTwo) {
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
