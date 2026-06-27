package csumb.example.passmessage;

public class Move {
    private int location;

    public static boolean move(int location, Board board) {
        int row = location / board.getBoardSize();
        int col = location % board.getBoardSize();
        if(board.getBoard()[row][col] == 0){
            board.getBoard()[row][col] = 6;
            return false;
        }else if (board.getBoard()[row][col] == 7 || board.getBoard()[row][col] == 6) {
                     return false;
        } else if (board.getBoard()[row][col] != 6 ){
            board.getBoard()[row][col] = 7;
            return true;
        }
        return false;
    }
}

