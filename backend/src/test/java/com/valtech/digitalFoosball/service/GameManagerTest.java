package com.valtech.digitalFoosball.service;

import com.valtech.digitalFoosball.exceptions.NameDuplicateException;
import com.valtech.digitalFoosball.model.input.InitDataModel;
import com.valtech.digitalFoosball.model.internal.PlayerDataModel;
import com.valtech.digitalFoosball.model.internal.TeamDataModel;
import com.valtech.digitalFoosball.model.output.GameDataModel;
import com.valtech.digitalFoosball.model.output.TeamOutput;
import com.valtech.digitalFoosball.storage.PlayerService;
import com.valtech.digitalFoosball.storage.TeamService;
import com.valtech.digitalFoosball.storage.repository.PlayerRepository;
import com.valtech.digitalFoosball.storage.repository.TeamRepository;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.groups.Tuple.tuple;

public class GameManagerTest {
    public GameManager gameManager;
    protected InitDataModel initDataModel;
    private final UUID id = UUID.randomUUID();
    private TeamDataModel teamDataModelOne;
    private TeamDataModel teamDataModelTwo;


    public GameManagerTest() {

        initDataModel = new InitDataModel();
        gameManager = new GameManager(new TeamService(new TeamRepositoryFake(id), new PlayerService(new PlayerRepositoryFake())));
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

        gameManager.initGame(initDataModel);
    }

    //better naming for init!
    @Test
    public void initGame_whenNamesWereGiven_thenSetThese() {

        List<TeamDataModel> actual = gameManager.getTeams();
        assertThat(actual).extracting(TeamDataModel::getName, TeamDataModel::getNameOfPlayerOne, TeamDataModel::getNameOfPlayerTwo).containsExactly(
                tuple("T1", "P1", "P2"),
                tuple("T2", "P3", "P4"));
    }

    @Test
    public void initGame_whenTeamsWereGiven_thenSaveItToDatabase() {

        List<TeamDataModel> actual = gameManager.getTeams();
        assertThat(actual).extracting(TeamDataModel::getName, TeamDataModel::getNameOfPlayerOne, TeamDataModel::getNameOfPlayerTwo, TeamDataModel::getId).containsExactly(
                tuple("T1", "P1", "P2", id),
                tuple("T2", "P3", "P4", id));
    }

    @Test
    public void initGame_whenPlayersAreGiven_thenSaveItToDatabase() {

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

        assertThatExceptionOfType(NameDuplicateException.class).isThrownBy(() -> {
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

        assertThatExceptionOfType(NameDuplicateException.class).isThrownBy(() -> {
            gameManager.initGame(initDataModel);
        });
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
        raiseScoreOf(1, 2);

        GameDataModel gameDataModel = gameManager.getGameData();
        List<TeamOutput> actual = gameDataModel.getTeams();

        assertThat(actual).extracting(TeamOutput::getName, TeamOutput::getPlayerOne, TeamOutput::getPlayerTwo, TeamOutput::getScore).containsExactly(
                tuple("T1", "P1", "P2", 1),
                tuple("T2", "P3", "P4", 1));
    }

    @Test
    public void getGameData_whenEmptyGameDataWhereRequested_thenReturnNull() {
        gameManager.setTeams(null);

        GameDataModel actual = gameManager.getGameData();
        assertThat(actual).isNull();
    }

    @Test
    public void resetMatch_whenMatchIsReset_thenNamesAndScoresAreDefault() {
        raiseScoreOf(1, 2);

        gameManager.resetMatch();

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
    public void resetMatch_whenMatchIsReset_thenScoreHistoryIsEmpty() throws Exception {
        raiseScoreOf(1);

        gameManager.resetMatch();

        Field lastScoringTeams = gameManager.getClass().getDeclaredField("historyOfGoals");
        lastScoringTeams.setAccessible(true);
        assertThat(lastScoringTeams.get(gameManager)).isEqualTo(new Stack<Integer>());
    }

    @Test
    public void resetMatch_whenMatchIsReset_thenUndoHistoryIsEmpty() throws Exception {
        raiseScoreOf(1, 1, 1, 1, 1, 1);
        gameManager.undoGoal();
        gameManager.undoGoal();

        gameManager.resetMatch();

        Field lastScoringTeams = gameManager.getClass().getDeclaredField("historyOfUndo");
        lastScoringTeams.setAccessible(true);
        assertThat(lastScoringTeams.get(gameManager)).isEqualTo(new Stack<Integer>());
    }

    @Test
    public void getRoundWinner_whenNoTeamFulfillsRoundWinCondition_thenReturnZero() {
        raiseScoreOf(1, 1, 1, 1, 1);

        int actual = gameManager.getSetWinner();

        assertThat(actual).isEqualTo(0);
    }

    @Test
    void getRoundWinner_whenATeamFulfillRoundWinCondition_thenReturnItsNumber() {
        raiseScoreOf(2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 2, 1, 1);

        int actual = gameManager.getSetWinner();

        assertThat(actual).isEqualTo(1);
    }

    @Test
    public void getMatchWinner_whenOneTeamHasWonTwoRounds_thenReturnItsNumber() {
        raiseScoreOf(1, 1, 1, 1, 1, 1);
        gameManager.changeover();
        raiseScoreOf(1, 1, 1, 1, 1, 1);

        int actual = gameManager.getMatchWinner();

        assertThat(actual).isEqualTo(1);
    }

    @Test
    public void newRound_whenNewRoundIsStarted_thenScoresAreZero() {
        raiseScoreOf(1, 2);

        gameManager.changeover();

        List<TeamDataModel> teams = gameManager.getTeams();
        assertThat(teams).extracting(TeamDataModel::getScore).containsExactly(0, 0);
        assertThat(teams).extracting(TeamDataModel::getName).containsExactly("T1", "T2");
        assertThat(teams).extracting(TeamDataModel::getNameOfPlayerOne).containsExactly("P1", "P3");
        assertThat(teams).extracting(TeamDataModel::getNameOfPlayerTwo).containsExactly("P2", "P4");
    }

    @Test
    public void newRound_whenNewRoundIsStarted_thenScoreHistoryIsEmpty() {
        raiseScoreOf(1, 2);

        gameManager.changeover();

        gameManager.undoGoal();
        List<TeamDataModel> teams = gameManager.getTeams();
        assertThat(teams).extracting(TeamDataModel::getScore).containsExactly(0, 0);
    }

    @Test
    public void newRound_whenNewRoundIsStarted_thenUndoHistoryIsEmpty() {
        raiseScoreOf(1, 2, 2, 2, 2);

        gameManager.changeover();

        gameManager.redoGoal();
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
    public void getAllTeams_whenTeamsWereFound_thenReturnThem() {
        teamDataModelOne.setName("Roto");
        teamDataModelTwo.setName("Rototo");

        List<TeamOutput> actual = gameManager.getAllTeams();

        assertThat(actual).extracting(TeamOutput::getName).containsExactly("Roto", "Rototo");
    }

    protected void raiseScoreOf(int... teams) {
        for (int team : teams) {
            gameManager.countGoalFor(team);
        }
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



