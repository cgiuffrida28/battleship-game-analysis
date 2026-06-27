import java.util.ArrayList;
import java.util.List;

public class RandomBoardSimulationRunner {
    private static final int MAX_TURNS_PER_GAME = 1000;

    public List<SimulationResult> runRandomBoardGames(BattleshipAI ai, int numberOfGames, int boardSeed) {
        if (numberOfGames <= 0) {
            throw new IllegalArgumentException("Number of games must be positive.");
        }
        List<SimulationResult> results = new ArrayList<>();
        for (int gameNumber = 1; gameNumber <= numberOfGames; gameNumber++) {
            Board targetBoard = RandomBoardFactory.createRandomBoard(boardSeed + gameNumber);
            int turnsToWin = runSingleGame(ai, targetBoard, gameNumber);
            results.add(new SimulationResult(ai.getName(), gameNumber, turnsToWin));
        }
        return results;
    }

    public List<SimulationResult> runOriginalAIRandomBoardGames(int aiSeed, int numberOfGames, int boardSeed) {
        if (numberOfGames <= 0) {
            throw new IllegalArgumentException("Number of games must be positive.");
        }
        List<SimulationResult> results = new ArrayList<>();
        for (int gameNumber = 1; gameNumber <= numberOfGames; gameNumber++) {
            Board targetBoard = RandomBoardFactory.createRandomBoard(boardSeed + gameNumber);
            AI originalAI = new AI(aiSeed + gameNumber);
            int turnsToWin = runSingleGame(originalAI, targetBoard, gameNumber);
            results.add(new SimulationResult("OriginalGroupAI", gameNumber, turnsToWin));
        }
        return results;
    }

    private int runSingleGame(BattleshipAI ai, Board targetBoard, int gameNumber) {
        ai.reset();
        int turns = 0;
        while (BoardAnalyzer.hasRemainingShipCells(targetBoard)) {
            int move = ai.chooseMove(targetBoard);
            Move.move(move, targetBoard);
            turns++;
            if (turns > MAX_TURNS_PER_GAME) {
                throw new IllegalStateException(ai.getName() + " did not clear the board within " + MAX_TURNS_PER_GAME + " turns in game " + gameNumber + ".");
            }
        }
        return turns;
    }

    private int runSingleGame(AI originalAI, Board targetBoard, int gameNumber) {
        int turns = 0;
        while (BoardAnalyzer.hasRemainingShipCells(targetBoard)) {
            originalAI.aiMove(targetBoard);
            turns++;
            if (turns > MAX_TURNS_PER_GAME) {
                throw new IllegalStateException("OriginalGroupAI did not clear the board within " + MAX_TURNS_PER_GAME + " turns in game " + gameNumber + ".");
            }
        }
        return turns;
    }
}
