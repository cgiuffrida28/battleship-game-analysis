package csumb.example.passmessage;

import java.io.Serializable;

public class Board implements Serializable {

    private int[][] board;
    private int boardSize;
    private int masterHealth;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";

    public final int DEFAULT_SIZE = 10;



    public Board(){
        board = new int[DEFAULT_SIZE][DEFAULT_SIZE];
        boardSize = DEFAULT_SIZE;
        masterHealth = 5;
    }

    public Board(int size){
        board = new int[size][size];
        boardSize = size;
        masterHealth = 5;
    }

    public boolean addShip(Ship s) {
        int row = s.getPosition() / boardSize;
        int col = s.getPosition() % boardSize;
        try {
            //VERTICAL
            if (s.getVertical()) {
                for(int x = 0; x < s.LENGTH; x++){
                    if(board[row][col] != 0){
                        for(; x >= 0; x--){
//                            System.out.println("VERTICAL FIX");
//                            System.out.println("Does The Value: " + s.VALUE + ", Equal the space?: " + board[row][col]);
                            if (board[row][col] == s.VALUE) {
//                                System.out.println("Resetting Space");
                                board[row][col] = 0;
                            }
                            row--;
                        }
                        return false;
                    }
                    board[row++][col] = s.VALUE;
                }
//          HORIZONTAL
            } else {
                for (int i = 0; i < s.LENGTH; i++) {
                    if(board[row][col] != 0){
                        for(; i >= 0; i--){
//                            System.out.println("HORIZONTAL FIX");
//                            System.out.println("Does The Value: " + s.VALUE + ", Equal the space?: " + board[row][col]);
                            if (board[row][col] == s.VALUE) {
//                                System.out.println("Resetting Space");
                                board[row][col] = 0;
                            }
                            col--;
                        }
                        return false;
                    }
                    board[row][col++] = s.VALUE;
                }
            }
            return true;
        } catch(ArrayIndexOutOfBoundsException e) {
            for(int x = 0; x < board.length; x++){
                for(int y = 0; y < board[x].length; y++){
                    if(board[x][y] == s.VALUE){
                        board[x][y] = 0;
                    }
                }
            }
            return false;
        }
    }

    public int[][] getBoard(){
        return this.board;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public void printBoard() {
        for (int i = 0; i < boardSize; i++) {
            for (int k = 0; k < boardSize; k++) {
                if (board[i][k] == 0) {
                    System.out.print(ANSI_GREEN + "[ " + board[i][k] + " ]" + ANSI_RESET);
                } else if (board[i][k] == 7){
                    System.out.print(ANSI_GREEN + "[ " + ANSI_RED + board[i][k] + ANSI_GREEN + " ]" + ANSI_RESET);
                } else if (board[i][k] == 6){
                    System.out.print(ANSI_GREEN + "[ " + ANSI_BLUE + board[i][k] + ANSI_GREEN + " ]" + ANSI_RESET);
                }
                else {
                    System.out.print(ANSI_GREEN + "[ " + ANSI_YELLOW + board[i][k] + ANSI_GREEN + " ]" + ANSI_RESET);
                }
            }
            System.out.println();
        }
    }

    public void printEnemyBoard() {
        for (int i = 0; i < boardSize; i++) {
            for (int k = 0; k < boardSize; k++) {
                if (board[i][k] == 0) {
                    System.out.print(ANSI_RED + "[ " + board[i][k] + " ]" + ANSI_RESET);
                } else if (board[i][k] == 6) {
                    System.out.print(ANSI_RED + "[ " + ANSI_YELLOW + board[i][k] + ANSI_RED + " ]" + ANSI_RESET);
                } else if (board[i][k] == 7){
                    System.out.print(ANSI_RED + "[ " + ANSI_GREEN + board[i][k] + ANSI_RED + " ]" + ANSI_RESET);
                } else {
                    System.out.print(ANSI_RED + "[ 0 ]" + ANSI_RESET);
                }
            }
            System.out.println();
        }
    }

    public boolean checkMasterHealth() {
        if (masterHealth <= 0) {
            return false;
        }
        return true;
    }

    public boolean subtractMasterHealth() {
        masterHealth--;
        return checkMasterHealth();
    }
}
