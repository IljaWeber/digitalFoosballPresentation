package com.valtech.digitalFoosball.api.driver.sensorcommands;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.ranked.RankedGame;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RaspiControllerShould {

    @Test
    void raise_score_in_the_given_game() {
        RaspiController raspiController = new RaspiController();

        FakeRankedGame game = new FakeRankedGame();
        raspiController.setGame(game);
        raspiController.raiseScore(1);

        assertThat(game.scoreRaised).isTrue();
    }

    private class FakeRankedGame extends RankedGame {

        public boolean scoreRaised = false;

        public FakeRankedGame() {
            super(null);
        }

        @Override
        public void countGoalFor(Team team) {
            scoreRaised = true;
        }
    }
}