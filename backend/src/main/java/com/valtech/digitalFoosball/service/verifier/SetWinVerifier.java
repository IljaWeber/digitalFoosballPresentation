package com.valtech.digitalFoosball.service.verifier;

import com.valtech.digitalFoosball.constants.Team;
import com.valtech.digitalFoosball.model.internal.TeamDataModel;

import java.util.Map;

public interface SetWinVerifier {
    boolean teamWon(Map<Team, TeamDataModel> teams, Team scoringTeam);
}