package com.valtech.digitalFoosball.domain.common;

import com.valtech.digitalFoosball.Application;
import com.valtech.digitalFoosball.api.driver.usercommands.DigitalFoosballUserCommandAPI;
import com.valtech.digitalFoosball.domain.adhoc.AdHocGame;
import com.valtech.digitalFoosball.domain.common.constants.GameMode;
import com.valtech.digitalFoosball.domain.ranked.RankedGame;
import com.valtech.digitalFoosball.domain.timePlay.TimeGame;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = Application.class)
@SpringBootTest(classes = DigitalFoosballUserCommandAPI.class)
public class GameModeHolderShould {

    @Autowired
    GameManipulatorProvider gameManipulatorProvider;

    @Test
    void get_the_ad_hoc_game() {
        IPlayAGame gameManipulator = gameManipulatorProvider.getGameManipulator(GameMode.AD_HOC);

        assertThat(gameManipulator).isInstanceOf(AdHocGame.class);
    }

    @Test
    void get_the_ranked_game() {
        IPlayAGame gameManipulator = gameManipulatorProvider.getGameManipulator(GameMode.RANKED);

        assertThat(gameManipulator).isInstanceOf(RankedGame.class);
    }

    @Test
    void get_the_time_game() {
        IPlayAGame gameManipulator = gameManipulatorProvider.getGameManipulator(GameMode.TIME_GAME);

        assertThat(gameManipulator).isInstanceOf(TimeGame.class);
    }
}