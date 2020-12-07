package com.valtech.digitalFoosball.adapter;

import com.valtech.digitalFoosball.entity.PlayerDataEntity;
import com.valtech.digitalFoosball.repository.PlayerRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final Logger logger = LogManager.getLogger(PlayerService.class);

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public PlayerDataEntity setUp(PlayerDataEntity playerDataEntity) {
        Optional<PlayerDataEntity> players = playerRepository.findByName(playerDataEntity.getName());
        if (players.isEmpty()) {
            logger.info("{} saved into DB", playerDataEntity.toString());

            return playerRepository.save(playerDataEntity);
        }

        logger.info("{} loaded from DB", players.get().toString());

        return players.get();
    }
}
