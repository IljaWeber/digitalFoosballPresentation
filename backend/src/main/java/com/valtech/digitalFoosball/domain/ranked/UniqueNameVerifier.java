package com.valtech.digitalFoosball.domain.ranked;

import com.valtech.digitalFoosball.domain.common.exceptions.NameDuplicateException;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import com.valtech.digitalFoosball.domain.common.models.PlayerDataModel;

import java.util.ArrayList;
import java.util.List;

public class UniqueNameVerifier {

    private List<String> processedPlayerNames;
    private List<String> processedTeamNames;

    public void checkForDuplicateNames(InitDataModel initDataModel) {
        processedPlayerNames = new ArrayList<>();
        processedTeamNames = new ArrayList<>();

        for (RankedTeamDataModel team : initDataModel.getTeams()) {
            String teamName = team.getName();

            checkName(teamName, processedTeamNames);

            processedTeamNames.add(teamName);

            checkPlayerNames(team);
        }
    }

    private void checkPlayerNames(RankedTeamDataModel team) {
        for (PlayerDataModel player : team.getPlayers()) {
            String playerName = player.getName();

            checkName(playerName, processedPlayerNames);

            processedPlayerNames.add(playerName);
        }
    }

    private void checkName(String name, List<String> names) {
        if (names.contains(name)) {
            throw new NameDuplicateException(name);
        }
    }
}
