package com.valtech.digitalFoosball.api.driven.persistence;

import com.valtech.digitalFoosball.domain.gameModes.regular.models.team.RankedTeamDataModel;

import java.util.List;

public interface IObtainTeams {
    RankedTeamDataModel loadOrSaveIntoDatabase(RankedTeamDataModel teamDataModel);

    List<RankedTeamDataModel> getAllTeamsFromDatabase();
}
