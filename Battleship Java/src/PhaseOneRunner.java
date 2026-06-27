public class PhaseOneRunner {
    public static void main(String[] args) {
        Board targetBoard = FixedBoardFactory.createFixedBoard();
        BattleshipAI ai = new RandomAI(42);

        int turns = runSingleGame(ai, targetBoard, true);
        System.out.println();
        System.out.println(ai.getName() + " cleared the fixed board in " + turns + " turns.");
    }

    private static int runSingleGame(BattleshipAI ai, Board targetBoard, boolean printMoves) {
        ai.reset();
        int turns = 0;

        while (BoardAnalyzer.hasRemainingShipCells(targetBoard)) {
            int move = ai.chooseMove(targetBoard);
            int row = move / targetBoard.getBoardSize();
            int col = move % targetBoard.getBoardSize();
            int previousValue = targetBoard.getBoard()[row][col];
            boolean hit = Move.move(move, targetBoard);
            turns++;

            if (printMoves) {
                String result = hit ? "hit ship value " + previousValue : "miss";
                System.out.println(
                        "Turn " + turns +
                                ": fired at " + move +
                                " (row " + row + ", col " + col + ") -> " + result +
                                "; remaining ship cells: " + BoardAnalyzer.countRemainingShipCells(targetBoard)
                );
            }
        }

        return turns;
    }
}