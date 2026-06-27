import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;

public class BattleshipTests {
    GameLoop g;
    Scanner in;

    @Test
    void instantWin() throws IOException {
        FileReader fr = new FileReader("C:/Users/dougi_rlte0ci/IdeaProjects/InClassNotes/CST-338-Battleship-Project/Battleship Java/tests/testInstantWin.txt");
        in = new Scanner(fr);
        g = new GameLoop(in);
        boolean win = g.mainGame();
        assertEquals(win, true);
    }

    @Test
    void slowerWin() throws IOException {
        FileReader fr = new FileReader("C:/Users/dougi_rlte0ci/IdeaProjects/InClassNotes/CST-338-Battleship-Project/Battleship Java/tests/testSlowerWin.txt");
        in = new Scanner(fr);
        g = new GameLoop(in);
        boolean win = g.mainGame();
        assertEquals(win, true);
    }

    @Test
    void testLose() throws IOException {
        FileReader fr = new FileReader("C:/Users/dougi_rlte0ci/IdeaProjects/InClassNotes/CST-338-Battleship-Project/Battleship Java/tests/testLose.txt");
        in = new Scanner(fr);
        g = new GameLoop(in);
        boolean win = g.mainGame();
        assertEquals(win, false);
    }

    @Test
    void testOutOfBounds() throws IOException {
        FileReader fr = new FileReader("C:/Users/dougi_rlte0ci/IdeaProjects/InClassNotes/CST-338-Battleship-Project/Battleship Java/tests/testOutOfBounds.txt");
        in = new Scanner(fr);
        g = new GameLoop(in);
        boolean win = g.mainGame();
        assertEquals(win, true);
    }




}
