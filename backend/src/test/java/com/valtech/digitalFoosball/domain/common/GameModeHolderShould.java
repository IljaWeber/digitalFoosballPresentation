package com.valtech.digitalFoosball.domain.common;

import com.valtech.digitalFoosball.Application;
import com.valtech.digitalFoosball.api.driver.usercommands.DigitalFoosballUserCommandAPI;
import com.valtech.digitalFoosball.domain.adhoc.AdHocGame;
import com.valtech.digitalFoosball.domain.common.constants.GameMode;
import com.valtech.digitalFoosball.domain.ranked.RankedGame;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = Application.class)
@SpringBootTest(classes = DigitalFoosballUserCommandAPI.class)
public class GameModeHolderShould {

    @Autowired
    GameProvider gameProvider;

    @Test
    void get_the_ad_hoc_game() {
        IPlayAGame gameManipulator = gameProvider.getGameManipulator(GameMode.AD_HOC);

        assertThat(gameManipulator).isInstanceOf(AdHocGame.class);
    }

    @Test
    void get_the_ranked_game() {
        IPlayAGame gameManipulator = gameProvider.getGameManipulator(GameMode.RANKED);

        assertThat(gameManipulator).isInstanceOf(RankedGame.class);
    }
}
