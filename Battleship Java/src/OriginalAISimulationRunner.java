import java.util.ArrayList;
import java.util.List;

public class OriginalAISimulationRunner {
    private static final int MAX_TURNS_PER_GAME = 1000;

    public List<SimulationResult> runFixedBoardGames(int baseSeed, int numberOfGames) {
        if (numberOfGames <= 0) {
            throw new IllegalArgumentException("Number of games must be positive.");
        }
        List<SimulationResult> results = new ArrayList<>();
        for (int gameNumber = 1; gameNumber <= numberOfGames; gameNumber++) {
            Board targetBoard = FixedBoardFactory.createFixedBoard();
            AI originalAI = new AI(baseSeed + gameNumber);
            int turnsToWin = runSingleGame(originalAI, targetBoard, gameNumber);
            results.add(new SimulationResult("OriginalGroupAI", gameNumber, turnsToWin));
        }
        return results;
    }

    private int runSingleGame(AI originalAI, Board targetBoard, int gameNumber) {
        int turns = 0;
        while (BoardAnalyzer.hasRemainingShipCells(targetBoard)) {
            originalAI.aiMove(targetBoard);
            turns++;
            if (turns > MAX_TURNS_PER_GAME) {
                throw new IllegalStateException(
                        "Original AI did not clear the board within " + MAX_TURNS_PER_GAME +
                        " turns in game " + gameNumber + "."
                );
            }
        }
        return turns;
    }
}
