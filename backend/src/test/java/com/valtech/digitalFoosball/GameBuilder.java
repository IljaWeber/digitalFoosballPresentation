package com.valtech.digitalFoosball;

import com.valtech.digitalFoosball.api.driven.persistence.PlayerService;
import com.valtech.digitalFoosball.api.driven.persistence.TeamService;
import com.valtech.digitalFoosball.api.driven.persistence.repository.PlayerRepository;
import com.valtech.digitalFoosball.api.driven.persistence.repository.TeamRepository;
import com.valtech.digitalFoosball.domain.gameModes.regular.adhoc.AdHocGameManipulator;
import com.valtech.digitalFoosball.domain.gameModes.regular.adhoc.AdHocInitService;
import com.valtech.digitalFoosball.domain.gameModes.regular.ranked.RankedGameManipulator;
import com.valtech.digitalFoosball.domain.gameModes.regular.ranked.RankedInitService;
import com.valtech.digitalFoosball.domain.gameModes.timePlay.TimeGameManipulator;

public abstract class GameBuilder {
    public static RankedGameManipulator buildRankedGameWith(TeamRepository teamRepository,
                                                            PlayerRepository playerRepository) {
        PlayerService playerService = new PlayerService(playerRepository);
        TeamService teamService = new TeamService(teamRepository, playerService);
        RankedInitService initService = new RankedInitService(teamService);
        return new RankedGameManipulator(initService);
    }

    public static AdHocGameManipulator buildAdHocGameWith(TeamRepository teamRepository,
                                                          PlayerRepository playerRepository) {
        PlayerService playerService = new PlayerService(playerRepository);
        TeamService teamService = new TeamService(teamRepository, playerService);
        AdHocInitService initService = new AdHocInitService(teamService);
        return new AdHocGameManipulator(initService);
    }

    public static TimeGameManipulator buildTimeGame() {
        return new TimeGameManipulator(null);
    }
}
