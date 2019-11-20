package com.valtech.digitalFoosball.storage;

import com.valtech.digitalFoosball.model.internal.PlayerDataModel;
import com.valtech.digitalFoosball.storage.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public PlayerDataModel setUp(PlayerDataModel playerDataModel) {
        Optional<PlayerDataModel> optionalPlayerDataModel = playerRepository.findByName(playerDataModel.getName());

        if (optionalPlayerDataModel.isEmpty()) {
            return playerRepository.save(playerDataModel);
        }

        return optionalPlayerDataModel.get();
    }
}