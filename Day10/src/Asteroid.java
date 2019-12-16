import java.util.ArrayList;
import java.util.HashMap;

class Asteroid {

    int x, y;
    HashMap<Double, ArrayList<Asteroid>> degrees = new HashMap<>();

    double getDistance(Asteroid a) {
        return Math.sqrt(Math.pow(this.x - a.x, 2) + Math.pow(this.y - a.y, 2));
    }

    public String toString() {
        return this.x + "," + this.y;
    }

}

