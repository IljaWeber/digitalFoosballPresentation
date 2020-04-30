package com.valtech.digitalFoosball.service.builder;

import com.valtech.digitalFoosball.service.game.AdHocGame;
import com.valtech.digitalFoosball.service.game.RankedGame;
import com.valtech.digitalFoosball.service.game.TeamManager;
import com.valtech.digitalFoosball.service.game.TimeGame;
import com.valtech.digitalFoosball.storage.PlayerService;
import com.valtech.digitalFoosball.storage.TeamService;
import com.valtech.digitalFoosball.storage.repository.PlayerRepository;
import com.valtech.digitalFoosball.storage.repository.TeamRepository;

public abstract class GameBuilder {
    public static RankedGame buildRankedGameWith(TeamRepository teamRepository, PlayerRepository playerRepository) {
        PlayerService playerService = new PlayerService(playerRepository);
        TeamService teamService = new TeamService(teamRepository, playerService);
        TeamManager teamManager = new TeamManager(teamService);
        return new RankedGame(teamManager);
    }

    public static AdHocGame buildAdHocGameWith(TeamRepository teamRepository, PlayerRepository playerRepository) {
        PlayerService playerService = new PlayerService(playerRepository);
        TeamService teamService = new TeamService(teamRepository, playerService);
        TeamManager teamManager = new TeamManager(teamService);
        return new AdHocGame(teamManager);
    }

    public static TimeGame buildTimeGame() {
        return new TimeGame();
    }
}
