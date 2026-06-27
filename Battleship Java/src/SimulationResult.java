public class SimulationResult {
    private final String aiName;
    private final int gameNumber;
    private final int turnsToWin;

    public SimulationResult(String aiName, int gameNumber, int turnsToWin) {
        this.aiName = aiName;
        this.gameNumber = gameNumber;
        this.turnsToWin = turnsToWin;
    }

    public String getAiName() {
        return aiName;
    }

    public int getGameNumber() {
        return gameNumber;
    }

    public int getTurnsToWin() {
        return turnsToWin;
    }
}
