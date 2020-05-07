package com.valtech.digitalFoosball.service.verifier.setwin;

import com.valtech.digitalFoosball.constants.Team;
import com.valtech.digitalFoosball.model.GameDataModel;
import com.valtech.digitalFoosball.model.internal.TeamDataModel;

import java.util.Map;

public interface WonSetVerifier {
    boolean teamWon(Map<Team, TeamDataModel> teams, Team scoringTeam);

    boolean teamWon(GameDataModel teams);
}
