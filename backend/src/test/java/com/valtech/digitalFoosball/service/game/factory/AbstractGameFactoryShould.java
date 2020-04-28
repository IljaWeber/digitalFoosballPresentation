package com.valtech.digitalFoosball.service.game.factory;

import com.valtech.digitalFoosball.constants.GameMode;
import com.valtech.digitalFoosball.service.game.AdHocGame;
import com.valtech.digitalFoosball.service.game.Game;
import com.valtech.digitalFoosball.service.game.RankedGame;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AbstractGameFactoryShould {

    @Test
    void create_an_ad_hoc_game() {
        AbstractGameFactory factory = AbstractGameFactory.getFactory(GameMode.AD_HOC);
        Game game = factory.createGame();

        assertThat(game).isInstanceOf(AdHocGame.class);
    }

    @Test
    void create_a_ranked_game() {
        AbstractGameFactory factory = AbstractGameFactory.getFactory(GameMode.RANKED);
        Game game = factory.createGame();

        assertThat(game).isInstanceOf(RankedGame.class);
    }
}
