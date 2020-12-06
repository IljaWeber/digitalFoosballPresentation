package com.valtech.digitalFoosball.domain.ports;

import com.valtech.digitalFoosball.domain.common.models.TeamDataModel;

import java.util.List;

public interface RankedGamePersistencePort {
    TeamDataModel loadOrSaveIntoDatabase(TeamDataModel teamDataModel);

    List<TeamDataModel> getAllTeamsFromDatabase();
}
