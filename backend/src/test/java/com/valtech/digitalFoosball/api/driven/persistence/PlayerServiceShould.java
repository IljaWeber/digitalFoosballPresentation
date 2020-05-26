package com.valtech.digitalFoosball.api.driven.persistence;

import com.valtech.digitalFoosball.api.driven.persistence.repository.PlayerRepository;
import com.valtech.digitalFoosball.domain.common.models.PlayerDataModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class PlayerServiceShould {

    private final UUID id = UUID.randomUUID();
    private PlayerDataModel initalDataModel;
    private PlayerRepositoryFake playerRepository;
    private PlayerService playerService;

    @BeforeEach
    public void setUp() {
        playerRepository = new PlayerRepositoryFake();
        initalDataModel = new PlayerDataModel();
        playerService = new PlayerService(playerRepository);
    }

    @Test
    public void load_existing_player_ignoring_case() {
        initalDataModel.setId(id);
        initalDataModel.setName("a");

        PlayerDataModel playerDataModel = playerService.setUp(initalDataModel);

        UUID actual = playerDataModel.getId();
        assertThat(actual).isEqualTo(id);
    }

    @Test
    public void save_player_into_database_when_player_did_not_exist_yet() {
        initalDataModel.setId(id);
        initalDataModel.setName("b");

        PlayerDataModel playerDataModel = playerService.setUp(initalDataModel);

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

            return Optional.of(initalDataModel);
        }
    }
}
