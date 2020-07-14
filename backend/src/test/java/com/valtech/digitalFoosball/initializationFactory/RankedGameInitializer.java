package com.valtech.digitalFoosball.initializationFactory;

import com.valtech.digitalFoosball.GameBuilder;
import com.valtech.digitalFoosball.api.driven.persistence.repository.PlayerRepository;
import com.valtech.digitalFoosball.api.driven.persistence.repository.TeamRepository;
import com.valtech.digitalFoosball.domain.common.IPlayAGame;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import com.valtech.digitalFoosball.domain.ranked.RankedGame;
import com.valtech.digitalFoosball.domain.ranked.RankedTeamDataModel;

public class RankedGameInitializer implements GameInitializer {

    private final InitDataModel initDataModel;

    public RankedGameInitializer(RankedTeamDataModel teamOne, RankedTeamDataModel teamTwo) {
        initDataModel = new InitDataModel(teamOne, teamTwo);
    }

    @Override
    public IPlayAGame initializeGame(TeamRepository teamRepository,
                                     PlayerRepository playerRepository) {

        RankedGame rankedGame = GameBuilder.buildRankedGameWith(teamRepository, playerRepository);
        rankedGame.initGame(initDataModel);

        return rankedGame;
    }
}
