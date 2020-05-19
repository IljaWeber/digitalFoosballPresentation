package com.valtech.digitalFoosball.domain.game;

import com.valtech.digitalFoosball.Application;
import com.valtech.digitalFoosball.api.driver.usercommands.DigitalFoosballRestAPI;
import com.valtech.digitalFoosball.domain.constants.GameMode;
import com.valtech.digitalFoosball.domain.gameModes.manipulators.AbstractGameManipulator;
import com.valtech.digitalFoosball.domain.gameModes.manipulators.GameManipulatorProvider;
import com.valtech.digitalFoosball.domain.gameModes.regular.adhoc.AdHocGameManipulator;
import com.valtech.digitalFoosball.domain.gameModes.regular.ranked.RankedGameManipulator;
import com.valtech.digitalFoosball.domain.gameModes.timePlay.TimeGameManipulator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = Application.class)
@SpringBootTest(classes = DigitalFoosballRestAPI.class)
public class GameModeHolderShould {

    @Autowired
    GameManipulatorProvider gameManipulatorProvider;

    @Test
    void get_the_ad_hoc_game() {
        AbstractGameManipulator gameManipulator = gameManipulatorProvider.getGameManipulator(GameMode.AD_HOC);

        assertThat(gameManipulator).isInstanceOf(AdHocGameManipulator.class);
    }

    @Test
    void get_the_ranked_game() {
        AbstractGameManipulator gameManipulator = gameManipulatorProvider.getGameManipulator(GameMode.RANKED);

        assertThat(gameManipulator).isInstanceOf(RankedGameManipulator.class);
    }

    @Test
    void get_the_time_game() {
        AbstractGameManipulator gameManipulator = gameManipulatorProvider.getGameManipulator(GameMode.TIME_GAME);

        assertThat(gameManipulator).isInstanceOf(TimeGameManipulator.class);
    }
}
