package com.valtech.digitalFoosball.domain.game.init;

import com.valtech.digitalFoosball.api.driven.persistence.repository.PlayerRepository;
import com.valtech.digitalFoosball.api.driven.persistence.repository.TeamRepository;
import com.valtech.digitalFoosball.domain.gameModes.InitService;
import com.valtech.digitalFoosball.domain.gameModes.InitServiceProvider;
import com.valtech.digitalFoosball.domain.gameModes.models.TeamOutput;
import com.valtech.digitalFoosball.domain.gameModes.regular.models.PlayerDataModel;
import com.valtech.digitalFoosball.domain.gameModes.regular.models.TeamDataModel;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class InitServiceShould {

    @Test
    void get_all_teams_when_there_are_some() {
        InitServiceProvider provider = InitServiceProviderBuilder.buildWith(new FakeTeamRepository(),
                                                                            new FakePlayerRepository());
        InitService initService = new InitService(provider);

        List<TeamOutput> actual = initService.getAllTeamsFromDatabase();
        String teamOneName = getNameOfTeam(0, actual);
        String teamTwoName = getNameOfTeam(1, actual);

        assertThat(teamOneName).isEqualTo("Green");
        assertThat(teamTwoName).isEqualTo("Blue");
    }

    @Test
    void get_nothing_when_there_are_no_teams() {
        InitServiceProvider provider = InitServiceProviderBuilder.buildWith(new FakeTeamRepositoryTwo(),
                                                                            new FakePlayerRepository());
        InitService initService = new InitService(provider);

        List<TeamOutput> actual = initService.getAllTeamsFromDatabase();

        assertThat(actual).isEmpty();
    }

    private String getNameOfTeam(int index, List<TeamOutput> teams) {
        TeamOutput team = teams.get(index);
        return team.getName();
    }

    private class FakeTeamRepository implements TeamRepository {
        @Override
        public Optional<TeamDataModel> findByNameIgnoreCase(String teamName) {
            return Optional.empty();
        }

        @Override
        public <S extends TeamDataModel> S save(S entity) {
            return null;
        }

        @Override
        public <S extends TeamDataModel> Iterable<S> saveAll(Iterable<S> entities) {
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
        public List<TeamDataModel> findAll() {
            TeamDataModel teamOne = new TeamDataModel("Green", "Hans", "Peter");
            TeamDataModel teamTwo = new TeamDataModel("Blue", "Gisela", "Marianne");
            List<TeamDataModel> teamDataModels = Arrays.asList(teamOne, teamTwo);
            return teamDataModels;
        }

        @Override
        public Iterable<TeamDataModel> findAllById(Iterable<UUID> uuids) {
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
        public void delete(TeamDataModel entity) {

        }

        @Override
        public void deleteAll(Iterable<? extends TeamDataModel> entities) {

        }

        @Override
        public void deleteAll() {

        }
    }

    private class FakePlayerRepository implements PlayerRepository {
        @Override
        public Optional<PlayerDataModel> findByName(String name) {
            return Optional.empty();
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

    private class FakeTeamRepositoryTwo implements TeamRepository {
        @Override
        public Optional<TeamDataModel> findByNameIgnoreCase(String teamName) {
            return Optional.empty();
        }

        @Override
        public <S extends TeamDataModel> S save(S entity) {
            return null;
        }

        @Override
        public <S extends TeamDataModel> Iterable<S> saveAll(Iterable<S> entities) {
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
        public List<TeamDataModel> findAll() {
            return new ArrayList<>();
        }

        @Override
        public Iterable<TeamDataModel> findAllById(Iterable<UUID> uuids) {
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
        public void delete(TeamDataModel entity) {

        }

        @Override
        public void deleteAll(Iterable<? extends TeamDataModel> entities) {

        }

        @Override
        public void deleteAll() {

        }
    }
}
