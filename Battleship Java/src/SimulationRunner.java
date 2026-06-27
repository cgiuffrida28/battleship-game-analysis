import java.util.ArrayList;
import java.util.List;

public class SimulationRunner {
    public List<SimulationResult> runFixedBoardGames(BattleshipAI ai, int numberOfGames) {
        if (numberOfGames <= 0) {
            throw new IllegalArgumentException("Number of games must be positive.");
        }
        List<SimulationResult> results = new ArrayList<>();
        for (int gameNumber = 1; gameNumber <= numberOfGames; gameNumber++) {
            Board targetBoard = FixedBoardFactory.createFixedBoard();
            int turnsToWin = runSingleGame(ai, targetBoard);
            results.add(new SimulationResult(ai.getName(), gameNumber, turnsToWin));
        }
        return results;
    }

    private int runSingleGame(BattleshipAI ai, Board targetBoard) {
        ai.reset();
        int turns = 0;
        while (BoardAnalyzer.hasRemainingShipCells(targetBoard)) {
            int move = ai.chooseMove(targetBoard);
            Move.move(move, targetBoard);
            turns++;
        }
        return turns;
    }
}
