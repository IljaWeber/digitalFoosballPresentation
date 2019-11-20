package com.valtech.digitalFoosball.storage;

import com.valtech.digitalFoosball.model.internal.PlayerDataModel;
import com.valtech.digitalFoosball.storage.repository.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class PlayerServiceTest {

    private final UUID id = UUID.randomUUID();
    private PlayerDataModel data;
    private PlayerRepositoryFake playerRepository;
    private PlayerService playerService;

    @BeforeEach
    public void setUp() {
        playerRepository = new PlayerRepositoryFake();
        data = new PlayerDataModel();
        playerService = new PlayerService(playerRepository);
    }

    @Test
    public void setUp_whenSearchingForExistingPlayer_thenLoadThisPlayer() {
        data.setId(id);
        data.setName("a");

        PlayerDataModel playerDataModel = playerService.setUp(data);

        assertThat(playerDataModel).extracting(PlayerDataModel::getId, PlayerDataModel::getName).containsExactly(id, "a");
    }

    @Test
    public void setUp_whenSearchingForNotExistingPlayer_thenCreateAndLoadThisPlayer() {
        data.setId(id);
        data.setName("b");

        PlayerDataModel playerDataModel = playerService.setUp(data);

        assertThat(playerDataModel).extracting(PlayerDataModel::getId, PlayerDataModel::getName).containsExactly(id, "b");
    }

    private class PlayerRepositoryFake implements PlayerRepository {


        @Override
        public PlayerDataModel save(PlayerDataModel s) {
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

        @Override
        public Optional<PlayerDataModel> findByName(String name) {
            if (!name.equals("a")) {
                return Optional.empty();
            }

            return Optional.of(data);
        }
    }
}
