package com.valtech.digitalFoosball.storage;

import com.valtech.digitalFoosball.model.internal.TeamDataModel;

import java.util.List;

public interface IObtainTeams {
    TeamDataModel loadOrSaveIntoDatabase(TeamDataModel teamDataModel);

    List<TeamDataModel> getAllTeamsFromDatabase();
}
