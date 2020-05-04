package com.valtech.digitalFoosball.service.game;

import com.valtech.digitalFoosball.Application;
import com.valtech.digitalFoosball.api.DigitalFoosballAPI;
import com.valtech.digitalFoosball.constants.GameMode;
import com.valtech.digitalFoosball.service.game.modes.AdHocGame;
import com.valtech.digitalFoosball.service.game.modes.Game;
import com.valtech.digitalFoosball.service.game.modes.RankedGame;
import com.valtech.digitalFoosball.service.game.modes.TimeGame;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = Application.class)
@SpringBootTest(classes = DigitalFoosballAPI.class)
public class GameModeHolderShould {

    @Autowired
    GameModeHolder gameModeHolder;

    @Test
    void get_the_ad_hoc_game() {
        Game game = gameModeHolder.getGame(GameMode.AD_HOC);

        assertThat(game).isInstanceOf(AdHocGame.class);
    }

    @Test
    void get_the_ranked_game() {
        Game game = gameModeHolder.getGame(GameMode.RANKED);

        assertThat(game).isInstanceOf(RankedGame.class);
    }

    @Test
    void get_the_time_game() {
        Game game = gameModeHolder.getGame(GameMode.TIME_GAME);

        assertThat(game).isInstanceOf(TimeGame.class);
    }
}
