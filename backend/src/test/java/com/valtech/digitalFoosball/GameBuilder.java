package com.valtech.digitalFoosball;

import com.valtech.digitalFoosball.api.driven.persistence.PlayerService;
import com.valtech.digitalFoosball.api.driven.persistence.TeamService;
import com.valtech.digitalFoosball.api.driven.persistence.repository.PlayerRepository;
import com.valtech.digitalFoosball.api.driven.persistence.repository.TeamRepository;
import com.valtech.digitalFoosball.domain.gameModes.regular.adhoc.AdHocGame;
import com.valtech.digitalFoosball.domain.gameModes.regular.adhoc.AdHocInitService;
import com.valtech.digitalFoosball.domain.gameModes.regular.ranked.RankedGame;
import com.valtech.digitalFoosball.domain.gameModes.regular.ranked.RankedInitService;
import com.valtech.digitalFoosball.domain.gameModes.timePlay.TimeGame;

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

    public static TimeGame buildTimeGame() {
        return new TimeGame(null);
    }
}
