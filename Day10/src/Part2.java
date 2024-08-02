import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Part2 {

    public static void main(String[] args) throws IOException {
        List<String> input = Files.readAllLines(Paths.get(new File("Day10").getAbsolutePath() + File.separator + "src" + File.separator + "input.txt"));

        ArrayList<Asteroid> asteroids = generateAsteroids(input);

        Asteroid bestAsteroid = asteroids.get(0);

        for (Asteroid asteroid : asteroids) {
            for (Asteroid otherAsteroid : asteroids) {
                if (otherAsteroid == asteroid) {
                    continue;
                }

                double theta = Math.atan2(otherAsteroid.y - asteroid.y, otherAsteroid.x - asteroid.x);
                double degrees = theta * (180 / Math.PI);
                if (degrees <= 0) {
                    degrees += 360;
                }
                asteroid.degrees.putIfAbsent(degrees, new ArrayList<>());
                asteroid.degrees.get(degrees).add(otherAsteroid);

            }

            if (asteroid.degrees.keySet().size() > bestAsteroid.degrees.keySet().size()) {
                bestAsteroid = asteroid;
            }

        }

        ArrayList<Double> undestroyedAsteroidDegrees = new ArrayList<>(bestAsteroid.degrees.keySet());
        Collections.sort(undestroyedAsteroidDegrees);

        for (int i = 0; i < undestroyedAsteroidDegrees.size() - 1; i++) {
            if (undestroyedAsteroidDegrees.get(i) >= 270) {
                Collections.rotate(undestroyedAsteroidDegrees, i * -1);
                break;
            }
        }

        ArrayList<Asteroid> asteroidsRemoved = new ArrayList<>();

        while (bestAsteroid.degrees.keySet().size() > 0) {
            for (Double degree : undestroyedAsteroidDegrees) {
                ArrayList<Asteroid> asteroidsAtDegree = bestAsteroid.degrees.get(degree);
                if (asteroidsAtDegree != null) {
                    Asteroid closestAsteroid = asteroidsAtDegree.get(0);
                    for (Asteroid asteroidAtDegree : asteroidsAtDegree) {
                        if (asteroidAtDegree.getDistance(bestAsteroid) < closestAsteroid.getDistance(bestAsteroid)) {
                            closestAsteroid = asteroidAtDegree;
                        }
                    }
                    asteroidsAtDegree.remove(closestAsteroid);
                    asteroidsRemoved.add(closestAsteroid);
                    if (asteroidsAtDegree.isEmpty()) {
                        bestAsteroid.degrees.remove(degree);
                    }
                }
            }
        }

        Asteroid twoHundredthAsteroid = asteroidsRemoved.get(199);
        System.out.println("200th Asteroid: " + ((twoHundredthAsteroid.x * 100) + twoHundredthAsteroid.y));
        System.out.println("Done!");
    }

    private static ArrayList<Asteroid> generateAsteroids(List<String> input) {
        ArrayList<Asteroid> asteroids = new ArrayList<>();
        int y = 0;

        for (String line : input) {
            int x = 0;
            for (String point : line.split("")) {
                if (point.equals("#")) {
                    Asteroid newAsteroid = new Asteroid();
                    newAsteroid.x = x;
                    newAsteroid.y = y;
                    asteroids.add(newAsteroid);
                }
                x++;
            }
            y++;
        }

        return asteroids;
    }
}
