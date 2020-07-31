package com.valtech.digitalFoosball.api.driver.usercommands;

import com.valtech.digitalFoosball.api.driver.sensorcommands.RaspiController;
import com.valtech.digitalFoosball.domain.adhoc.AdHocInitService;
import com.valtech.digitalFoosball.domain.common.IPlayAGame;
import com.valtech.digitalFoosball.domain.common.models.GameDataModel;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import com.valtech.digitalFoosball.domain.timeGame.TimeGame;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TimeGameAPIShould {

    @Test
    void inform_the_raspi_controller_that_the_current_game_is_adhoc() {
        TimeGame game = new TimeGame(new FakeInitService());
        FakeRaspiController raspiController = new FakeRaspiController();
        TimeGameAPI timeGameAPI = new TimeGameAPI(game, raspiController);

        timeGameAPI.init();

        assertThat(raspiController.IPlayAGame).isInstanceOf(TimeGame.class);
    }

    private class FakeRaspiController extends RaspiController {
        public IPlayAGame IPlayAGame;

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
