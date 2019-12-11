import java.util.HashSet;

class Planet {
    String id;
    Planet orbitCenter;
    HashSet<Planet> orbitedBy = new HashSet<>();
    int directOrbits = 0;
    int indirectOrbits;

    Planet(String id) {
        this.id = id;
    }

    public String toString() {
        return this.id;
    }

}