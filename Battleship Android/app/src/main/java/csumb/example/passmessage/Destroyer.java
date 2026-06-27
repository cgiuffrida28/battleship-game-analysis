package csumb.example.passmessage;

import java.io.Serializable;

public class Destroyer extends Ship  implements Serializable {
    public Destroyer(boolean vertical, int position) {
        super(3, 1, vertical, position);
    }
}
