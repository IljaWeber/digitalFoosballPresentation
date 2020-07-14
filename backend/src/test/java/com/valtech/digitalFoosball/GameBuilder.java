package com.valtech.digitalFoosball;

import com.valtech.digitalFoosball.api.driven.persistence.PlayerService;
import com.valtech.digitalFoosball.api.driven.persistence.TeamService;
import com.valtech.digitalFoosball.api.driven.persistence.repository.PlayerRepository;
import com.valtech.digitalFoosball.api.driven.persistence.repository.TeamRepository;
import com.valtech.digitalFoosball.domain.adhoc.AdHocGame;
import com.valtech.digitalFoosball.domain.adhoc.AdHocInitService;
import com.valtech.digitalFoosball.domain.ranked.RankedGame;
import com.valtech.digitalFoosball.domain.ranked.RankedInitService;

public abstract class GameBuilder {
    public static RankedGame buildRankedGameWith(TeamRepository teamRepository,
                                                 PlayerRepository playerRepository) {
        PlayerService playerService = new PlayerService(playerRepository);
        TeamService teamService = new TeamService(teamRepository, playerService);
        RankedInitService initService = new RankedInitService(teamService);
        return new RankedGame(initService);
    }

    public static AdHocGame buildAdHocGameWith(TeamRepository teamRepository,
                                               PlayerRepository playerRepository) {
        PlayerService playerService = new PlayerService(playerRepository);
        TeamService teamService = new TeamService(teamRepository, playerService);
        AdHocInitService initService = new AdHocInitService(teamService);
        return new AdHocGame(initService);
    }
}
