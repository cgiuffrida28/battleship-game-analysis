import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Phase3Runner {
    public static void main(String[] args) throws IOException {
        final int numberOfGames = 1000;
        final String csvFilePath = "phase3_ai_comparison_results.csv";
        SimulationRunner runner = new SimulationRunner();
        List<SimulationResult> allResults = new ArrayList<>();
        BattleshipAI randomAI = new RandomAI(42);
        BattleshipAI huntTargetAI = new HuntTargetAI(42);
        List<SimulationResult> randomResults = runner.runFixedBoardGames(randomAI, numberOfGames);
        List<SimulationResult> huntTargetResults = runner.runFixedBoardGames(huntTargetAI, numberOfGames);
        allResults.addAll(randomResults);
        allResults.addAll(huntTargetResults);
        SimulationStats randomStats = SimulationStats.fromResults(randomResults);
        SimulationStats huntTargetStats = SimulationStats.fromResults(huntTargetResults);
        System.out.println("Phase 3: RandomAI vs HuntTargetAI fixed-board benchmark");
        System.out.println("-------------------------------------------------------");
        System.out.println(randomStats.toDisplayString());
        System.out.println(huntTargetStats.toDisplayString());
        System.out.println(buildComparisonSummary(randomResults, huntTargetResults));
        CsvExporter.exportResults(allResults, csvFilePath);
        System.out.println("Saved raw comparison results to: " + csvFilePath);
    }

    private static String buildComparisonSummary(List<SimulationResult> baselineResults, List<SimulationResult> improvedResults) {
        double baselineAverage = calculateAverageTurns(baselineResults);
        double improvedAverage = calculateAverageTurns(improvedResults);
        double improvement = baselineAverage - improvedAverage;
        double percentImprovement = improvement / baselineAverage * 100.0;
        return String.format(
                "HuntTargetAI improved by %.2f turns on average compared with RandomAI (%.2f%% fewer turns).\n",
                improvement,
                percentImprovement
        );
    }

    private static double calculateAverageTurns(List<SimulationResult> results) {
        int total = 0;
        for (SimulationResult result : results) {
            total += result.getTurnsToWin();
        }
        return total / (double) results.size();
    }
}
