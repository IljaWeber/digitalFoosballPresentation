package com.valtech.digitalFoosball.domain.timeGame;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MyTimerTaskShould {

    @Test
    void inform_the_rules_when_time_ran_down() {
        FakeTimeGame game = new FakeTimeGame();
        MyTimerTask myTimerTask = new MyTimerTask(game);

        myTimerTask.run();

        assertThat(game.isInformed).isTrue();
    }

    private class FakeTimeGame extends TimeGame {

        public boolean isInformed = false;

        public FakeTimeGame() {
            super(null, null);
        }

        @Override
        public void timeRanDown() {
            isInformed = true;
        }
    }
}
