

import java.util.ArrayList;
import java.util.Random;
public class AI extends Board {
    private Random random;
    private boolean targeting = false;
    private int targetDirection;
    private int originValue = -1;
    private int currentTarget;
    private int targetOrigin;
    private ArrayList<Integer> playedSpaces = new ArrayList<>();

    public AI() {
        random = new Random();
    }

    public AI(int seed) {
        random = new Random(seed);
    }

    public int aiMove(Board board) {
        int target;
        if (!targeting) {
            do{
                target = Math.abs(random.nextInt() % (board.getBoardSize() * board.getBoardSize()));
//                System.out.println("Random Target: " + target);
                if (!playedSpaces.contains(target)) {
                    playedSpaces.add(target);
                    break;
                }
            } while(true);
            int check = board.getBoard()[target / board.getBoardSize()][target % getBoardSize()];

            if(Move.move(target, board)){
                originValue = check;
                targetOrigin = target;
                currentTarget = target;
                this.targeting = true;
                targetDirection = 0;
                return check;
            } else {
                return check;
            }
        }
        else {
            int onBoard = board.getBoard()[currentTarget / board.getBoardSize()][currentTarget % board.getBoardSize()];
            boolean move = false;
            int onBoard2 = 0;



            try{
                if (targetDirection == 0) {
                    onBoard2 = board.getBoard()[(currentTarget - board.getBoardSize()) / board.getBoardSize()][currentTarget - getBoardSize() % board.getBoardSize()];
                    move = Move.move(currentTarget - board.getBoardSize(), board);
                } else if (targetDirection == 1) {
                    onBoard2 = board.getBoard()[(currentTarget + 1) / board.getBoardSize()][(currentTarget + 1) % board.getBoardSize()];
                    move = Move.move(currentTarget + 1, board);
                } else if (targetDirection == 2) {
                    onBoard2 = board.getBoard()[(currentTarget + board.getBoardSize()) / board.getBoardSize() / board.getBoardSize()][(currentTarget + getBoardSize()) % board.getBoardSize()];
                    move = Move.move(currentTarget + board.getBoardSize(), board);
                } else if (targetDirection == 3) {
                    onBoard2 = board.getBoard()[(currentTarget - 1) / board.getBoardSize()][(currentTarget - 1) % board.getBoardSize()];
                    move = Move.move(currentTarget - 1, board);
                }
                if (targetDirection == 0) { // North
                    if (Move.move(currentTarget - board.getBoardSize(), board)) {
                        currentTarget = currentTarget - board.getBoardSize();
                        playedSpaces.add(currentTarget);
                    }
                    else {
                        targetDirection++;
                        currentTarget = targetOrigin;
                        playedSpaces.add(currentTarget);
                        onBoard = board.getBoard()[currentTarget / board.getBoardSize()][currentTarget % board.getBoardSize()];
                        return onBoard;
                    }
                } else if (targetDirection == 1) { // East
                    if (move) {
                        currentTarget = currentTarget + 1;
                        playedSpaces.add(currentTarget);
                    }
                    else {
                        targetDirection++;
                        currentTarget = targetOrigin;
                        playedSpaces.add(currentTarget);

                        onBoard = board.getBoard()[currentTarget / board.getBoardSize()][currentTarget % board.getBoardSize()];
                        return onBoard;
                    }
                } else if (targetDirection == 2) { // South
                    if (move) {
                        currentTarget = currentTarget + board.getBoardSize();
                        playedSpaces.add(currentTarget);

                    }
                    else {
                        targetDirection++;
                        currentTarget = targetOrigin;
                        playedSpaces.add(currentTarget);
                        onBoard = board.getBoard()[currentTarget / board.getBoardSize()][currentTarget % board.getBoardSize()];
                        return onBoard;
                    }
                } else if(targetDirection == 3){ // West
                    if (move) {
                        currentTarget = currentTarget - 1;
                        playedSpaces.add(currentTarget);
                    }
                    else {
                        targetDirection++;
                        currentTarget = targetOrigin;
                        playedSpaces.add(currentTarget);
                        onBoard = board.getBoard()[currentTarget / board.getBoardSize()][currentTarget % board.getBoardSize()];
                        return onBoard;
                    }
                }else{
                    targeting = false;
                    playedSpaces.add(currentTarget);
                    return aiMove(board);
                }
            } catch(ArrayIndexOutOfBoundsException e){
                targetDirection++;
                currentTarget = targetOrigin;
                playedSpaces.add(currentTarget);
                return aiMove(board);
            }


            return onBoard2;
        }
    }



    public boolean getTargeting() {
        return targeting;
    }

    public void setTargeting(boolean targeting) {
        this.targeting = targeting;
    }


}
