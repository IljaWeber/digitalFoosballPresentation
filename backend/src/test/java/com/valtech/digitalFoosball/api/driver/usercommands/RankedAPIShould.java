package com.valtech.digitalFoosball.api.driver.usercommands;

import com.valtech.digitalFoosball.api.driver.sensorcommands.RaspiController;
import com.valtech.digitalFoosball.domain.common.IPlayAGame;
import com.valtech.digitalFoosball.domain.common.models.GameDataModel;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import com.valtech.digitalFoosball.domain.ranked.RankedGame;
import com.valtech.digitalFoosball.domain.ranked.RankedInitService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RankedAPIShould {

    @Test
    void inform_the_raspi_controller_that_the_current_game_is_ranked() {
        RankedGame game = new RankedGame(new FakeInitService());
        FakeRaspiController raspiController = new FakeRaspiController();
        RankedAPI rankedAPI = new RankedAPI(game, raspiController);

        rankedAPI.init(new InitDataModel());

        assertThat(raspiController.IPlayAGame).isInstanceOf(RankedGame.class);
    }

    private class FakeRaspiController extends RaspiController {
        public IPlayAGame IPlayAGame;

        private FakeRaspiController() {
            super(null);
        }

        @Override
        public void setGame(IPlayAGame IPlayAGame) {
            this.IPlayAGame = IPlayAGame;
        }
    }

    private class FakeInitService extends RankedInitService {
        public FakeInitService() {
            super(null);
        }

        @Override
        public GameDataModel init(InitDataModel initDataModel) {
            return new GameDataModel();
        }
    }
}