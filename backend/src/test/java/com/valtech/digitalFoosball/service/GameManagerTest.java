package com.valtech.digitalFoosball.service;

import com.valtech.digitalFoosball.exceptions.PlayerDuplicateException;
import com.valtech.digitalFoosball.exceptions.TeamDuplicateException;
import com.valtech.digitalFoosball.model.input.InitDataModel;
import com.valtech.digitalFoosball.model.internal.PlayerDataModel;
import com.valtech.digitalFoosball.model.internal.TeamDataModel;
import com.valtech.digitalFoosball.model.output.GameDataModel;
import com.valtech.digitalFoosball.model.output.TeamOutput;
import com.valtech.digitalFoosball.storage.PlayerService;
import com.valtech.digitalFoosball.storage.TeamService;
import com.valtech.digitalFoosball.storage.repository.PlayerRepository;
import com.valtech.digitalFoosball.storage.repository.TeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.groups.Tuple.tuple;

public class GameManagerTest {


    private GameManager gameManager;
    private InitDataModel initDataModel;
    private final UUID id = UUID.randomUUID();
    private TeamDataModel teamDataModelOne;
    private TeamDataModel teamDataModelTwo;

    @BeforeEach
    public void setUp() {
        gameManager = new GameManager(new TeamService(new TeamRepositoryFake(id), new PlayerService(new PlayerRepositoryFake())));
        initDataModel = new InitDataModel();
        teamDataModelOne = new TeamDataModel();
        teamDataModelTwo = new TeamDataModel();
        List<TeamDataModel> teamDataModels = new ArrayList<>();

        teamDataModelOne.setName("T1");
        teamDataModelOne.setNameOfPlayerOne("P1");
        teamDataModelOne.setNameOfPlayerTwo("P2");

        teamDataModelTwo.setName("T2");
        teamDataModelTwo.setNameOfPlayerOne("P3");
        teamDataModelTwo.setNameOfPlayerTwo("P4");

        teamDataModels.add(teamDataModelOne);
        teamDataModels.add(teamDataModelTwo);

        initDataModel.setTeams(teamDataModels);
    }

    @Test
    public void initGame_whenTeamsAndPlayersAreGiven_thenTheyAreSet() {
        gameManager.initGame(initDataModel);

        List<TeamDataModel> actual = gameManager.getTeams();
        assertThat(actual).extracting(TeamDataModel::getName, TeamDataModel::getNameOfPlayerOne, TeamDataModel::getNameOfPlayerTwo).containsExactly(
                tuple("T1", "P1", "P2"),
                tuple("T2", "P3", "P4"));
    }

    @Test
    public void initGame_whenTeamsAreGiven_thenTheyAreSavedIntoDatabase() {
        gameManager.initGame(initDataModel);

        List<TeamDataModel> actual = gameManager.getTeams();
        assertThat(actual).extracting(TeamDataModel::getName, TeamDataModel::getNameOfPlayerOne, TeamDataModel::getNameOfPlayerTwo, TeamDataModel::getId).containsExactly(
                tuple("T1", "P1", "P2", id),
                tuple("T2", "P3", "P4", id));
    }

    @Test
    public void initGame_whenPlayersAreGiven_thenSaveItToDatabase() {
        gameManager.initGame(initDataModel);

        List<PlayerDataModel> actual = gameManager.getTeams().get(0).getPlayers();
        assertThat(actual).extracting(PlayerDataModel::getName, PlayerDataModel::getId).containsExactly(
                tuple("P1", id),
                tuple("P2", id));
    }

    @Test
    public void initGame_whenAPlayerNameIsDuplicated_thenThrowPlayerDuplicateException() {
        initDataModel = new InitDataModel();
        TeamDataModel teamDataModelOne = new TeamDataModel();
        TeamDataModel teamDataModelTwo = new TeamDataModel();
        List<TeamDataModel> teamDataModels = new ArrayList<>();
        teamDataModelOne.setName("T1");
        teamDataModelOne.setNameOfPlayerOne("P1");
        teamDataModelOne.setNameOfPlayerTwo("P2");
        teamDataModelTwo.setName("T2");
        teamDataModelTwo.setNameOfPlayerOne("P3");
        teamDataModelTwo.setNameOfPlayerTwo("P1");
        teamDataModels.add(teamDataModelOne);
        teamDataModels.add(teamDataModelTwo);
        initDataModel.setTeams(teamDataModels);

        assertThatExceptionOfType(PlayerDuplicateException.class).isThrownBy(() -> {
            gameManager.initGame(initDataModel);
        });
    }

