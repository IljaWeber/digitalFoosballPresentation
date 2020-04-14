package com.valtech.digitalFoosball.service.verifier;

import com.valtech.digitalFoosball.exceptions.NameDuplicateException;
import com.valtech.digitalFoosball.model.input.InitDataModel;
import com.valtech.digitalFoosball.model.internal.PlayerDataModel;
import com.valtech.digitalFoosball.model.internal.TeamDataModel;

import java.util.ArrayList;
import java.util.List;

public class UniqueNameVerifier {
    public UniqueNameVerifier() {
    }

    public void checkForDuplicateNames(InitDataModel initDataModel) {
        List<String> playerNames = new ArrayList<>();
        List<String> teamNames = new ArrayList<>();

        for (TeamDataModel team : initDataModel.getTeams()) {

            if (teamNames.contains(team.getName())) {
                throw new NameDuplicateException(team.getName());
            }

            teamNames.add(team.getName());

            for (PlayerDataModel player : team.getPlayers()) {

                if (playerNames.contains(player.getName())) {
                    throw new NameDuplicateException(player.getName());
                }

                playerNames.add(player.getName());
            }
        }
    }
}