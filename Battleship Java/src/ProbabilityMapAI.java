import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class ProbabilityMapAI implements BattleshipAI {
    private static final int MISS = 6;
    private static final int HIT = 7;

    // Standard Battleship ship lengths used by this project:
    // Carrier, Battleship, Destroyer, Submarine, Patrol Boat.
    private final int[] shipLengths = {5, 4, 3, 3, 2};
    private final Random random;
    private final Set<Integer> playedMoves;

    public ProbabilityMapAI(long seed) {
        this.random = new Random(seed);
        this.playedMoves = new HashSet<>();
    }

    @Override
    public String getName() {
        return "ProbabilityMapAI";
    }

    @Override
    public int chooseMove(Board targetBoard) {
        int[][] probabilityMap = buildProbabilityMap(targetBoard);
        List<Integer> bestMoves = new ArrayList<>();
        int bestScore = -1;
        int boardSize = targetBoard.getBoardSize();
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                if (!isLegalUnplayedMove(row, col, targetBoard)) {
                    continue;
                }
                int score = probabilityMap[row][col];
                int location = row * boardSize + col;
                if (score > bestScore) {
                    bestScore = score;
                    bestMoves.clear();
                    bestMoves.add(location);
                } else if (score == bestScore) {
                    bestMoves.add(location);
                }
            }
        }

        if (bestMoves.isEmpty()) {
            throw new IllegalStateException("No legal moves remaining.");
        }
        int chosenMove = bestMoves.get(random.nextInt(bestMoves.size()));
        playedMoves.add(chosenMove);
        return chosenMove;
    }

    @Override
    public void reset() {
        playedMoves.clear();
    }

    public int[][] buildProbabilityMap(Board targetBoard) {
        int boardSize = targetBoard.getBoardSize();
        int[][] probabilityMap = new int[boardSize][boardSize];
        boolean hasKnownHit = hasKnownHit(targetBoard);
        for (int shipLength : shipLengths) {
            addHorizontalPlacements(targetBoard, probabilityMap, shipLength, hasKnownHit);
            addVerticalPlacements(targetBoard, probabilityMap, shipLength, hasKnownHit);
        }
        return probabilityMap;
    }

    private void addHorizontalPlacements(Board targetBoard, int[][] probabilityMap, int shipLength, boolean hasKnownHit) {
        int boardSize = targetBoard.getBoardSize();
        for (int row = 0; row < boardSize; row++) {
            for (int startCol = 0; startCol <= boardSize - shipLength; startCol++) {
                addPlacementIfValid(targetBoard, probabilityMap, row, startCol, 0, 1, shipLength, hasKnownHit);
            }
        }
    }


    private void addVerticalPlacements(Board targetBoard, int[][] probabilityMap, int shipLength, boolean hasKnownHit) {
        int boardSize = targetBoard.getBoardSize();
        for (int startRow = 0; startRow <= boardSize - shipLength; startRow++) {
            for (int col = 0; col < boardSize; col++) {
                addPlacementIfValid(targetBoard, probabilityMap, startRow, col, 1, 0, shipLength, hasKnownHit);
            }
        }
    }

    private void addPlacementIfValid(
            Board targetBoard,
            int[][] probabilityMap,
            int startRow,
            int startCol,
            int rowStep,
            int colStep,
            int shipLength,
            boolean hasKnownHit
    ) {
        int hitCount = 0;

        for (int offset = 0; offset < shipLength; offset++) {
            int row = startRow + offset * rowStep;
            int col = startCol + offset * colStep;
            int cellValue = targetBoard.getBoard()[row][col];
            if (cellValue == MISS) {
                return;
            }
            if (cellValue == HIT) {
                hitCount++;
            }
        }
        int placementWeight = 1;
        if (hasKnownHit && hitCount > 0) {
            placementWeight += hitCount * 12;
        }
        for (int offset = 0; offset < shipLength; offset++) {
            int row = startRow + offset * rowStep;
            int col = startCol + offset * colStep;
            if (targetBoard.getBoard()[row][col] != HIT) {
                probabilityMap[row][col] += placementWeight;
            }
        }
    }

    private boolean hasKnownHit(Board targetBoard) {
        int boardSize = targetBoard.getBoardSize();
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                if (targetBoard.getBoard()[row][col] == HIT) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isLegalUnplayedMove(int row, int col, Board targetBoard) {
        int boardSize = targetBoard.getBoardSize();
        if (row < 0 || row >= boardSize || col < 0 || col >= boardSize) {
            return false;
        }
        int location = row * boardSize + col;
        if (playedMoves.contains(location)) {
            return false;
        }
        int cellValue = targetBoard.getBoard()[row][col];
        return cellValue != MISS && cellValue != HIT;
    }
}
