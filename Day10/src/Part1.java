import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Part1 {

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
                asteroid.degrees.putIfAbsent(degrees, new ArrayList<>());
                asteroid.degrees.get(degrees).add(otherAsteroid);

            }

            if (asteroid.degrees.keySet().size() > bestAsteroid.degrees.keySet().size()) {
                bestAsteroid = asteroid;
            }

        }

        System.out.println(bestAsteroid.degrees.keySet().size());
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
