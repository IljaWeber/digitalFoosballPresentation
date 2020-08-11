package com.valtech.digitalFoosball.domain.timeGame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Stack;

import static org.assertj.core.api.Assertions.assertThat;

class HalfTimeShould {

    private IPlayATimeGame halftime;
    private TimeGameRules rules;

    @BeforeEach
    void setUp() {
        rules = new TimeGameRules();
        rules.setGame(new FakeTimeGame());
        this.halftime = new HalfTime(new Stack<>(), rules);
    }

    @Test
    public void start_a_second_half() {
        halftime.changeover();

        IPlayATimeGame actual = rules.getActualGameSequence();
        assertThat(actual).isInstanceOf(SecondHalf.class);
    }

    private class FakeTimeGame extends TimeGame {
        public FakeTimeGame() {
            super(null, null);
        }

        @Override
        public void gameSequenceChanged() {
        }
    }
}
