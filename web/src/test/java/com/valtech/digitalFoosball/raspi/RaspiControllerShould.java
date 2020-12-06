package com.valtech.digitalFoosball.raspi;

import com.valtech.digitalFoosball.domain.DigitalFoosballFacade;
import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.models.GameDataModel;
import com.valtech.digitalFoosball.domain.common.models.output.game.ClassicGameOutputModel;
import com.valtech.digitalFoosball.domain.common.models.output.game.GameOutputModel;
import com.valtech.digitalFoosball.domain.ports.INotifyAboutStateChanges;
import com.valtech.digitalFoosball.domain.ranked.RankedGameRules;
import com.valtech.digitalFoosball.rest.RaspiController;
import org.junit.jupiter.api.BeforeEach;

class RaspiControllerShould {

    private RaspiController raspiController;
    private FakeRankedGameRules game;
    private FakePublisher publisher;

    @BeforeEach
    void setUp() {
        publisher = new FakePublisher();
        raspiController = new RaspiController(publisher, new DigitalFoosballFacadeFake(game));
        game = new FakeRankedGameRules();
    }

    private class DigitalFoosballFacadeFake extends DigitalFoosballFacade {
        private FakeRankedGameRules rules;

        public DigitalFoosballFacadeFake(FakeRankedGameRules rules) {
            super(null, null);
            this.rules = rules;
        }
    }

    private class FakeRankedGameRules extends RankedGameRules {

        public boolean scoreRaised = false;

        public FakeRankedGameRules() {
            super(null);
        }

        @Override
        public GameOutputModel getGameData() {
            return new ClassicGameOutputModel(new GameDataModel());
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
