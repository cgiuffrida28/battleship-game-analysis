package csumb.example.passmessage;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;




public class MainGame extends AppCompatActivity implements View.OnClickListener {
    LinearLayout [] layouts = new LinearLayout[10];
    Button [] [] buttons = new Button[10][10];
    Board playerBoard;
    AI aiBoard;
    boolean won;

    private String [] shipNames = {"Destroyer", "Battleship", "Submarine",  "Carrier", "Patrol Boat"};

    boolean onPlayBoard;

    private Ship aiCarrier;
    private Ship aiBattleship;
    private Ship aiDestroyer;
    private Ship aiSubmarine;
    private Ship aiPatrolBoat;
    private Ship [] aiShips = new Ship[5];
    private Ship [] playerShips;
    public final int DEBUG_SEED = 1;
    private String [] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
    private String [] numbers = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    private ArrayList<Integer> playedSpaces = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.main_game);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        playerBoard = intent.getSerializableExtra("Player Board", Board.class);
        playerShips = intent.getSerializableExtra("Ships Array", Ship[].class);
        won = false;

        layouts[0] = findViewById(R.id.row10);
        layouts[1] = findViewById(R.id.row11);
        layouts[2] = findViewById(R.id.row12);
        layouts[3] = findViewById(R.id.row13);
        layouts[4] = findViewById(R.id.row14);
        layouts[5] = findViewById(R.id.row15);
        layouts[6] = findViewById(R.id.row16);
        layouts[7] = findViewById(R.id.row17);
        layouts[8] = findViewById(R.id.row18);
        layouts[9] = findViewById(R.id.row19);
        for (int row = 0; row < layouts.length; row++) {
            LinearLayout ll = layouts[row];
            for (int col = 0; col < layouts.length; col++) {
                Button b = new Button(this);
                b.setId(View.generateViewId());
                b.setTag(row * 10 + col);
                b.setText(letters[row] + numbers[col]);
                b.setTextSize(13);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        1.0f
                );
                b.setLayoutParams(params);
                b.setBackgroundTintList(ContextCompat.getColorStateList(this, android.R.color.holo_blue_bright));
                ll.addView(b);
                b.setOnClickListener(this::onClick);
                buttons[row][col] = b;
            }
        }

        Button change = findViewById(R.id.view);
        change.setOnClickListener(this::switchView);
        onPlayBoard = false;
        aiBoard = new AI(1);
        randomlyGenerateArray(aiBoard);
        setColors(aiBoard);
    }

    public void switchView(View v) {
        Button b = findViewById(R.id.view);
        if (onPlayBoard) {
            b.setText("Switch To Player Board");
            setColors(aiBoard);
        } else {
            b.setText("Switch To Enemy Board");
            setColors(playerBoard);
        }
        onPlayBoard = !onPlayBoard;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Button button = findViewById(id);
        if (!onPlayBoard && !won) {
            playerTurn(Integer.parseInt(button.getTag().toString()), v);
        }


    }

    private boolean notified = false;
    public void playerTurn(int position, View v) {
        TextView aiSpace = findViewById(R.id.textView);
        TextView notification = findViewById(R.id.textView2);
        TextView win = findViewById(R.id.textView3);
        if (notified) {
            notification.setText("");
            notified = false;
        }
        if (!playedSpaces.contains(position)) {
            int value = aiBoard.getBoard()[position / aiBoard.getBoardSize()][position % aiBoard.getBoardSize()];
            boolean hit = Move.move(position, aiBoard);
            if (hit) {
                Ship s = aiShips[value - 1];
                if (!s.subtractHealth()) {
                    // Notify of sinking
                    notified = true;
                    String name = shipNames[value - 1];
                    notification.setText("AI's " + name + " Has Been Sunk!");
                    if (!aiBoard.subtractMasterHealth()) {
                        // Game Over Player Wins
                        win.setText("Player Wins!!!!!");
                        won = true;
                    }
                }
            }
        } else {
            return;
        }
        playedSpaces.add(position);


        // Ai Turn
        int shot = aiBoard.aiMove(playerBoard);
        if (shot != 0 && shot != 7 && shot != 6) {
            Ship s = playerShips[shot - 1];
            aiSpace.setText("AI Hit!");
            if (!s.subtractHealth()) {
                notified = true;
                String name = shipNames[shot - 1];
                notification.setText("Player's " + name + " Has Been Sunk!");
                if (!playerBoard.subtractMasterHealth()) {
                    // AI Wins
                    win.setText("AI WINS!!!!");
                    won = true;
                }

            }
//            playerHealth--;
//            if (playerHealth <= 0) {
//                win.setText("AI WINS!!!!");
//            }
        } else {
            aiSpace.setText("AI Missed!");
        }

        if (!onPlayBoard) {
            setColors(aiBoard);
        } else {
            setColors(playerBoard);
        }

        if (won) {

            LinearLayout l = findViewById(R.id.row20);
            Button b = new Button(this);
            b.setText("Start New Game!");
            l.addView(b);
            b.setOnClickListener(this::newGame);

            Button b2 = new Button(this);
            b2.setText("Return To Main Menu!");
            l.addView(b2);
            b2.setOnClickListener(this::mainMenu);

        }
    }

    public void mainMenu(View v) {
        Intent intent = new Intent(MainGame.this, MainMenu.class);
        startActivity(intent);
        finish();
    }

    public void newGame(View v) {
        Intent intent = new Intent(MainGame.this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    public void randomlyGenerateArray(Board board) {
        Random r = new Random(1);
        for (int i = 0; i < aiShips.length; i++) {
            boolean orientation = r.nextBoolean();
            int place = r.nextInt() % 100;
            if (i == 3) {
                aiCarrier = new Carrier(orientation, place);
                aiShips[3] = aiCarrier;
                boolean valid = board.addShip(aiCarrier);
                if (!valid) {
                    i--;
                }
            } else if (i == 1) {
                aiBattleship = new Battleship(orientation, place);
                aiShips[1] = aiBattleship;
                boolean valid = board.addShip(aiBattleship);
                if (!valid) {
                    i--;
                }
            } else if (i == 0) {
                aiDestroyer = new Destroyer(orientation, place);
                aiShips[0] = aiDestroyer;
                boolean valid = board.addShip(aiDestroyer);
                if (!valid) {
                    i--;
                }
            } else if (i == 2) {
                aiSubmarine = new Submarine(orientation, place);
                aiShips[2] = aiSubmarine;
                boolean valid = board.addShip(aiSubmarine);
                if (!valid) {
                    i--;
                }
            } else {
                aiPatrolBoat = new PatrolBoat(orientation, place);
                aiShips[4] = aiPatrolBoat;
                boolean valid = board.addShip(aiPatrolBoat);
                if (!valid) {
                    i--;
                }
            }
        }
    }

    public void setColors(Board b) {
        int [] [] board = b.getBoard();
        if (b.getClass() == AI.class) {
            for (int row = 0; row < buttons.length; row++) {
                for (int col = 0; col < buttons[0].length; col++) {

                    if (board[row][col] == 6) {
                        buttons[row][col].setBackgroundTintList(ContextCompat.getColorStateList(this, android.R.color.white));
                    }
                    else if (board[row][col] == 7) {
                        buttons[row][col].setBackgroundTintList(ContextCompat.getColorStateList(this, android.R.color.holo_green_light));
                    }
                    else if (board[row][col] <= 5) {
                        buttons[row][col].setBackgroundTintList(ContextCompat.getColorStateList(this, android.R.color.holo_red_light));
                    }
                }
            }
        } else {
            for (int row = 0; row < buttons.length; row++) {
                for (int col = 0; col < buttons[0].length; col++) {
                    if (board[row][col] == 1 || board[row][col] == 2 || board[row][col] == 3 || board[row][col] == 4 || board[row][col] == 5) {
                        buttons[row][col].setBackgroundTintList(ContextCompat.getColorStateList(this, android.R.color.darker_gray));
                    }
                    else if (board[row][col] == 0) {
                        buttons[row][col].setBackgroundTintList(ContextCompat.getColorStateList(this, android.R.color.holo_blue_bright));
                    }
                    else if (board[row][col] == 6) {
                        buttons[row][col].setBackgroundTintList(ContextCompat.getColorStateList(this, android.R.color.white));
                    }
                    else if (board[row][col] == 7) {
                        buttons[row][col].setBackgroundTintList(ContextCompat.getColorStateList(this, android.R.color.holo_red_light));
                    }
                }
            }
        }
    }
}


