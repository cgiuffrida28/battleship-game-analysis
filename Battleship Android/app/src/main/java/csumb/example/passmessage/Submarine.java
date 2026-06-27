package csumb.example.passmessage;

import java.io.Serializable;

public class Submarine extends Ship  implements Serializable {
    public Submarine(boolean vertical, int position) {
        super(3, 3, vertical, position);
    }
}