    @Test
    public void initGame_whenTeamNameIsDuplicated_thenThrowTeamDuplicateException() {
        initDataModel = new InitDataModel();
        TeamDataModel teamDataModelOne = new TeamDataModel();
        TeamDataModel teamDataModelTwo = new TeamDataModel();
        List<TeamDataModel> teamDataModels = new ArrayList<>();
        teamDataModelOne.setName("T1");
        teamDataModelOne.setNameOfPlayerOne("P1");
        teamDataModelOne.setNameOfPlayerTwo("P2");
        teamDataModelTwo.setName("T1");
        teamDataModelTwo.setNameOfPlayerOne("P3");
        teamDataModelTwo.setNameOfPlayerTwo("P4");
        teamDataModels.add(teamDataModelOne);
        teamDataModels.add(teamDataModelTwo);
        initDataModel.setTeams(teamDataModels);

        assertThatExceptionOfType(TeamDuplicateException.class).isThrownBy(() -> {
            gameManager.initGame(initDataModel);
        });
    }

    @Test
    public void raiseScore_whenATeamScores_thenRaiseTheirCounter() {
        gameManager.initGame(initDataModel);

        gameManager.raiseScore(1);

        int actual = gameManager.getTeams().get(0).getScore();
        assertThat(actual).isEqualTo(1);
    }

    @Test
    public void undoLastGoal_whenUndoIsMade_thenDecreaseTheScoreOfTheLastScoringTeamByOne() {
        gameManager.initGame(initDataModel);
        gameManager.raiseScore(1);

        gameManager.undoLastGoal();

        int actual = gameManager.getTeams().get(0).getScore();
        assertThat(actual).isEqualTo(0);
    }

    @Test
    public void undoLastGoal_whenSeveralGoalsAreScoredAndUndid_thenTheScoredOfBothTeamsAreZero() {
        gameManager.initGame(initDataModel);
        gameManager.raiseScore(1);
        gameManager.raiseScore(2);
        gameManager.raiseScore(1);

        gameManager.undoLastGoal();
        gameManager.undoLastGoal();
        gameManager.undoLastGoal();

        List<TeamDataModel> actual = gameManager.getTeams();
        assertThat(actual.get(0).getScore()).isEqualTo(0);
        assertThat(actual.get(1).getScore()).isEqualTo(0);
    }

    @Test
    public void undoLastGoal_whenNoGoalWasMade_thenDoNothing() {
        gameManager.initGame(initDataModel);

        gameManager.undoLastGoal();

        List<TeamDataModel> actual = gameManager.getTeams();
        assertThat(actual).extracting(TeamDataModel::getScore).containsExactly(0, 0);
    }

    @Test
    public void undoLastGoal_whenRoundWinningGoalIsUndid_thenDecreaseRoundWinsSoTheValueStaysSame() {
        gameManager.initGame(initDataModel);
        gameManager.raiseScore(1);
        gameManager.raiseScore(1);
        gameManager.raiseScore(1);
        gameManager.raiseScore(1);
        gameManager.raiseScore(1);
        gameManager.raiseScore(1);
        gameManager.getGameData();

        gameManager.undoLastGoal();
        List<TeamDataModel> teams = gameManager.getTeams();
        TeamDataModel teamDataModel = teams.get(0);
        int actualScore = teamDataModel.getScore();
        int actualWonRounds = teamDataModel.getWonRounds();

        assertThat(actualScore).isEqualTo(5);
        assertThat(actualWonRounds).isEqualTo(0);
    }

    @Test
    public void redoLastGoal_whenNoGoalWasUndid_thenDoNothing() {
        gameManager.initGame(initDataModel);

        gameManager.redoLastGoal();

        List<TeamDataModel> actual = gameManager.getTeams();
        assertThat(actual).extracting(TeamDataModel::getScore).containsExactly(0, 0);
    }

