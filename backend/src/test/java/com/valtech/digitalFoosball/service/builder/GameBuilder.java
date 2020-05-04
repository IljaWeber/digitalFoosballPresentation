package com.valtech.digitalFoosball.service.builder;

import com.valtech.digitalFoosball.service.game.TeamManager;
import com.valtech.digitalFoosball.service.game.modes.AdHocGameManipulator;
import com.valtech.digitalFoosball.service.game.modes.RankedGameManipulator;
import com.valtech.digitalFoosball.service.game.modes.TimeGameManipulator;
import com.valtech.digitalFoosball.storage.PlayerService;
import com.valtech.digitalFoosball.storage.TeamService;
import com.valtech.digitalFoosball.storage.repository.PlayerRepository;
import com.valtech.digitalFoosball.storage.repository.TeamRepository;

public abstract class GameBuilder {
    public static RankedGameManipulator buildRankedGameWith(TeamRepository teamRepository,
                                                            PlayerRepository playerRepository) {
        PlayerService playerService = new PlayerService(playerRepository);
        TeamService teamService = new TeamService(teamRepository, playerService);
        TeamManager teamManager = new TeamManager(teamService);
        return new RankedGameManipulator(teamManager);
    }

    public static AdHocGameManipulator buildAdHocGameWith(TeamRepository teamRepository,
                                                          PlayerRepository playerRepository) {
        PlayerService playerService = new PlayerService(playerRepository);
        TeamService teamService = new TeamService(teamRepository, playerService);
        TeamManager teamManager = new TeamManager(teamService);
        return new AdHocGameManipulator(teamManager);
    }

    public static TimeGameManipulator buildTimeGame() {
        return new TimeGameManipulator();
    }
}
