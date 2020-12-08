package com.valtech.digitalFoosball.domain.usecases.ranked.service;

import com.valtech.digitalFoosball.domain.common.exceptions.NameDuplicateException;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import com.valtech.digitalFoosball.domain.common.models.PlayerDataModel;
import com.valtech.digitalFoosball.domain.common.models.TeamDataModel;

import java.util.ArrayList;
import java.util.List;

public class UniqueNameVerifier {
    private List<String> processedPlayerNames;

    public void checkForDuplicateNames(InitDataModel initDataModel) {
        processedPlayerNames = new ArrayList<>();
        List<String> processedTeamNames = new ArrayList<>();

        for (TeamDataModel team : initDataModel.getTeams()) {
            String teamName = team.getName();

            checkName(teamName, processedTeamNames);

            processedTeamNames.add(teamName);

            checkPlayerNames(team);
        }
    }

    private void checkPlayerNames(TeamDataModel team) {
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
