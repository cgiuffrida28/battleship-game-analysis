package csumb.example.passmessage;

import java.io.Serializable;

public class PatrolBoat extends Ship  implements Serializable {
    public PatrolBoat(boolean vertical, int position) {
        super(2, 5, vertical, position);
    }
}
