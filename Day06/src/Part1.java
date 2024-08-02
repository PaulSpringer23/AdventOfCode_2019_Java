import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

public class Part1 {

    public static void main (String[] args) throws IOException {
        List<String> input = Files.readAllLines(Paths.get(new File("Day06").getAbsolutePath() + File.separator + "src" + File.separator + "input.txt"));
        HashMap<String, Planet> planets = new HashMap<>();

        for (String line : input) {
            String planetName = line.split("\\)")[1];
            String orbitCenter = line.split("\\)")[0];

            planets.putIfAbsent(planetName, new Planet(line.split("\\)")[1]));
            planets.putIfAbsent(orbitCenter, new Planet(line.split("\\)")[0]));
        }

        for (String line : input) {
            Planet planet = planets.get(line.split("\\)")[1]);
            Planet orbitCenter = planets.get(line.split("\\)")[0]);

            planet.orbitCenter = orbitCenter;

        }

        for (Planet planet : planets.values()) {
            if (planet.orbitCenter != null) {
                planet.directOrbits++;
            }

            Planet indrectlyOrbitedPlanet = planet.orbitCenter;
            while (indrectlyOrbitedPlanet != null) {
                if (!indrectlyOrbitedPlanet.id.equals("COM")) {
                    planet.indirectOrbits++;
                }
                indrectlyOrbitedPlanet = indrectlyOrbitedPlanet.orbitCenter;
            }
        }

        int totalOrbits = 0;
        for (Planet planet : planets.values()) {
            totalOrbits += planet.directOrbits;
            totalOrbits += planet.indirectOrbits;
        }


        System.out.println(totalOrbits);
        System.out.println("Done!");
    }
}
