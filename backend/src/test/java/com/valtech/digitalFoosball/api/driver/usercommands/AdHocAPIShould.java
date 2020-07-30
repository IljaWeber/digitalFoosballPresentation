package com.valtech.digitalFoosball.api.driver.usercommands;

import com.valtech.digitalFoosball.api.driver.sensorcommands.RaspiController;
import com.valtech.digitalFoosball.domain.adhoc.AdHocGame;
import com.valtech.digitalFoosball.domain.adhoc.AdHocInitService;
import com.valtech.digitalFoosball.domain.common.IPlayAGame;
import com.valtech.digitalFoosball.domain.common.models.GameDataModel;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AdHocAPIShould {

    @Test
    void inform_the_raspi_controller_that_the_current_game_is_adhoc() {
        AdHocGame game = new AdHocGame(new FakeInitService());
        FakeRaspiController raspiController = new FakeRaspiController();
        AdHocAPI adHocAPI = new AdHocAPI(game, raspiController);

        adHocAPI.init();

        assertThat(raspiController.game).isInstanceOf(AdHocGame.class);
    }

    private class FakeRaspiController extends RaspiController {
        public IPlayAGame game;

        @Override
        public void setGame(IPlayAGame game) {
            this.game = game;
        }
    }

    private class FakeInitService extends AdHocInitService {
        @Override
        public GameDataModel init(InitDataModel initDataModel) {
            return new GameDataModel();
        }
    }
}