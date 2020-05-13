package com.valtech.digitalFoosball.domain.game;

import com.valtech.digitalFoosball.domain.constants.GameMode;
import com.valtech.digitalFoosball.domain.gameModes.manipulators.AbstractGameManipulator;
import com.valtech.digitalFoosball.domain.gameModes.manipulators.GameManipulatorFactory;
import com.valtech.digitalFoosball.domain.gameModes.regular.adhoc.AdHocGameManipulator;
import com.valtech.digitalFoosball.domain.gameModes.regular.ranked.RankedGameManipulator;
import com.valtech.digitalFoosball.domain.gameModes.timePlay.TimeGameManipulator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GameModeHolderShould {

    @Test
    void get_the_ad_hoc_game() {
        AbstractGameManipulator gameManipulator = GameManipulatorFactory.createManipulatorFor(GameMode.AD_HOC);

        assertThat(gameManipulator).isInstanceOf(AdHocGameManipulator.class);
    }

    @Test
    void get_the_ranked_game() {
        AbstractGameManipulator gameManipulator = GameManipulatorFactory.createManipulatorFor(GameMode.RANKED);

        assertThat(gameManipulator).isInstanceOf(RankedGameManipulator.class);
    }

    @Test
    void get_the_time_game() {
        AbstractGameManipulator gameManipulator = GameManipulatorFactory.createManipulatorFor(GameMode.TIME_GAME);

        assertThat(gameManipulator).isInstanceOf(TimeGameManipulator.class);
    }
}
