package com.valtech.digitalFoosball.domain.timeGame;

import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import com.valtech.digitalFoosball.domain.common.models.output.game.GameOutputModel;
import com.valtech.digitalFoosball.domain.common.models.output.team.TeamOutputModel;
import com.valtech.digitalFoosball.domain.ports.INotifyAboutStateChanges;
import com.valtech.digitalFoosball.domain.usecases.adhoc.AdHocInitService;
import com.valtech.digitalFoosball.domain.usecases.timeGame.TimeGame;
import com.valtech.digitalFoosball.domain.usecases.timeGame.TimeGameOutputModel;
import com.valtech.digitalFoosball.domain.usecases.timeGame.TimeGameRules;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.valtech.digitalFoosball.domain.common.constants.Team.*;
import static org.assertj.core.api.Assertions.assertThat;

class TimeGameShould {
    private TimeGame timeGame;

    private FakePublisher fakePublisher;

    @BeforeEach
    void setUp() {
        fakePublisher = new FakePublisher();
        timeGame = new TimeGame(new AdHocInitService(), fakePublisher);
        timeGame.initGame(new InitDataModel());
    }

    @Test
    void play_a_time_game_until_one_team_scored_ten_goals() {
        raiseScoreFor(ONE, ONE,
                      TWO,
                      ONE,
                      TWO, TWO);
        timeGame.timeRanDown();
        timeGame.changeover();

        raiseScoreFor(ONE, ONE,
                      TWO,
                      ONE,
                      TWO, TWO,
                      ONE, ONE, ONE, ONE);

        TimeGameOutputModel gameData = timeGame.getGameData();
        List<TeamOutputModel> actual = gameData.getTeams();
        assertThat(actual).extracting(TeamOutputModel::getName).containsExactly("Orange", "Green");
        assertThat(actual).extracting(TeamOutputModel::getPlayerOne).containsExactly("Goalie", "Goalie");
        assertThat(actual).extracting(TeamOutputModel::getPlayerTwo).containsExactly("Striker", "Striker");
        assertThat(actual).extracting(TeamOutputModel::getScore).containsExactly(10, 6);
        assertThat(gameData.getActualGameSequence()).isEqualTo("End By Score");
        assertThat(gameData.getMatchWinner()).isEqualTo(ONE);
    }

    @Test
    void play_a_time_game_until_second_half_is_over_no_matter_if_there_is_a_winner() {
        raiseScoreFor(ONE, ONE,
                      TWO,
                      ONE,
                      TWO, TWO);
        timeGame.timeRanDown();
        timeGame.changeover();
        raiseScoreFor(ONE, ONE,
                      TWO,
                      ONE,
                      TWO, TWO);

        timeGame.timeRanDown();

        TimeGameOutputModel gameData = timeGame.getGameData();
        List<TeamOutputModel> actual = gameData.getTeams();
        assertThat(actual).extracting(TeamOutputModel::getName).containsExactly("Orange", "Green");
        assertThat(actual).extracting(TeamOutputModel::getPlayerOne).containsExactly("Goalie", "Goalie");
        assertThat(actual).extracting(TeamOutputModel::getPlayerTwo).containsExactly("Striker", "Striker");
        assertThat(actual).extracting(TeamOutputModel::getScore).containsExactly(6, 6);
        assertThat(gameData.getActualGameSequence()).isEqualTo("End By Time");
        assertThat(gameData.getMatchWinner()).isEqualTo(NO_TEAM);
    }

    @Test
    public void start_a_time_game() {
        timeGame.initGame(new InitDataModel());

        TimeGameOutputModel outputModel = timeGame.getGameData();
        List<TeamOutputModel> actual = outputModel.getTeams();
        assertThat(actual).extracting(TeamOutputModel::getName).containsExactly("Orange", "Green");
        assertThat(actual).extracting(TeamOutputModel::getPlayerOne).containsExactly("Goalie", "Goalie");
        assertThat(actual).extracting(TeamOutputModel::getPlayerTwo).containsExactly("Striker", "Striker");
        assertThat(actual).extracting(TeamOutputModel::getScore).containsExactly(0, 0);
        assertThat(outputModel.getActualGameSequence()).isEqualTo("First Half");
        assertThat(outputModel.getMatchWinner()).isEqualTo(NO_TEAM);
    }

    @Test
    void inform_the_clients_when_the_game_sequence_changes() {
        timeGame.timeRanDown();

        assertThat(fakePublisher.clientsInformed).isTrue();
    }

    @Test
    void inform_the_rules_when_the_time_ran_down() {
        FakeGameRules rules = new FakeGameRules();
        timeGame.setGameRules(rules);

        timeGame.timeRanDown();

        assertThat(rules.isInformed).isTrue();
    }

    private void raiseScoreFor(Team... teams) {
        for (Team team : teams) {
            timeGame.countGoalFor(team);
        }
    }

    private class FakePublisher implements INotifyAboutStateChanges {
        public boolean clientsInformed = false;

        @Override
        public void notifyAboutStateChange(GameOutputModel gameData) {
            clientsInformed = true;
        }
    }

    private class FakeGameRules extends TimeGameRules {
        public boolean isInformed = false;

        @Override
        public void timeRanDown() {
            isInformed = true;
        }
    }
}
