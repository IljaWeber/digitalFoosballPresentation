package com.valtech.digitalFoosball.domain.timeGame;

import com.valtech.digitalFoosball.domain.usecases.timeGame.IPlayATimeGame;
import com.valtech.digitalFoosball.domain.usecases.timeGame.TimeGameRules;
import com.valtech.digitalFoosball.domain.usecases.timeGame.sequences.HalfTime;
import com.valtech.digitalFoosball.domain.usecases.timeGame.sequences.SecondHalf;
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
        this.halftime = new HalfTime(new Stack<>(), new Stack<>() , rules);
    }

    @Test
    public void start_a_second_half() {
        halftime.changeover();

        IPlayATimeGame actual = rules.getActualGameSequence();
        assertThat(actual).isInstanceOf(SecondHalf.class);
    }
}
