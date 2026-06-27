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

import java.io.IOException;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button [] [] buttons = new Button[10][10];
    LinearLayout [] layouts = new LinearLayout[10];
    public Scanner in = new Scanner(System.in);
    boolean orientation = true;
    private String [] letters = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
    private String [] numbers = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
    int count = 0;
    Ship [] ships = new Ship[5];
    Board playerBoard;

    public GameLoop g;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        playerBoard = new Board();
        count = 0;
        orientation = true;
        ships = new Ship[5];
        buttons = new Button[10][10];
        layouts = new LinearLayout[10];


        layouts[0] = findViewById(R.id.row0);
        layouts[1] = findViewById(R.id.row1);
        layouts[2] = findViewById(R.id.row2);
        layouts[3] = findViewById(R.id.row3);
        layouts[4] = findViewById(R.id.row4);
        layouts[5] = findViewById(R.id.row5);
        layouts[6] = findViewById(R.id.row6);
        layouts[7] = findViewById(R.id.row7);
        layouts[8] = findViewById(R.id.row8);
        layouts[9] = findViewById(R.id.row9);
        for (int row = 0; row < layouts.length; row++) {
            LinearLayout ll = layouts[row];
            for (int col = 0; col < layouts.length; col++) {
                Button b = new Button(this);
                b.setId(View.generateViewId());
                b.setTag(row * 10 + col);
                b.setTextSize(13);
                b.setText(letters[row] + numbers[col]);
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
        Button switchOrientation = findViewById(R.id.orientation);
        switchOrientation.setOnClickListener(this::switchOrientation);

    }

    public void switchOrientation(View v) {
        Button b = findViewById(R.id.orientation);
        if (orientation) {
            b.setText("Switch To Vertical");
        } else {
            b.setText("Switch To Horizontal");
        }
        orientation = !orientation;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        Button button = findViewById(id);
        int location = Integer.parseInt(button.getTag().toString());
        setUpBoard(location);
        setColors();
    }




    public void setUpBoard(int location) {
        TextView text = findViewById(R.id.textView5);

        if (count == 0) {
            ships[count] = new Destroyer(orientation, location);
            boolean valid = playerBoard.addShip(ships[count]);
            if (!valid) {
                return;
            } else {
                count++;
                text.setText("Place Your Battleship!");
            }
        }
        else if (count == 1) {
            ships[count] = new Battleship(orientation, location);
            boolean valid = playerBoard.addShip(ships[count]);
            if (!valid) {
                return;
            } else {
                count++;
                text.setText("Place Your Submarine!");
            }
        }
        else if (count == 2) {
            ships[count] = new Submarine(orientation, location);
            boolean valid = playerBoard.addShip(ships[count]);
            if (!valid) {
                return;
            } else {
                count++;
                text.setText("Place Your Carrier!");
            }
        }
        else if (count == 3) {
            ships[count] = new Carrier(orientation, location);
            boolean valid = playerBoard.addShip(ships[count]);
            if (!valid) {
                return;
            } else {
                count++;
                text.setText("Place Your Patrol Boat!");
            }
        }
        else if (count == 4) {
            ships[count] = new PatrolBoat(orientation, location);
            boolean valid = playerBoard.addShip(ships[count]);
            if (!valid) {
                return;
            } else {
                count++;
            }
        }

        if (count >= 5) {
            Intent intent = new Intent(MainActivity.this, MainGame.class);
            intent.putExtra("Player Board", playerBoard);
            intent.putExtra("Ships Array", ships);
            startActivity(intent);
            finish();
        }
    }

    public void setColors() {
        for (int row = 0; row < buttons.length; row++) {
            for (int col = 0; col < buttons[0].length; col++) {
                if (playerBoard.getBoard()[row][col] != 0) {
//                    buttons[row][col].setBackgroundTintList(ContextCompat.getColorStateList(this, android.R.color.holo_blue_bright));
                    buttons[row][col].setBackgroundTintList(ContextCompat.getColorStateList(this, android.R.color.darker_gray));
                }
            }
        }
    }


}