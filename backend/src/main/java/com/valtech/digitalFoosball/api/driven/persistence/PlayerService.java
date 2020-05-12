package com.valtech.digitalFoosball.api.driven.persistence;

import com.valtech.digitalFoosball.api.driven.persistence.repository.PlayerRepository;
import com.valtech.digitalFoosball.domain.gameModes.regular.models.PlayerDataModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private Logger logger = LogManager.getLogger(PlayerService.class);


    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public PlayerDataModel setUp(PlayerDataModel playerDataModel) {
        Optional<PlayerDataModel> optionalPlayerDataModel = playerRepository.findByName(playerDataModel.getName());

        if (optionalPlayerDataModel.isEmpty()) {
            logger.info("{} saved into DB", playerDataModel.toString());

            return playerRepository.save(playerDataModel);
        }

        logger.info("{} loaded from DB", optionalPlayerDataModel.get().toString());

        return optionalPlayerDataModel.get();
    }
}