    @Test
    public void redoLastGoal_whenGoalsWereUndid_thenRedoThemInTheOrderOfUndo() {
        gameManager.initGame(initDataModel);
        gameManager.raiseScore(1);
        gameManager.raiseScore(1);
        gameManager.raiseScore(2);
        gameManager.raiseScore(1);
        gameManager.undoLastGoal();
        gameManager.undoLastGoal();

        gameManager.redoLastGoal();

        List<TeamDataModel> actual = gameManager.getTeams();
        assertThat(actual).extracting(TeamDataModel::getScore).containsExactly(2, 1);

        gameManager.redoLastGoal();

        actual = gameManager.getTeams();
        assertThat(actual).extracting(TeamDataModel::getScore).containsExactly(3, 1);
    }

    @Test
    public void redo_whenGoalWasUndidAndRedid_thenGoalIsSavedInHistoryAgain() throws Exception {
        gameManager.initGame(initDataModel);
        gameManager.raiseScore(1);
        gameManager.undoLastGoal();

        gameManager.redoLastGoal();

        Class cls = Class.forName("com.valtech.digitalFoosball.service.GameManager");
        Field lastScoringTeams = cls.getDeclaredField("historyOfGoals");
        lastScoringTeams.setAccessible(true);
        Stack<Integer> stack = (Stack<Integer>) lastScoringTeams.get(gameManager);
        Integer actual = stack.peek();
        assertThat(actual).isEqualTo(0);
    }

    @Test
    public void getGameData_whenGameDataIsRequested_thenReturnCurrentGameDataReadyForOutput() {
        GameDataModel expected = new GameDataModel();
        List<TeamOutput> teamOutputs = new ArrayList<>();
        for (TeamDataModel team : initDataModel.getTeams()) {
            teamOutputs.add(new Converter().convertToTeamOutput(team));
        }
        expected.setTeams(teamOutputs);
        gameManager.initGame(initDataModel);
        gameManager.raiseScore(1);
        gameManager.raiseScore(2);

        GameDataModel gameDataModel = gameManager.getGameData();
        List<TeamOutput> actual = gameDataModel.getTeams();

        assertThat(actual).extracting(TeamOutput::getName, TeamOutput::getPlayerOne, TeamOutput::getPlayerTwo, TeamOutput::getScore).containsExactly(
                tuple("T1", "P1", "P2", 1),
                tuple("T2", "P3", "P4", 1));
    }

    @Test
    public void getGameData_whenNoGameWasInitialized_thenReturnNull() {
        GameDataModel actual = gameManager.getGameData();

        assertThat(actual).isNull();
    }

    @Test
    public void resetGameValues_whenResetGameValuesIsCalled_thenSetEmptyNamesAndScoreToZero() {
        gameManager.initGame(initDataModel);
        gameManager.raiseScore(1);
        gameManager.raiseScore(2);

        gameManager.resetGameValues();

        List<String> expectedPlayerNames = new ArrayList<>();
        expectedPlayerNames.add("");
        expectedPlayerNames.add("");

        GameDataModel gameData = gameManager.getGameData();
        List<TeamOutput> actual = gameData.getTeams();
        assertThat(actual).extracting(TeamOutput::getName, TeamOutput::getPlayerOne, TeamOutput::getPlayerTwo, TeamOutput::getScore).containsExactly(
                tuple("", "", "", 0),
                tuple("", "", "", 0));
    }

    @Test
    public void resetGameValues_whenResetGameValuesIsCalled_thenTheLastScoringTeamsAreEmpty() throws Exception {
        gameManager.initGame(initDataModel);
        gameManager.raiseScore(1);

        gameManager.resetGameValues();

        Field lastScoringTeams = gameManager.getClass().getDeclaredField("historyOfGoals");
        lastScoringTeams.setAccessible(true);
        assertThat(lastScoringTeams.get(gameManager)).isEqualTo(new Stack<Integer>());
    }

    @Test
    public void getRoundWinner_whenOneTeamScoredSixOrMoreGoalsAndHasMoreThanOneGoalDifferenceToTheOtherTeam_thenReturnTheNumberOfThisTeam() {
        gameManager.initGame(initDataModel);
        gameManager.raiseScore(1);
        gameManager.raiseScore(1);
        gameManager.raiseScore(1);
        gameManager.raiseScore(1);
        gameManager.raiseScore(1);
        gameManager.raiseScore(1);

        int actual = gameManager.getRoundWinner();

        assertThat(actual).isEqualTo(1);
    }

