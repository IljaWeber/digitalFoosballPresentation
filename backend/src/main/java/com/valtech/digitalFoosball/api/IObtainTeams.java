package com.valtech.digitalFoosball.api;

import com.valtech.digitalFoosball.domain.common.models.TeamDataModel;

import java.util.List;

public interface IObtainTeams {
    TeamDataModel loadOrSaveIntoDatabase(TeamDataModel teamDataModel);

    List<TeamDataModel> getAllTeamsFromDatabase();
}
