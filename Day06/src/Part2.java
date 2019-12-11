import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;

public class Part2 {

    public static void main (String[] args) throws IOException {
        List<String> input = Files.readAllLines(Paths.get(new File("").getAbsolutePath() + File.separator + "src" + File.separator + "input.txt"));
        HashMap<String, Planet> planets = new HashMap<>();

        for (String line : input) {
            String planetName = line.split("\\)")[1];
            String orbitCenter = line.split("\\)")[0];

            planets.putIfAbsent(planetName, new Planet(line.split("\\)")[1]));
            planets.putIfAbsent(orbitCenter, new Planet(line.split("\\)")[0]));
        }

        for (String line : input) {
            Planet planet = planets.get(line.split("\\)")[1]);
            planet.orbitCenter = planets.get(line.split("\\)")[0]);
        }

        Planet startingPlanet = planets.get("YOU").orbitCenter;
        Planet endingPlanet = planets.get("SAN").orbitCenter;
        planets.remove("YOU");
        planets.remove("SAN");

        for (Planet planet : planets.values()) {
            if (!planet.id.equals("COM")) {
                planet.orbitCenter.orbitedBy.add(planet);
            }
        }

        HashMap <String, Integer> distances = new HashMap<>();
        distances.put(startingPlanet.id, 0);

        HashSet<String> visited = new HashSet<>();
        Queue<String> queue = new ArrayDeque<>();
        queue.add(startingPlanet.id);

        while (!queue.isEmpty()) {
            Planet currentPlanet = planets.get(queue.poll());
            int currentDistance = distances.get(currentPlanet.id);
            visited.add(currentPlanet.id);

            if (currentPlanet.orbitCenter != null) {
                distances.putIfAbsent(currentPlanet.orbitCenter.id, Integer.MAX_VALUE);
                int currentDistanceToNeighbor = distances.get(currentPlanet.orbitCenter.id);
                if (currentDistance + 1 < currentDistanceToNeighbor) {
                    distances.put(currentPlanet.orbitCenter.id, currentDistance + 1);
                }

                if (!visited.contains(currentPlanet.orbitCenter.id)) {
                    queue.add(currentPlanet.orbitCenter.id);
                }

            }

            if (currentPlanet.orbitedBy.size() > 0) {
                for (Planet orbitingPlanet : currentPlanet.orbitedBy) {
                    distances.putIfAbsent(orbitingPlanet.id, Integer.MAX_VALUE);
                    int currentDistanceToNeighbor = distances.get(orbitingPlanet.id);
                    if (currentDistance + 1 < currentDistanceToNeighbor) {
                        distances.put(orbitingPlanet.id, currentDistance + 1);
                    }

                    if (!visited.contains(orbitingPlanet.id)) {
                        queue.add(orbitingPlanet.id);
                    }
                }
            }

        }

        System.out.println(distances.get(endingPlanet.id));
        System.out.println("Done!");
    }
}


