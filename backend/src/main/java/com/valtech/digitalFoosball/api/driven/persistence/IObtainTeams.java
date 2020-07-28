package com.valtech.digitalFoosball.api.driven.persistence;

import com.valtech.digitalFoosball.domain.common.models.TeamDataModel;

import java.util.List;

public interface IObtainTeams {
    TeamDataModel loadOrSaveIntoDatabase(TeamDataModel teamDataModel);

    List<TeamDataModel> getAllTeamsFromDatabase();
}
