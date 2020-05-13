package com.valtech.digitalFoosball.domain.game.init;

import com.valtech.digitalFoosball.api.driven.persistence.PlayerService;
import com.valtech.digitalFoosball.api.driven.persistence.TeamService;
import com.valtech.digitalFoosball.api.driven.persistence.repository.PlayerRepository;
import com.valtech.digitalFoosball.api.driven.persistence.repository.TeamRepository;
import com.valtech.digitalFoosball.domain.gameModes.InitServiceProvider;
import com.valtech.digitalFoosball.domain.gameModes.regular.adhoc.AdHocInitService;
import com.valtech.digitalFoosball.domain.gameModes.regular.ranked.RankedInitService;

public class InitServiceProviderBuilder {
    private static RankedInitService buildRankedInitServiceWith(TeamRepository teamRepository,
                                                                PlayerRepository playerRepository) {
        TeamService teamDataPort = getTeamService(teamRepository, playerRepository);
        return new RankedInitService(teamDataPort);
    }

    private static TeamService getTeamService(TeamRepository teamRepository, PlayerRepository playerRepository) {
        PlayerService playerService = new PlayerService(playerRepository);
        return new TeamService(teamRepository, playerService);
    }

    private static AdHocInitService buildAdHocInitServiceWith(TeamRepository teamRepository,
                                                              PlayerRepository playerRepository) {
        TeamService teamDataPort = getTeamService(teamRepository, playerRepository);
        return new AdHocInitService(teamDataPort);
    }

    public static InitServiceProvider buildWith(TeamRepository teamRepository, PlayerRepository playerRepository) {
        RankedInitService rankedInitService = buildRankedInitServiceWith(teamRepository, playerRepository);
        AdHocInitService adHocInitService = buildAdHocInitServiceWith(teamRepository, playerRepository);
        return new InitServiceProvider(rankedInitService, adHocInitService);
    }
}
