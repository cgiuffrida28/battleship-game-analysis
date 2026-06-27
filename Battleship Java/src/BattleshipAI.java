public interface BattleshipAI {
    String getName();
    int chooseMove(Board targetBoard);
    void reset();
}