    @Test
    public void getRoundWinner_whenOneTeamScoredSixOrMoreGoalsButDoesNotHaveMoreThanOneGoalDifferenceToTheOtherTeam_thenReturnZero() {
        gameManager.initGame(initDataModel);
        gameManager.raiseScore(1);
        gameManager.raiseScore(1);
        gameManager.raiseScore(1);
        gameManager.raiseScore(1);
        gameManager.raiseScore(1);
        gameManager.raiseScore(2);
        gameManager.raiseScore(2);
        gameManager.raiseScore(2);
        gameManager.raiseScore(2);
        gameManager.raiseScore(2);
        gameManager.raiseScore(1);

        int actual = gameManager.getRoundWinner();

        assertThat(actual).isEqualTo(0);
    }

    @Test
    public void getRoundWinner_whenNoTeamHasScoredSixGoals_thenReturnZero() {
        gameManager.initGame(initDataModel);

        int actual = gameManager.getRoundWinner();

        assertThat(actual).isEqualTo(0);
    }

    @Test
    public void getRoundWinner_whenOneTeamHasFulfilledTheWinningCondition_thenRaiseThereWonRoundsByOne() {
        gameManager.initGame(initDataModel);
        gameManager.raiseScore(1);
        gameManager.raiseScore(1);
        gameManager.raiseScore(1);
        gameManager.raiseScore(1);
        gameManager.raiseScore(1);
        gameManager.raiseScore(1);

        gameManager.getRoundWinner();
        List<TeamDataModel> teams = gameManager.getTeams();
        TeamDataModel roundWinner = teams.get(0);
        int actual = roundWinner.getWonRounds();

        assertThat(actual).isEqualTo(1);
    }

    @Test
    public void getMatchWinner_whenOneTeamHasWonTwoRounds_thenReturnTheNumberOfThisTeam() {
        gameManager.initGame(initDataModel);
        gameManager.raiseScore(1);
        gameManager.raiseScore(1);
        gameManager.raiseScore(1);
        gameManager.raiseScore(1);
        gameManager.raiseScore(1);
        gameManager.raiseScore(1);
        gameManager.getRoundWinner();
        gameManager.raiseScore(1);
        gameManager.raiseScore(1);
        gameManager.raiseScore(1);
        gameManager.raiseScore(1);
        gameManager.raiseScore(1);
        gameManager.raiseScore(1);
        gameManager.getRoundWinner();

        int actual = gameManager.getMatchWinner();

        assertThat(actual).isEqualTo(1);
    }

    @Test
    public void newRound_whenNewRoundIsStarted_thenNamesAndPlayersAreSameButScoresAreZero() {
        gameManager.initGame(initDataModel);
        gameManager.raiseScore(1);
        gameManager.raiseScore(2);

        gameManager.newRound();

        List<TeamDataModel> teams = gameManager.getTeams();

        assertThat(teams).extracting(TeamDataModel::getScore).containsExactly(0, 0);
        assertThat(teams).extracting(TeamDataModel::getName).containsExactly("T1", "T2");
        assertThat(teams).extracting(TeamDataModel::getNameOfPlayerOne).containsExactly("P1", "P3");
        assertThat(teams).extracting(TeamDataModel::getNameOfPlayerTwo).containsExactly("P2", "P4");
    }

    @Test
    public void newRound_whenNewRoundIsStartedAndUndoIsMade_thenScoreStaysZeroForBothTeams() {
        gameManager.initGame(initDataModel);
        gameManager.raiseScore(1);
        gameManager.raiseScore(2);

        gameManager.newRound();
        gameManager.undoLastGoal();

        List<TeamDataModel> teams = gameManager.getTeams();
        assertThat(teams).extracting(TeamDataModel::getScore).containsExactly(0, 0);
    }

    @Test
    public void getAllTeams_whenNoTeamIsFound_thenReturnAnEmptyList() {
        gameManager = new GameManager(new TeamService(new TeamRepositoryFakeTwo(id), new PlayerService(new PlayerRepositoryFake())));
        gameManager.initGame(initDataModel);

        List<TeamOutput> actual = gameManager.getAllTeams();

        assertThat(actual).isEmpty();
    }

