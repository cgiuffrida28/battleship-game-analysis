public class FixedBoardFactory {
    public static Board createFixedBoard() {
        Board board = new Board();
        // Fixed layout for Phase 1 testing.
        board.addShip(new Destroyer(false, 0));    // 0, 1, 2
        board.addShip(new Battleship(false, 20));  // 20, 21, 22, 23
        board.addShip(new Submarine(true, 50));    // 50, 60, 70
        board.addShip(new Carrier(false, 35));     // 35, 36, 37, 38, 39
        board.addShip(new PatrolBoat(true, 84));   // 84, 94
        return board;
    }
}