package com.valtech.digitalFoosball.models;

import com.valtech.digitalFoosball.model.internal.PlayerDataModel;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PlayerDataModelTest {
    @Test
    public void resetValues_whenResetValuesIsCalled_thenSetEmptyName() {
        PlayerDataModel playerDataModel = new PlayerDataModel();
        playerDataModel.setName("P1");

        playerDataModel.resetValues();

        assertThat(playerDataModel.getName()).isEqualTo("");
    }
}
