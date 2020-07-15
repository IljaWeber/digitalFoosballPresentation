package com.valtech.digitalFoosball.domain.ranked;

import com.valtech.digitalFoosball.api.driven.persistence.repository.PlayerRepository;
import com.valtech.digitalFoosball.api.driven.persistence.repository.TeamRepository;
import com.valtech.digitalFoosball.domain.common.IPlayAGame;
import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.models.PlayerDataModel;
import com.valtech.digitalFoosball.initializationFactory.RankedGameFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static com.valtech.digitalFoosball.domain.common.constants.Team.ONE;
import static com.valtech.digitalFoosball.domain.common.constants.Team.TWO;
import static org.assertj.core.api.Assertions.assertThat;

public class RankedGameManipulatorShouldRaiseScore {

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
    void in_the_order_of_scoring() {
        raiseScoreOf(ONE, ONE, ONE, TWO);

        int scoreOfTeamOne = getScoreOfTeam(ONE);
        int scoreOfTeamTwo = getScoreOfTeam(TWO);
        assertThat(scoreOfTeamOne).isEqualTo(3);
        assertThat(scoreOfTeamTwo).isEqualTo(1);
    }

    private int getScoreOfTeam(Team team) {
        RankedGameDataModel gameData = game.getGameData();
        SortedMap<Team, RankedTeamDataModel> teams = gameData.getTeams();

        return teams.get(team).getScore();
    }

    @Test
    void only_until_the_win_condition_is_fulfilled() {
        raiseScoreOf(ONE, ONE, ONE, ONE, ONE, ONE, ONE);

        int actual = getScoreOfTeam(ONE);
        assertThat(actual).isEqualTo(6);
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
}
