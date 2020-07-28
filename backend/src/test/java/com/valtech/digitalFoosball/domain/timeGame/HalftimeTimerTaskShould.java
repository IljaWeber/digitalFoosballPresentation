package com.valtech.digitalFoosball.domain.timeGame;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HalftimeTimerTaskShould {

    @Test
    void inform_the_game_when_the_time_is_over() {
        TimeGameRulesFake timeGame = new TimeGameRulesFake();
        HalftimeTimerTask task = new HalftimeTimerTask(timeGame);

        task.run();

        boolean actual = timeGame.informed;
        assertThat(actual).isTrue();
    }

    private class TimeGameRulesFake extends TimeGameRules {
        boolean informed = false;

        @Override
        public void timeRanDown() {
            informed = true;
        }
    }
}
