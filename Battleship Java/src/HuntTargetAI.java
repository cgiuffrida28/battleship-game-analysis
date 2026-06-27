import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

public class HuntTargetAI implements BattleshipAI {
    private final Random random;
    private final List<Integer> huntMoves;
    private final Queue<Integer> targetQueue;
    private final Set<Integer> playedMoves;
    private int lastMove;

    public HuntTargetAI(long seed) {
        this.random = new Random(seed);
        this.huntMoves = new ArrayList<>();
        this.targetQueue = new ArrayDeque<>();
        this.playedMoves = new HashSet<>();
        reset();
    }

    @Override
    public String getName() {
        return "HuntTargetAI";
    }

    @Override
    public int chooseMove(Board targetBoard) {
        updateAfterPreviousShot(targetBoard);
        Integer queuedTarget = getNextQueuedTarget(targetBoard);
        int chosenMove;
        if (queuedTarget != null) {
            chosenMove = queuedTarget;
        } else {
            chosenMove = getNextHuntMove(targetBoard);
        }
        playedMoves.add(chosenMove);
        lastMove = chosenMove;
        return chosenMove;
    }

    @Override
    public void reset() {
        huntMoves.clear();
        targetQueue.clear();
        playedMoves.clear();
        lastMove = -1;
        for (int i = 0; i < 100; i++) {
            huntMoves.add(i);
        }
        Collections.shuffle(huntMoves, random);
    }

    private void updateAfterPreviousShot(Board targetBoard) {
        if (lastMove == -1) {
            return;
        }
        int row = lastMove / targetBoard.getBoardSize();
        int col = lastMove % targetBoard.getBoardSize();
        int cellValue = targetBoard.getBoard()[row][col];
        if (cellValue == 7) {
            addNeighborTargets(lastMove, targetBoard);
        }
        lastMove = -1;
    }

    private void addNeighborTargets(int location, Board targetBoard) {
        int boardSize = targetBoard.getBoardSize();
        int row = location / boardSize;
        int col = location % boardSize;
        addIfLegalTarget(row - 1, col, targetBoard); // north
        addIfLegalTarget(row, col + 1, targetBoard); // east
        addIfLegalTarget(row + 1, col, targetBoard); // south
        addIfLegalTarget(row, col - 1, targetBoard); // west
    }

    private void addIfLegalTarget(int row, int col, Board targetBoard) {
        int boardSize = targetBoard.getBoardSize();
        if (row < 0 || row >= boardSize || col < 0 || col >= boardSize) {
            return;
        }
        int location = row * boardSize + col;
        if (playedMoves.contains(location) || targetQueue.contains(location)) {
            return;
        }
        int cellValue = targetBoard.getBoard()[row][col];
        if (cellValue == 6 || cellValue == 7) {
            return;
        }
        targetQueue.add(location);
    }

    private Integer getNextQueuedTarget(Board targetBoard) {
        while (!targetQueue.isEmpty()) {
            int location = targetQueue.remove();
            if (isLegalUnplayedMove(location, targetBoard)) {
                return location;
            }
        }
        return null;
    }

    private int getNextHuntMove(Board targetBoard) {
        while (!huntMoves.isEmpty()) {
            int location = huntMoves.remove(huntMoves.size() - 1);
            if (isLegalUnplayedMove(location, targetBoard)) {
                return location;
            }
        }

        throw new IllegalStateException("No legal moves remaining.");
    }

    private boolean isLegalUnplayedMove(int location, Board targetBoard) {
        int boardSize = targetBoard.getBoardSize();
        int row = location / boardSize;
        int col = location % boardSize;
        if (location < 0 || location >= boardSize * boardSize) {
            return false;
        }
        if (playedMoves.contains(location)) {
            return false;
        }
        int cellValue = targetBoard.getBoard()[row][col];
        return cellValue != 6 && cellValue != 7;
    }
}
