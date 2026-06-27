import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Phase5Runner {
    public static void main(String[] args) throws IOException {
        // Use 1000 while developing. Use 10000 for final README/resume results.
        final int numberOfGames = 10000;
        final int aiSeed = 42;
        final int boardSeed = 2026;
        final String csvFilePath = "phase5_random_board_ai_comparison_results.csv";
        RandomBoardSimulationRunner runner = new RandomBoardSimulationRunner();
        List<SimulationResult> allResults = new ArrayList<>();
        BattleshipAI randomAI = new RandomAI(aiSeed);
        BattleshipAI huntTargetAI = new HuntTargetAI(aiSeed);
        BattleshipAI probabilityMapAI = new ProbabilityMapAI(aiSeed);
        List<SimulationResult> randomResults = runner.runRandomBoardGames(randomAI, numberOfGames, boardSeed);
        List<SimulationResult> huntTargetResults = runner.runRandomBoardGames(huntTargetAI, numberOfGames, boardSeed);
        List<SimulationResult> originalResults = runner.runOriginalAIRandomBoardGames(aiSeed, numberOfGames, boardSeed);
        List<SimulationResult> probabilityMapResults = runner.runRandomBoardGames(probabilityMapAI, numberOfGames, boardSeed);
        allResults.addAll(randomResults);
        allResults.addAll(huntTargetResults);
        allResults.addAll(originalResults);
        allResults.addAll(probabilityMapResults);
        SimulationStats randomStats = SimulationStats.fromResults(randomResults);
        SimulationStats huntTargetStats = SimulationStats.fromResults(huntTargetResults);
        SimulationStats originalStats = SimulationStats.fromResults(originalResults);
        SimulationStats probabilityMapStats = SimulationStats.fromResults(probabilityMapResults);
        System.out.println("Phase 5: Random-board AI benchmark");
        System.out.println("----------------------------------");
        System.out.println("Each strategy plays the same " + numberOfGames + " randomly generated boards.");
        System.out.println();
        System.out.println(randomStats.toDisplayString());
        System.out.println(huntTargetStats.toDisplayString());
        System.out.println(originalStats.toDisplayString());
        System.out.println(probabilityMapStats.toDisplayString());
        System.out.println(buildComparisonSummary("HuntTargetAI", randomResults, huntTargetResults));
        System.out.println(buildComparisonSummary("OriginalGroupAI", randomResults, originalResults));
        System.out.println(buildComparisonSummary("ProbabilityMapAI", randomResults, probabilityMapResults));
        System.out.println(buildHeadToHeadSummary("ProbabilityMapAI", probabilityMapResults, "HuntTargetAI", huntTargetResults));
        System.out.println(buildHeadToHeadSummary("ProbabilityMapAI", probabilityMapResults, "OriginalGroupAI", originalResults));
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
