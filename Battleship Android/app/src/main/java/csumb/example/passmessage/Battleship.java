package csumb.example.passmessage;

import java.io.Serializable;

public class Battleship extends Ship  implements Serializable {
    public Battleship(boolean vertical, int position) {
        super(4, 2, vertical, position);
    }
}
