package com.valtech.digitalFoosball.domain.ranked;

import com.valtech.digitalFoosball.GameBuilder;
import com.valtech.digitalFoosball.api.driven.persistence.repository.PlayerRepository;
import com.valtech.digitalFoosball.api.driven.persistence.repository.TeamRepository;
import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import com.valtech.digitalFoosball.domain.common.models.PlayerDataModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.valtech.digitalFoosball.domain.common.constants.Team.ONE;
import static com.valtech.digitalFoosball.domain.common.constants.Team.TWO;
import static org.assertj.core.api.Assertions.assertThat;

public class RankedGameManipulatorShouldRedoAnUndoneScore {

    public RankedGame rankedGame;
    private RankedTeamDataModel teamDataModelOne;
    private RankedTeamDataModel teamDataModelTwo;
    private final UUID id = UUID.randomUUID();

    public RankedGameManipulatorShouldRedoAnUndoneScore() {
        TeamRepositoryFake teamRepository = new TeamRepositoryFake(id);
        PlayerRepositoryFake playerRepository = new PlayerRepositoryFake();
        rankedGame = GameBuilder.buildRankedGameWith(teamRepository, playerRepository);
    }

    @BeforeEach
    void setUp() {
        teamDataModelOne = new RankedTeamDataModel("T1", "P1", "P2");
        teamDataModelTwo = new RankedTeamDataModel("T2", "P3", "P4");

        InitDataModel initDataModel = new InitDataModel(teamDataModelOne, teamDataModelTwo);

        rankedGame.initGame(initDataModel);
    }

    @Test
    void if_a_score_has_been_undone_recently() {
        raiseScoreOf(ONE);
        rankedGame.undoGoal();

        rankedGame.redoGoal();

        int actual = getScoreOfTeam(ONE);
        assertThat(actual).isEqualTo(1);
    }

    private int getScoreOfTeam(Team team) {
        RankedGameDataModel gameDataModel = rankedGame.getTeams();
        RankedTeamDataModel teamOne = gameDataModel.getTeam(team);
        return teamOne.getScore();
    }

    @Test
    void only_when_a_goal_was_undid_otherwise_do_nothing() {
        rankedGame.redoGoal();

        int actualScoreTeamOne = getScoreOfTeam(ONE);
        int actualScoreTeamTwo = getScoreOfTeam(TWO);
        assertThat(actualScoreTeamOne).isEqualTo(0);
        assertThat(actualScoreTeamTwo).isEqualTo(0);
    }

    @Test
    void and_raise_the_won_sets_if_necessary() {
        raiseScoreOf(ONE, ONE, ONE, ONE, ONE, ONE);
        rankedGame.undoGoal();

        rankedGame.redoGoal();

        int actual = getNumberOfWonSets(ONE);
        assertThat(actual).isEqualTo(1);
    }

    private int getNumberOfWonSets(Team team) {
        RankedGameDataModel gameDataModel = rankedGame.getTeams();
        RankedTeamDataModel teamOne = gameDataModel.getTeam(team);
        return teamOne.getWonSets();
    }

    private void raiseScoreOf(Team... teams) {
        for (Team team : teams) {
            rankedGame.countGoalFor(team);
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
