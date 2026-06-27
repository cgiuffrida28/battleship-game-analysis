public class BoardAnalyzer {
    public static boolean hasRemainingShipCells(Board board) {
        int[][] cells = board.getBoard();
        for (int row = 0; row < board.getBoardSize(); row++) {
            for (int col = 0; col < board.getBoardSize(); col++) {
                int value = cells[row][col];
                if (value >= 1 && value <= 5) {
                    return true;
                }
            }
        }
        return false;
    }

    public static int countRemainingShipCells(Board board) {
        int remaining = 0;
        int[][] cells = board.getBoard();
        for (int row = 0; row < board.getBoardSize(); row++) {
            for (int col = 0; col < board.getBoardSize(); col++) {
                int value = cells[row][col];
                if (value >= 1 && value <= 5) {
                    remaining++;
                }
            }
        }
        return remaining;
    }
}