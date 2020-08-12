package com.valtech.digitalFoosball.domain.timeGame;

import com.valtech.digitalFoosball.api.notification.Publisher;
import com.valtech.digitalFoosball.domain.adhoc.AdHocInitService;
import com.valtech.digitalFoosball.domain.common.constants.Team;
import com.valtech.digitalFoosball.domain.common.models.InitDataModel;
import com.valtech.digitalFoosball.domain.common.models.output.game.GameOutputModel;
import com.valtech.digitalFoosball.domain.common.models.output.team.TeamOutputModel;
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
    public void prepare_a_game_in_first_half_with_team_and_player_names_with_their_scores_for_output() {
        raiseScoreFor(ONE, ONE,
                      TWO);

        TimeGameOutputModel gameData = timeGame.getGameData();
        List<TeamOutputModel> actual = gameData.getTeams();
        assertThat(actual).extracting(TeamOutputModel::getName).containsExactly("Orange", "Green");
        assertThat(actual).extracting(TeamOutputModel::getPlayerOne).containsExactly("Goalie", "Goalie");
        assertThat(actual).extracting(TeamOutputModel::getPlayerTwo).containsExactly("Striker", "Striker");
        assertThat(actual).extracting(TeamOutputModel::getScore).containsExactly(2, 1);
        assertThat(gameData.getActualGameSequence()).isEqualTo("First Half");
        assertThat(gameData.getMatchWinner()).isEqualTo(NO_TEAM);
    }

    @Test
    public void show_game_which_has_won_by_reaching_the_score_limit_with_the_winner_all_team_and_player_names_and_their_scores() {
        raiseScoreFor(ONE, ONE,
                      ONE, ONE,
                      ONE, ONE,
                      ONE, ONE,
                      ONE, ONE);

        TimeGameOutputModel gameData = timeGame.getGameData();
        List<TeamOutputModel> actual = gameData.getTeams();
        assertThat(actual).extracting(TeamOutputModel::getName).containsExactly("Orange", "Green");
        assertThat(actual).extracting(TeamOutputModel::getPlayerOne).containsExactly("Goalie", "Goalie");
        assertThat(actual).extracting(TeamOutputModel::getPlayerTwo).containsExactly("Striker", "Striker");
        assertThat(actual).extracting(TeamOutputModel::getScore).containsExactly(10, 0);
        assertThat(gameData.getActualGameSequence()).isEqualTo("End by Score Limit");
        assertThat(gameData.getMatchWinner()).isEqualTo(ONE);
    }

    @Test
    void inform_the_clients_when_the_game_sequence_changes() {
        timeGame.gameSequenceChanged();

        GameOutputModel gameData = fakePublisher.gameData;
        List<TeamOutputModel> actual = gameData.getTeams();
        assertThat(actual).extracting(TeamOutputModel::getName).containsExactly("Orange", "Green");
    }

    @Test
    void inform_the_rules_when_the_time_ran_down() {
        TimeGame timeGame = new TimeGame(null, null);
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

    private class FakePublisher extends Publisher {
        public GameOutputModel gameData;

        public FakePublisher() {
            super(null);
        }

        @Override
        public void notifyAboutStateChange(GameOutputModel gameData) {
            this.gameData = gameData;
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
