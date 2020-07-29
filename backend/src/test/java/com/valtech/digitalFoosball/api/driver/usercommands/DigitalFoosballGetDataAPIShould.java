package com.valtech.digitalFoosball.api.driver.usercommands;

import com.valtech.digitalFoosball.api.driven.persistence.IObtainTeams;
import com.valtech.digitalFoosball.domain.common.models.TeamDataModel;
import com.valtech.digitalFoosball.domain.common.models.output.team.TeamOutputModel;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DigitalFoosballGetDataAPIShould {

    @Test
    public void load_all_teams_ignoring_case() {
        DigitalFoosballGetDataAPI getDataAPI = new DigitalFoosballGetDataAPI(null, new FakeDataPort());

        List<TeamOutputModel> actual = getDataAPI.getAllTeams();

        assertThat(actual).extracting(TeamOutputModel::getName).containsExactly("Roto", "Rototo");
    }

    @Test
    public void load_nothing_when_there_are_no_teams_starting_with_given_letters() {
        DigitalFoosballGetDataAPI getDataAPI = new DigitalFoosballGetDataAPI(null, new DummyDataPort());

        List<TeamOutputModel> actual = getDataAPI.getAllTeams();

        assertThat(actual).isEmpty();
    }

    private class FakeDataPort implements IObtainTeams {
        @Override
        public TeamDataModel loadOrSaveIntoDatabase(TeamDataModel teamDataModel) {
            return null;
        }

        @Override
        public List<TeamDataModel> getAllTeamsFromDatabase() {
            TeamDataModel teamOne = new TeamDataModel();
            teamOne.setName("Roto");
            TeamDataModel teamTwo = new TeamDataModel();
            teamTwo.setName("Rototo");
            return Arrays.asList(teamOne, teamTwo);
        }
    }

    private class DummyDataPort implements IObtainTeams {
        @Override
        public TeamDataModel loadOrSaveIntoDatabase(TeamDataModel teamDataModel) {
            return null;
        }

        @Override
        public List<TeamDataModel> getAllTeamsFromDatabase() {
            return new ArrayList<>();
        }
    }
}
