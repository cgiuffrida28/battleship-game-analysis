import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class CsvExporter {
    public static void exportResults(List<SimulationResult> results, String filePath) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            writer.println("ai_strategy,game_number,turns_to_win");

            for (SimulationResult result : results) {
                writer.println(
                        result.getAiName() + "," +
                        result.getGameNumber() + "," +
                        result.getTurnsToWin()
                );
            }
        }
    }
}
