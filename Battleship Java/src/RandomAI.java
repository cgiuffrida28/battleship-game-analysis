import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RandomAI implements BattleshipAI {
    private final Random random;
    private final List<Integer> availableMoves;

    public RandomAI(long seed) {
        this.random = new Random(seed);
        this.availableMoves = new ArrayList<>();
        reset();
    }

    @Override
    public String getName() {
        return "RandomAI";
    }

    @Override
    public int chooseMove(Board targetBoard) {
        if (availableMoves.isEmpty()) {
            throw new IllegalStateException("No moves remaining.");
        }
        return availableMoves.remove(availableMoves.size() - 1);
    }

    @Override
    public void reset() {
        availableMoves.clear();
        for (int i = 0; i < 100; i++) {
            availableMoves.add(i);
        }
        Collections.shuffle(availableMoves, random);
    }
}