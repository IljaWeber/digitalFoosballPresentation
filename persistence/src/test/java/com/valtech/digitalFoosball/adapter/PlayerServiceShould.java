package com.valtech.digitalFoosball.adapter;

import com.valtech.digitalFoosball.entity.PlayerDataEntity;
import com.valtech.digitalFoosball.repository.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class PlayerServiceShould {

    private final UUID id = UUID.randomUUID();
    private PlayerDataEntity initalDataModel;
    private PlayerRepositoryFake playerRepository;
    private PlayerService playerService;

    @BeforeEach
    public void setUp() {
        playerRepository = new PlayerRepositoryFake();
        initalDataModel = new PlayerDataEntity();
        playerService = new PlayerService(playerRepository);
    }

    @Test
    public void load_existing_player_ignoring_case() {
        initalDataModel.setId(id);
        initalDataModel.setName("a");

        PlayerDataEntity playerDataModel = playerService.setUp(initalDataModel);

        UUID actual = playerDataModel.getId();
        assertThat(actual).isEqualTo(id);
    }

    @Test
    public void save_player_into_database_when_player_did_not_exist_yet() {
        initalDataModel.setId(id);
        initalDataModel.setName("b");

        PlayerDataEntity playerDataModel = playerService.setUp(initalDataModel);

        assertThat(playerDataModel).extracting(PlayerDataEntity::getId, PlayerDataEntity::getName)
                                   .containsExactly(id, "b");
    }

    private class PlayerRepositoryFake implements PlayerRepository {

        @Override
        public Optional<PlayerDataEntity> findByName(String name) {
            return Optional.empty();
        }

        public PlayerDataEntity save(PlayerDataEntity s) {
            s.setId(id);

            return s;
        }

        @Override
        public <S extends PlayerDataEntity> Iterable<S> saveAll(Iterable<S> iterable) {
            return null;
        }

        @Override
        public Optional<PlayerDataEntity> findById(UUID uuid) {

            return Optional.empty();
        }

        @Override
        public boolean existsById(UUID uuid) {
            return false;
        }

        @Override
        public Iterable<PlayerDataEntity> findAll() {
            return null;
        }

        @Override
        public Iterable<PlayerDataEntity> findAllById(Iterable<UUID> iterable) {
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
        public void delete(PlayerDataEntity playerDataModel) {

        }

        @Override
        public void deleteAll(Iterable<? extends PlayerDataEntity> iterable) {

        }

        @Override
        public void deleteAll() {

        }
    }

}
