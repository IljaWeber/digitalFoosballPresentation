package com.valtech.digitalFoosball.adapter;

import com.valtech.digitalFoosball.domain.common.models.PlayerDataModel;
import com.valtech.digitalFoosball.domain.common.models.TeamDataModel;
import com.valtech.digitalFoosball.domain.ports.RankedGamePersistencePort;
import com.valtech.digitalFoosball.entity.PlayerDataEntity;
import com.valtech.digitalFoosball.entity.TeamDataEntity;
import com.valtech.digitalFoosball.repository.TeamRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TeamService implements RankedGamePersistencePort {

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
        Optional<TeamDataEntity> optionalTeamDataModel = teamRepository.findByNameIgnoreCase(teamDataModel.getName());

        List<PlayerDataEntity> playerDataEntities = translateIntoPlayerEntities(teamDataModel.getPlayers());
        List<PlayerDataEntity> playersFromDatabase = getPlayersFromDatabase(playerDataEntities);

        if (optionalTeamDataModel.isEmpty()) {
            logger.info("{} saved into DB", teamDataModel.toString());
            TeamDataEntity translatedEntity = translateIntoTeamEntity(teamDataModel);
            translatedEntity.setPlayers(playersFromDatabase);
            TeamDataEntity teamDataEntity = teamRepository.save(translatedEntity);

            return translateFromTeamEntity(teamDataEntity);
        }

        TeamDataEntity teamFromDatabase = optionalTeamDataModel.get();

        teamFromDatabase.setPlayers(playersFromDatabase);

        logger.info("{} loaded from DB", teamFromDatabase.toString());

        return translateFromTeamEntity(teamFromDatabase);
    }

    private List<PlayerDataEntity> getPlayersFromDatabase(List<PlayerDataEntity> players) {
        List<PlayerDataEntity> playersFromDatabase = new ArrayList<>();

        for (PlayerDataEntity player : players) {
            PlayerDataEntity playerDataModel = playerService.setUp(player);
            playersFromDatabase.add(playerDataModel);
        }

        return playersFromDatabase;
    }

    @Override
    public List<TeamDataModel> getAllTeamsFromDatabase() {
        List<TeamDataEntity> allEntities = teamRepository.findAll();
        List<TeamDataModel> allTeamDataModels = new ArrayList<>();

        for (TeamDataEntity entity : allEntities) {
            allTeamDataModels.add(translateFromTeamEntity(entity));
        }

        return allTeamDataModels;
    }

    private TeamDataEntity translateIntoTeamEntity(TeamDataModel teamDataModel) {
        TeamDataEntity entity = new TeamDataEntity();
        List<PlayerDataModel> playerEntities = teamDataModel.getPlayers();

        entity.setName(teamDataModel.getName());
        entity.setPlayers(translateIntoPlayerEntities(playerEntities));

        return entity;
    }

    private List<PlayerDataEntity> translateIntoPlayerEntities(List<PlayerDataModel> players) {
        List<PlayerDataEntity> playerDataEntities = new ArrayList<>();

        for (PlayerDataModel player : players) {
            PlayerDataEntity playerDataEntity = new PlayerDataEntity();
            playerDataEntity.setName(player.getName());
            playerDataEntities.add(playerDataEntity);
        }

        return playerDataEntities;
    }

    private TeamDataModel translateFromTeamEntity(TeamDataEntity entity) {
        TeamDataModel teamDataModel = new TeamDataModel();
        List<PlayerDataModel> playerDataEntities = new ArrayList<>();

        for (PlayerDataEntity player : entity.getPlayers()) {
            PlayerDataModel playerDataModel = new PlayerDataModel();

            playerDataModel.setName(player.getName());
            playerDataEntities.add(playerDataModel);
        }
        teamDataModel.setName(entity.getName());
        teamDataModel.setPlayers(playerDataEntities);

        return teamDataModel;
    }

}
