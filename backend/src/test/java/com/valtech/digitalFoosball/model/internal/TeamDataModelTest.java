package com.valtech.digitalFoosball.model.internal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TeamDataModelTest {

    private TeamDataModel teamDataModel;

    @BeforeEach
    public void setUp() {
        teamDataModel = new TeamDataModel();
    }
    @Test
    public void increase_whenIncreaseIsCalled_thenIncreaseTheirScoreByOne() {
        teamDataModel.increaseScore();

        int actual = teamDataModel.getScore();
        assertThat(actual).isEqualTo(1);
    }
    @Test
    public void decrease_whenDecreaseIsCalled_thenDecreaseTheirScoreByOne() {
        teamDataModel.increaseScore();

        teamDataModel.decreaseScore();

        int actual = teamDataModel.getScore();
        assertThat(actual).isEqualTo(0);
    }

    @Test
    public void resetValues_whenResetValuesIsCalled_thenSetEmptyNamesAndScoreToZero() {
        teamDataModel.setName("T1");
        teamDataModel.setNameOfPlayerOne("P1");
        teamDataModel.setNameOfPlayerTwo("P2");
        teamDataModel.increaseScore();

        teamDataModel.resetValues();

        assertThat(teamDataModel).extracting(TeamDataModel::getName, TeamDataModel::getNameOfPlayerOne, TeamDataModel::getNameOfPlayerTwo, TeamDataModel::getScore).containsExactly("", "", "", 0);
    }

    @Test
    void resetScore_whenScoreIsReset_thenScoreIsZeroAllOtherValuesStaySame() {
        teamDataModel.setName("T1");
        teamDataModel.setNameOfPlayerOne("P1");
        teamDataModel.setNameOfPlayerTwo("P2");
        teamDataModel.increaseScore();

        teamDataModel.resetScore();

        assertThat(teamDataModel).extracting(TeamDataModel::getName, TeamDataModel::getNameOfPlayerOne, TeamDataModel::getNameOfPlayerTwo, TeamDataModel::getScore).containsExactly("T1", "P1", "P2", 0);
    }
}
