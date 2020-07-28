package com.valtech.digitalFoosball.api.driven.persistence;

import com.valtech.digitalFoosball.api.driven.persistence.repository.TeamRepository;
import com.valtech.digitalFoosball.domain.common.models.PlayerDataModel;
import com.valtech.digitalFoosball.domain.ranked.TeamDataModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TeamService implements IObtainTeams {

    private final TeamRepository teamRepository;
    private final PlayerService playerService;
    private final Logger logger = LogManager.getLogger(TeamService.class);

    @Autowired
    public TeamService(TeamRepository teamRepository, PlayerService playerService) {
        this.teamRepository = teamRepository;
        this.playerService = playerService;
    }

    @Override
    public TeamDataModel loadOrSaveIntoDatabase(TeamDataModel teamDataModel) {
        Optional<TeamDataModel> optionalTeamDataModel = teamRepository.findByNameIgnoreCase(teamDataModel.getName());

        List<PlayerDataModel> unsetPlayers = teamDataModel.getPlayers();
        List<PlayerDataModel> playersFromDatabase = getPlayersFromDatabase(unsetPlayers);

        teamDataModel.setPlayers(playersFromDatabase);

        if (optionalTeamDataModel.isEmpty()) {
            logger.info("{} saved into DB", teamDataModel.toString());

            return teamRepository.save(teamDataModel);
        }

        TeamDataModel teamFromDatabase = optionalTeamDataModel.get();

        teamFromDatabase.setPlayers(playersFromDatabase);

        logger.info("{} loaded from DB", teamFromDatabase.toString());

        return teamFromDatabase;
    }

    private List<PlayerDataModel> getPlayersFromDatabase(List<PlayerDataModel> players) {
        List<PlayerDataModel> playersFromDatabase = new ArrayList<>();

        for (PlayerDataModel player : players) {
            PlayerDataModel playerDataModel = playerService.setUp(player);
            playersFromDatabase.add(playerDataModel);
        }

        return playersFromDatabase;
    }

    @Override
    public List<TeamDataModel> getAllTeamsFromDatabase() {
        return teamRepository.findAll();
    }
}
