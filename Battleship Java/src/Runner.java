import java.io.IOException;
import java.util.Scanner;

public class Runner {
    public static void main(String[] args) throws IOException {
        GameLoop g = new GameLoop(new Scanner(System.in));
        g.mainGame();
    }
}
