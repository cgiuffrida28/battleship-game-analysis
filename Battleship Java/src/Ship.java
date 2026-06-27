public abstract class Ship {
    public final int LENGTH;
    public final int VALUE;
    private boolean vertical;
    private int position;
    private int health;

    Ship(int length, int value, boolean vertical, int position) {
        LENGTH = length;
        health = length;
        VALUE = value;
        this.vertical = vertical;
        this.position = position;
    }

    public boolean getVertical() {
        return this.vertical;
    }
    public void setVertical(boolean orientation) {
        this.vertical = orientation;
    }
    public int getPosition() {
        return this.position;
    }
    public void setPosition(int location) {
        this.position = location;
    }

    public boolean checkHealth() {
        if (health == 0) {
            return false;
        }
        return true;
    }

    public boolean subtractHealth() {
        health--;
        return checkHealth();
    }

    public String toString() {
        String s = "";
        if (VALUE == 1) {
            s = "Destroyer";
        }
        else if (VALUE == 2) {
            s = "Battleship";
        }
        else if (VALUE == 3) {
            s = "Submarine";
        }
        else if (VALUE == 4) {
            s = "Carrier";
        }
        else if (VALUE == 5) {
            s = "Patrol Boat";
        }
        return s;
    }
}
