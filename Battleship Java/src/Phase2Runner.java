import java.io.IOException;
import java.util.List;

public class Phase2Runner {
    public static void main(String[] args) throws IOException {
        final int numberOfGames = 1000;
        final String csvFilePath = "phase2_random_ai_results.csv";
        BattleshipAI ai = new RandomAI(42);
        SimulationRunner runner = new SimulationRunner();
        List<SimulationResult> results = runner.runFixedBoardGames(ai, numberOfGames);
        SimulationStats stats = SimulationStats.fromResults(results);
        System.out.println("Phase 2: RandomAI fixed-board benchmark");
        System.out.println("---------------------------------------");
        System.out.println(stats.toDisplayString());
        CsvExporter.exportResults(results, csvFilePath);
        System.out.println("Saved raw results to: " + csvFilePath);
    }
}
