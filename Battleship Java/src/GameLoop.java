import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class GameLoop {
    private Board playerBoard;
    private AI aiBoard;
    private ArrayList<Integer> playedSpaces = new ArrayList<>();
    private Ship carrier;
    private Ship battleship;
    private Ship destroyer;
    private Ship submarine;
    private Ship patrolBoat;

    private Ship aiCarrier;
    private Ship aiBattleship;
    private Ship aiDestroyer;
    private Ship aiSubmarine;
    private Ship aiPatrolBoat;


    private Ship [] ships = new Ship[5];
    private Ship [] aiShips = new Ship[5];
    private String [] shipNames = {"Destroyer", "Battleship", "Submarine",  "Carrier", "Patrol Boat"};
    public final int DEBUG_SEED = 1;

    private Scanner in;


    public GameLoop(Scanner in) {
        playerBoard = new Board();
        aiBoard = new AI(DEBUG_SEED);
        this.in = in;
    }

    public void setUpBoard() throws IOException {
//        System.out.print("Would you like to randomly generate a board (y/n)?");
//        String random = s.next();
//        if (random.equalsIgnoreCase("y")) {
//            randomlyGenerateArray();
//            return;
//        }
//        FileReader fr = new FileReader("C:/Users/dougi_rlte0ci/IdeaProjects/InClassNotes/CST-338-Battleship-Project/Battleship Java/src/testScript.txt");
//        Scanner fs = new Scanner(fr);
        for (int i = 0; i < 5; i++) {
            playerBoard.printBoard();
            System.out.printf("Would you like the %s to be vertical (y/n)?: ", shipNames[i]);
//            String position = fs.next();
            String position = in.next();
            boolean orientation;
            if (position.equalsIgnoreCase("y")) {
                orientation = true;
            } else {
                orientation = false;
            }
            System.out.print("Enter a location: ");
//            int place = fs.nextInt();
            int place = in.nextInt();
            if (i == 3) {
                carrier = new Carrier(orientation, place);
                ships[3] = carrier;
                boolean valid = playerBoard.addShip(carrier);
                if (!valid) {
                    System.out.println("Invalid Ship Placement!");
                    i--;
                }
            } else if (i == 1) {
                battleship = new Battleship(orientation, place);
                ships[1] = battleship;
                boolean valid = playerBoard.addShip(battleship);
                if (!valid) {
                    System.out.println("Invalid Ship Placement!");
                    i--;
                }
            } else if (i == 0) {
                destroyer = new Destroyer(orientation, place);
                ships[0] = destroyer;
                boolean valid = playerBoard.addShip(destroyer);
                if (!valid) {
                    System.out.println("Invalid Ship Placement!");
                    i--;
                }
            } else if (i == 2) {
                submarine = new Submarine(orientation, place);
                ships[2] = submarine;
                boolean valid = playerBoard.addShip(submarine);
                if (!valid) {
                    System.out.println("Invalid Ship Placement!");
                    i--;
                }
            } else{
                patrolBoat = new PatrolBoat(orientation, place);
                ships[4] = patrolBoat;
                boolean valid = playerBoard.addShip(patrolBoat);
                if (!valid) {
                    System.out.println("Invalid Ship Placement!");
                    i--;
                }
            }
        }
    }

    public void randomlyGenerateArray(Board board) {
        Random r = new Random(DEBUG_SEED);
        for (int i = 0; i < aiShips.length; i++) {
            boolean orientation = r.nextBoolean();
            int place = r.nextInt() % 100;
            if (i == 3) {
                aiCarrier = new Carrier(orientation, place);
                aiShips[3] = aiCarrier;
                boolean valid = board.addShip(aiCarrier);
                if (!valid) {
//                    System.out.println("Invalid Ship Placement!");
                    i--;
                }
            } else if (i == 1) {
                aiBattleship = new Battleship(orientation, place);
                aiShips[1] = aiBattleship;
                boolean valid = board.addShip(aiBattleship);
                if (!valid) {
//                    System.out.println("Invalid Ship Placement!");
                    i--;
                }
            } else if (i == 0) {
                aiDestroyer = new Destroyer(orientation, place);
                aiShips[0] = aiDestroyer;
                boolean valid = board.addShip(aiDestroyer);
                if (!valid) {
//                    System.out.println("Invalid Ship Placement!");
                    i--;
                }
            } else if (i == 2) {
                aiSubmarine = new Submarine(orientation, place);
                aiShips[2] = aiSubmarine;
                boolean valid = board.addShip(aiSubmarine);
                if (!valid) {
//                    System.out.println("Invalid Ship Placement!");
                    i--;
                }
            } else {
                aiPatrolBoat = new PatrolBoat(orientation, place);
                aiShips[4] = aiPatrolBoat;
                boolean valid = board.addShip(aiPatrolBoat);
                if (!valid) {
//                    System.out.println("Invalid Ship Placement!");
                    i--;
                }
            }
        }
    }

    public Board getPlayerBoard() {
        return playerBoard;
    }


    public boolean mainGame() throws IOException {
        setUpBoard();
        randomlyGenerateArray(aiBoard);
        System.out.println("ENEMY BOARD: ");
//        in = new Scanner(System.in);
        while(true){

            // PLAYER'S TURN
            aiBoard.printEnemyBoard();
            System.out.println();
            playerBoard.printBoard();
            System.out.print("Player, input a position to fire at: ");
            int input;
            do{
                input = in.nextInt();
                if (!playedSpaces.contains(input)) {
                    playedSpaces.add(input);
                    break;
                }
            } while(true);
            int value = aiBoard.getBoard()[input / aiBoard.getBoardSize()][input % aiBoard.getBoardSize()];
            if(Move.move(input, aiBoard)){
                System.out.println("Player Hit!");
                Ship s = aiShips[value - 1];
                if (!s.subtractHealth()) {
                    System.out.println("ENEMY'S " + s + " HAS BEEN SUNK!");
                    if(!aiBoard.subtractMasterHealth()) {
                        System.out.println("GAME OVER, PLAYER WINS!");
                        return true;
//                        break;
                    }
                }
            } else {
                System.out.println("Player Missed.");
            }

            // AI'S TURN
            System.out.println("AI's Turn!");
            int shot = aiBoard.aiMove(playerBoard);
            if(shot != 0 && shot != 7 && shot != 6){
                System.out.println("AI hit.");
                Ship s = ships[shot - 1];
                if (!s.subtractHealth()) {
                    System.out.println("PLAYERS'S " + s + " HAS BEEN SUNK!");
                    if(!playerBoard.subtractMasterHealth()) {
                        System.out.println("GAME OVER, AI WINS!");
                        return false;
//                        break;
                    }
                }
            } else {
                System.out.println("AI Missed.");
            }
        }
    }
}
