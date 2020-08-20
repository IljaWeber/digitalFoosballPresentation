package com.valtech.digitalFoosball.domain.ranked;

import com.valtech.digitalFoosball.api.persistence.PlayerService;
import com.valtech.digitalFoosball.api.persistence.TeamService;
import com.valtech.digitalFoosball.api.persistence.repository.PlayerRepository;
import com.valtech.digitalFoosball.api.persistence.repository.TeamRepository;
import com.valtech.digitalFoosball.domain.common.DigitalFoosballGameRules;
import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import com.valtech.digitalFoosball.domain.common.models.PlayerDataModel;
import com.valtech.digitalFoosball.domain.common.models.TeamDataModel;
import com.valtech.digitalFoosball.domain.common.models.output.game.ClassicGameOutputModel;
import com.valtech.digitalFoosball.domain.common.models.output.game.GameOutputModel;
import com.valtech.digitalFoosball.domain.ranked.service.RankedInitService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.valtech.digitalFoosball.domain.common.constants.Team.*;
import static org.assertj.core.api.Assertions.assertThat;

public class DigitalFoosballGameRulesRulesShouldShow {
    private final UUID id = UUID.randomUUID();
    private DigitalFoosballGameRules rules;

    @BeforeEach
    void setUp() {
        TeamDataModel teamOne = new TeamDataModel("T1", "P1", "P2");
        TeamDataModel teamTwo = new TeamDataModel("T2", "P3", "P4");
        InitDataModel initDataModel = new InitDataModel(teamOne, teamTwo);

        TeamRepository teamRepository = new TeamRepositoryFake(id);
        PlayerRepository playerRepository = new PlayerRepositoryFake();
        rules = new DigitalFoosballGameRules(new RankedInitService(new TeamService(teamRepository,
                                                                                   new PlayerService(playerRepository))));
        rules.initGame(initDataModel);
    }

    @Test
    public void no_set_winner_when_no_team_scored_six_goals() {
        countGoalsFor(TWO,
                      ONE, ONE,
                      TWO,
                      ONE);

        GameOutputModel gameData = rules.getGameData();
        ClassicGameOutputModel outputModel = (ClassicGameOutputModel) gameData;
        Team actual = outputModel.getWinnerOfSet();
        assertThat(actual).isEqualTo(NO_TEAM);
    }

    @Test
    void no_set_winner_when_teams_has_a_score_more_than_six_but_a_difference_of_less_than_two() {
        countGoalsFor(TWO, TWO, TWO,
                      ONE, ONE,
                      TWO,
                      ONE, ONE, ONE,
                      TWO, TWO,
                      ONE,
                      TWO);

        GameOutputModel gameData = rules.getGameData();
        ClassicGameOutputModel outputModel = (ClassicGameOutputModel) gameData;
        Team actual = outputModel.getWinnerOfSet();
        assertThat(actual).isEqualTo(NO_TEAM);
    }

    @Test
    public void a_set_winner_when_a_team_scored_more_than_six_goals_and_there_is_a_score_difference_of_at_least_two() {
        countGoalsFor(TWO, TWO, TWO, TWO, TWO, TWO);

        GameOutputModel gameData = rules.getGameData();
        ClassicGameOutputModel outputModel = (ClassicGameOutputModel) gameData;
        Team actual = outputModel.getWinnerOfSet();
        assertThat(actual).isEqualTo(TWO);
    }

    private void countGoalsFor(Team... teams) {
        for (Team team : teams) {
            rules.countGoalFor(team);
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
}
