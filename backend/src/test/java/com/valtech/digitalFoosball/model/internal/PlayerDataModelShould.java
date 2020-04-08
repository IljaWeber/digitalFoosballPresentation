package com.valtech.digitalFoosball.model.internal;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PlayerDataModelShould {
    @Test
    public void have_an_empty_string_as_name_after_reset() {
        PlayerDataModel playerDataModel = new PlayerDataModel();
        playerDataModel.setName("P1");

        playerDataModel.resetValues();

        assertThat(playerDataModel.getName()).isEqualTo("");
    }
}
