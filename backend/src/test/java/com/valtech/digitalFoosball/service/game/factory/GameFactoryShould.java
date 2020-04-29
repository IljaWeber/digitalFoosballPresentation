package com.valtech.digitalFoosball.service.game.factory;

import com.valtech.digitalFoosball.Application;
import com.valtech.digitalFoosball.api.DigitalFoosballAPI;
import com.valtech.digitalFoosball.constants.GameMode;
import com.valtech.digitalFoosball.service.game.AdHocGame;
import com.valtech.digitalFoosball.service.game.Game;
import com.valtech.digitalFoosball.service.game.RankedGame;
import com.valtech.digitalFoosball.service.game.TimeGame;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

@ContextConfiguration(classes = Application.class)
@SpringBootTest(classes = DigitalFoosballAPI.class)
public class GameFactoryShould {

    @Autowired
    GameFactory gameFactory;

    @Test
    void create_an_ad_hoc_game() {
        Game game = gameFactory.getGame(GameMode.AD_HOC);

        assertThat(game).isInstanceOf(AdHocGame.class);
    }

    @Test
    void create_a_ranked_game() {
        Game game = gameFactory.getGame(GameMode.RANKED);

        assertThat(game).isInstanceOf(RankedGame.class);
    }

    @Test
    void create_a_time_game() {
        Game game = gameFactory.getGame(GameMode.TIME_GAME);

        assertThat(game).isInstanceOf(TimeGame.class);
    }
}
