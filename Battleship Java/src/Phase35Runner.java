import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Phase35Runner {
    public static void main(String[] args) throws IOException {
        final int numberOfGames = 1000;
        final int baseSeed = 42;
        final String csvFilePath = "phase35_ai_comparison_results.csv";
        SimulationRunner runner = new SimulationRunner();
        OriginalAISimulationRunner originalRunner = new OriginalAISimulationRunner();
        List<SimulationResult> allResults = new ArrayList<>();
        BattleshipAI randomAI = new RandomAI(baseSeed);
        BattleshipAI huntTargetAI = new HuntTargetAI(baseSeed);
        List<SimulationResult> randomResults = runner.runFixedBoardGames(randomAI, numberOfGames);
        List<SimulationResult> huntTargetResults = runner.runFixedBoardGames(huntTargetAI, numberOfGames);
        List<SimulationResult> originalResults = originalRunner.runFixedBoardGames(baseSeed, numberOfGames);
        allResults.addAll(randomResults);
        allResults.addAll(huntTargetResults);
        allResults.addAll(originalResults);
        SimulationStats randomStats = SimulationStats.fromResults(randomResults);
        SimulationStats huntTargetStats = SimulationStats.fromResults(huntTargetResults);
        SimulationStats originalStats = SimulationStats.fromResults(originalResults);
        System.out.println("Phase 3.5: RandomAI vs HuntTargetAI vs OriginalGroupAI fixed-board benchmark");
        System.out.println("------------------------------------------------------------------------");
        System.out.println(randomStats.toDisplayString());
        System.out.println(huntTargetStats.toDisplayString());
        System.out.println(originalStats.toDisplayString());
        System.out.println(buildComparisonSummary("HuntTargetAI", randomResults, huntTargetResults));
        System.out.println(buildComparisonSummary("OriginalGroupAI", randomResults, originalResults));
        System.out.println(buildHeadToHeadSummary("OriginalGroupAI", originalResults, "HuntTargetAI", huntTargetResults));
        CsvExporter.exportResults(allResults, csvFilePath);
        System.out.println("Saved raw comparison results to: " + csvFilePath);
    }

    private static String buildComparisonSummary(
            String comparisonName,
            List<SimulationResult> baselineResults,
            List<SimulationResult> comparisonResults
    ) {
        double baselineAverage = calculateAverageTurns(baselineResults);
        double comparisonAverage = calculateAverageTurns(comparisonResults);
        double improvement = baselineAverage - comparisonAverage;
        double percentImprovement = improvement / baselineAverage * 100.0;
        if (improvement >= 0) {
            return String.format(
                    "%s improved by %.2f turns on average compared with RandomAI (%.2f%% fewer turns).",
                    comparisonName,
                    improvement,
                    percentImprovement
            );
        }
        return String.format(
                "%s was %.2f turns slower on average than RandomAI (%.2f%% more turns).",
                comparisonName,
                Math.abs(improvement),
                Math.abs(percentImprovement)
        );
    }

    private static String buildHeadToHeadSummary(
            String firstName,
            List<SimulationResult> firstResults,
            String secondName,
            List<SimulationResult> secondResults
    ) {
        double firstAverage = calculateAverageTurns(firstResults);
        double secondAverage = calculateAverageTurns(secondResults);
        double difference = secondAverage - firstAverage;
        double percentDifference = difference / secondAverage * 100.0;
        if (difference > 0) {
            return String.format(
                    "%s was %.2f turns faster on average than %s (%.2f%% fewer turns).",
                    firstName,
                    difference,
                    secondName,
                    percentDifference
            );
        }
        return String.format(
                "%s was %.2f turns slower on average than %s (%.2f%% more turns).",
                firstName,
                Math.abs(difference),
                secondName,
                Math.abs(percentDifference)
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
