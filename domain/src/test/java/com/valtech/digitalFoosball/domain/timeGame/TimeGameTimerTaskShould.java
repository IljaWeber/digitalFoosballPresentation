package com.valtech.digitalFoosball.domain.timeGame;

import com.valtech.digitalFoosball.domain.timeGame.service.TimeGameTimerTask;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TimeGameTimerTaskShould {

    @Test
    void inform_the_rules_when_time_ran_down() {
        FakeTimeGame game = new FakeTimeGame();
        TimeGameTimerTask timeGameTimerTask = new TimeGameTimerTask(game);

        timeGameTimerTask.run();

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
