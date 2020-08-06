package com.valtech.digitalFoosball.api.sensorcommands;

import com.valtech.digitalFoosball.api.INotifyAboutStateChanges;
import com.valtech.digitalFoosball.domain.common.DigitalFoosballGameRules;
import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.models.GameDataModel;
import com.valtech.digitalFoosball.domain.common.models.output.game.ClassicGameOutputModel;
import com.valtech.digitalFoosball.domain.common.models.output.game.GameOutputModel;
import com.valtech.digitalFoosball.domain.ranked.RankedGame;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RaspiControllerShould {

    private RaspiController raspiController;
    private FakeRankedGame game;
    private FakePublisher publisher;

    @BeforeEach
    void setUp() {
        publisher = new FakePublisher();
        raspiController = new RaspiController(publisher);
        game = new FakeRankedGame();
    }

    @Test
    void raise_score_in_the_given_game() {
        raspiController.setGame(game);
        raspiController.raiseScore(1);

        assertThat(game.scoreRaised).isTrue();
    }

    @Test
    void inform_the_clients_about_a_goal() {
        raspiController.setGame(game);
        raspiController.raiseScore(1);

        assertThat(publisher.clientsInformed).isTrue();
    }

    private class FakeRankedGame extends RankedGame {

        public boolean scoreRaised = false;

        public FakeRankedGame() {
            super(null);
        }

        @Override
        public GameOutputModel getGameData() {
            return new ClassicGameOutputModel(new GameDataModel(), new DigitalFoosballGameRules());
        }

        @Override
        public void countGoalFor(Team team) {
            scoreRaised = true;
        }
    }

    private class FakePublisher implements INotifyAboutStateChanges {
        public boolean clientsInformed = false;

        @Override
        public void notifyAboutStateChange(GameOutputModel gameData) {
            clientsInformed = true;
        }
    }
}
