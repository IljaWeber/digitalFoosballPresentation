package com.valtech.digitalFoosball.storage;

import com.valtech.digitalFoosball.model.internal.PlayerDataModel;
import com.valtech.digitalFoosball.model.internal.TeamDataModel;
import com.valtech.digitalFoosball.storage.repository.TeamRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TeamService {
    private TeamRepository teamRepository;
    private PlayerService playerService;
    private Logger logger = LogManager.getLogger(TeamService.class);


    @Autowired
    public TeamService(TeamRepository teamRepository, PlayerService playerService) {
        this.teamRepository = teamRepository;
        this.playerService = playerService;
    }

    public TeamDataModel setUp(TeamDataModel teamDataModel) {
        Optional<TeamDataModel> optionalTeamDataModel = teamRepository.findByNameIgnoreCase(teamDataModel.getName());

        List<PlayerDataModel> playersFromDatabase = new ArrayList<>();

        for (PlayerDataModel player : teamDataModel.getPlayers()) {
            PlayerDataModel playerDataModel = playerService.setUp(player);
            playersFromDatabase.add(playerDataModel);
        }

        teamDataModel.setPlayers(playersFromDatabase);

        if (optionalTeamDataModel.isEmpty()) {
            logger.info("{} saved into DB", teamDataModel.toString());

            return teamRepository.save(teamDataModel);
        }

        logger.info("{} loaded from DB", optionalTeamDataModel.get().toString());

        return optionalTeamDataModel.get();
    }

    public List<TeamDataModel> getAll() {
        return teamRepository.findAll();
    }
}
