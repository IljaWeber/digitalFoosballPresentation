package com.valtech.digitalFoosball.initializationFactory;

import com.valtech.digitalFoosball.api.driven.persistence.repository.PlayerRepository;
import com.valtech.digitalFoosball.api.driven.persistence.repository.TeamRepository;
import com.valtech.digitalFoosball.domain.common.IPlayAGame;

public interface GameInitializer {

    IPlayAGame initializeGame(TeamRepository teamRepository,
                              PlayerRepository playerRepository);
}