    @Test
    public void getAllTeams_whenTeamsWereFound_thenReturnListWithTheseTeams() {
        teamDataModelOne.setName("Roto");
        teamDataModelTwo.setName("Rototo");

        List<TeamOutput> actual = gameManager.getAllTeams();

        assertThat(actual).extracting(TeamOutput::getName).containsExactly("Roto", "Rototo");
    }

    private class TeamRepositoryFake implements TeamRepository {
        private UUID id;

        public TeamRepositoryFake(UUID id) {
            this.id = id;
        }

        @Override
        public TeamDataModel save(TeamDataModel teamDataModel) {
            teamDataModel.setId(id);
            return teamDataModel;
        }

        @Override
        public <S extends TeamDataModel> Iterable<S> saveAll(Iterable<S> iterable) {
            return null;
        }

        @Override
        public Optional<TeamDataModel> findById(UUID uuid) {
            return Optional.empty();
        }

        @Override
        public boolean existsById(UUID uuid) {
            return false;
        }

        @Override
        public Iterable<TeamDataModel> findAllById(Iterable<UUID> iterable) {
            return null;
        }

        @Override
        public long count() {
            return 0;
        }

        @Override
        public void deleteById(UUID uuid) {

        }

        @Override
        public void delete(TeamDataModel teamDataModel) {

        }

        @Override
        public void deleteAll(Iterable<? extends TeamDataModel> iterable) {

        }

        @Override
        public void deleteAll() {

        }

        @Override
        public Optional<TeamDataModel> findByNameIgnoreCase(String teamName) {
            return Optional.empty();
        }

        @Override
        public List<TeamDataModel> findAll() {
            List<TeamDataModel> teamDataModels = new ArrayList<>();
            teamDataModels.add(teamDataModelOne);
            teamDataModels.add(teamDataModelTwo);


            return teamDataModels;
        }
    }

    private class PlayerRepositoryFake implements PlayerRepository {

        @Override
        public Optional<PlayerDataModel> findByName(String name) {
            return Optional.empty();
        }

        public PlayerDataModel save(PlayerDataModel s) {
            s.setId(id);

            return s;
        }


        @Override
        public <S extends PlayerDataModel> Iterable<S> saveAll(Iterable<S> iterable) {
            return null;
        }

        @Override
        public Optional<PlayerDataModel> findById(UUID uuid) {

            return Optional.empty();
        }

        @Override
        public boolean existsById(UUID uuid) {
            return false;
        }

        @Override
        public Iterable<PlayerDataModel> findAll() {
            return null;
        }

        @Override
        public Iterable<PlayerDataModel> findAllById(Iterable<UUID> iterable) {
            return null;
        }

        @Override
        public long count() {
            return 0;
        }

        @Override
        public void deleteById(UUID uuid) {

        }

        @Override
        public void delete(PlayerDataModel playerDataModel) {

        }

        @Override
        public void deleteAll(Iterable<? extends PlayerDataModel> iterable) {

        }

        @Override
        public void deleteAll() {

        }
    }

    private class TeamRepositoryFakeTwo implements TeamRepository {
        private UUID id;

        public TeamRepositoryFakeTwo(UUID id) {
            this.id = id;
        }

        @Override
        public TeamDataModel save(TeamDataModel teamDataModel) {
            teamDataModel.setId(id);
            return teamDataModel;
        }

        @Override
        public <S extends TeamDataModel> Iterable<S> saveAll(Iterable<S> iterable) {
            return null;
        }

        @Override
        public Optional<TeamDataModel> findById(UUID uuid) {
            return Optional.empty();
        }

        @Override
        public boolean existsById(UUID uuid) {
            return false;
        }

        @Override
        public Iterable<TeamDataModel> findAllById(Iterable<UUID> iterable) {
            return null;
        }

        @Override
        public long count() {
            return 0;
        }

        @Override
        public void deleteById(UUID uuid) {

        }

        @Override
        public void delete(TeamDataModel teamDataModel) {

        }

        @Override
        public void deleteAll(Iterable<? extends TeamDataModel> iterable) {

        }

        @Override
        public void deleteAll() {

        }

        @Override
        public Optional<TeamDataModel> findByNameIgnoreCase(String teamName) {
            return Optional.empty();
        }

        @Override
        public List<TeamDataModel> findAll() {

            return new ArrayList<>();
        }
    }
}
