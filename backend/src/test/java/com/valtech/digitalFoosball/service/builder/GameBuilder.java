package com.valtech.digitalFoosball.service.builder;

import com.valtech.digitalFoosball.service.game.init.AdHocInitService;
import com.valtech.digitalFoosball.service.game.init.RankedInitService;
import com.valtech.digitalFoosball.service.game.manipulator.AdHocGameManipulator;
import com.valtech.digitalFoosball.service.game.manipulator.RankedGameManipulator;
import com.valtech.digitalFoosball.service.game.manipulator.TimeGameManipulator;
import com.valtech.digitalFoosball.storage.PlayerService;
import com.valtech.digitalFoosball.storage.TeamService;
import com.valtech.digitalFoosball.storage.repository.PlayerRepository;
import com.valtech.digitalFoosball.storage.repository.TeamRepository;

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
