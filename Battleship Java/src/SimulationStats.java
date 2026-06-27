import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SimulationStats {
    private final String aiName;
    private final int gamesPlayed;
    private final double averageTurns;
    private final double medianTurns;
    private final int bestGame;
    private final int worstGame;
    private final double standardDeviation;

    private SimulationStats(
            String aiName,
            int gamesPlayed,
            double averageTurns,
            double medianTurns,
            int bestGame,
            int worstGame,
            double standardDeviation) {
        this.aiName = aiName;
        this.gamesPlayed = gamesPlayed;
        this.averageTurns = averageTurns;
        this.medianTurns = medianTurns;
        this.bestGame = bestGame;
        this.worstGame = worstGame;
        this.standardDeviation = standardDeviation;
    }

    public static SimulationStats fromResults(List<SimulationResult> results) {
        if (results == null || results.isEmpty()) {
            throw new IllegalArgumentException("Results cannot be empty.");
        }
        String aiName = results.get(0).getAiName();
        List<Integer> turns = new ArrayList<>();
        int sum = 0;
        for (SimulationResult result : results) {
            turns.add(result.getTurnsToWin());
            sum += result.getTurnsToWin();
        }
        Collections.sort(turns);
        double average = sum / (double) turns.size();
        double median;
        int middle = turns.size() / 2;
        if (turns.size() % 2 == 0) {
            median = (turns.get(middle - 1) + turns.get(middle)) / 2.0;
        } else {
            median = turns.get(middle);
        }
        int best = turns.get(0);
        int worst = turns.get(turns.size() - 1);
        double squaredDifferenceSum = 0.0;
        for (int value : turns) {
            double difference = value - average;
            squaredDifferenceSum += difference * difference;
        }
        double standardDeviation = Math.sqrt(squaredDifferenceSum / turns.size());
        return new SimulationStats(
                aiName,
                turns.size(),
                average,
                median,
                best,
                worst,
                standardDeviation
        );
    }

    public String toDisplayString() {
        StringBuilder builder = new StringBuilder();
        builder.append("AI Strategy: ").append(aiName).append("\n");
        builder.append("Games played: ").append(gamesPlayed).append("\n");
        builder.append(String.format("Average turns to win: %.2f\n", averageTurns));
        builder.append(String.format("Median turns to win: %.2f\n", medianTurns));
        builder.append("Best game: ").append(bestGame).append(" turns\n");
        builder.append("Worst game: ").append(worstGame).append(" turns\n");
        builder.append(String.format("Standard deviation: %.2f\n", standardDeviation));
        return builder.toString();
    }
}
