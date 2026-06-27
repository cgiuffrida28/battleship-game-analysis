package csumb.example.passmessage;

import java.io.Serializable;

public class Carrier extends Ship  implements Serializable {
    public Carrier(boolean vertical, int position) {
        super(5, 4, vertical, position);
    }
}
