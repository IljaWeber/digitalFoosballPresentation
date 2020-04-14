package com.valtech.digitalFoosball.service.verifier;

import com.valtech.digitalFoosball.exceptions.NameDuplicateException;
import com.valtech.digitalFoosball.model.input.InitDataModel;
import com.valtech.digitalFoosball.model.internal.PlayerDataModel;
import com.valtech.digitalFoosball.model.internal.TeamDataModel;

import java.util.ArrayList;
import java.util.List;

public class UniqueNameVerifier {

    private static List<String> processedPlayerNames;
    private static List<String> processedTeamNames;

    public static void checkForDuplicateNames(InitDataModel initDataModel) {
        processedPlayerNames = new ArrayList<>();
        processedTeamNames = new ArrayList<>();

        for (TeamDataModel team : initDataModel.getTeams()) {
            String teamName = team.getName();

            checkName(teamName, processedTeamNames);

            processedTeamNames.add(teamName);

            checkPlayerNames(team);
        }
    }

    private static void checkPlayerNames(TeamDataModel team) {
        for (PlayerDataModel player : team.getPlayers()) {
            String playerName = player.getName();

            checkName(playerName, processedPlayerNames);

            processedPlayerNames.add(playerName);
        }
    }

    private static void checkName(String name, List<String> names) {
        if (names.contains(name)) {
            throw new NameDuplicateException(name);
        }
    }
}