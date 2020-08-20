package com.valtech.digitalFoosball.api.usercommands;

import com.valtech.digitalFoosball.api.sensorcommands.RaspiController;
import com.valtech.digitalFoosball.domain.IPlayAGame;
import com.valtech.digitalFoosball.domain.adhoc.AdHocGameRules;
import com.valtech.digitalFoosball.domain.adhoc.AdHocInitService;
import com.valtech.digitalFoosball.domain.common.models.GameDataModel;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AdHocAPIShould {

    @Test
    void inform_the_raspi_controller_that_the_current_game_is_adhoc() {
        AdHocGameRules game = new AdHocGameRules(new FakeInitService());
        FakeRaspiController raspiController = new FakeRaspiController();
        AdHocAPI adHocAPI = new AdHocAPI(game, raspiController);

        adHocAPI.init();

        assertThat(raspiController.IPlayAGame).isInstanceOf(AdHocGameRules.class);
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

    private class FakeInitService extends AdHocInitService {
        @Override
        public GameDataModel init(InitDataModel initDataModel) {
            return new GameDataModel();
        }
    }
}
