import java.util.Random;

public class RandomBoardFactory {
    private static final int BOARD_CELLS = 100;
    private static final int MAX_PLACEMENT_ATTEMPTS_PER_SHIP = 1000;

    public static Board createRandomBoard(long seed) {
        return createRandomBoard(new Random(seed));
    }

    public static Board createRandomBoard(Random random) {
        Board board = new Board();
        placeShip(board, ShipType.CARRIER, random);
        placeShip(board, ShipType.BATTLESHIP, random);
        placeShip(board, ShipType.DESTROYER, random);
        placeShip(board, ShipType.SUBMARINE, random);
        placeShip(board, ShipType.PATROL_BOAT, random);
        return board;
    }

    private static void placeShip(Board board, ShipType shipType, Random random) {
        for (int attempt = 0; attempt < MAX_PLACEMENT_ATTEMPTS_PER_SHIP; attempt++) {
            boolean vertical = random.nextBoolean();
            int position = random.nextInt(BOARD_CELLS);
            Ship ship = createShip(shipType, vertical, position);
            if (board.addShip(ship)) {
                return;
            }
        }
        throw new IllegalStateException("Could not place " + shipType + " after " + MAX_PLACEMENT_ATTEMPTS_PER_SHIP + " attempts.");
    }

    private static Ship createShip(ShipType shipType, boolean vertical, int position) {
        if (shipType == ShipType.CARRIER) {
            return new Carrier(vertical, position);
        }
        if (shipType == ShipType.BATTLESHIP) {
            return new Battleship(vertical, position);
        }
        if (shipType == ShipType.DESTROYER) {
            return new Destroyer(vertical, position);
        }
        if (shipType == ShipType.SUBMARINE) {
            return new Submarine(vertical, position);
        }
        if (shipType == ShipType.PATROL_BOAT) {
            return new PatrolBoat(vertical, position);
        }
        throw new IllegalArgumentException("Unsupported ship type: " + shipType);
    }

    private enum ShipType {
        CARRIER,
        BATTLESHIP,
        DESTROYER,
        SUBMARINE,
        PATROL_BOAT
    }
}
