import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Part1 {

    public static void main(String[] args) throws IOException {
        List<String> input = Files.readAllLines(Paths.get(new File("Day12").getAbsolutePath() + File.separator + "src" + File.separator + "input.txt"));

        ArrayList<Moon> moons = new ArrayList<>();

        for (String line : input) {
            String [] coordinates = line.replace("<", "").replace(">","").split(", ");
            int x = Integer.parseInt(coordinates[0].split("=")[1]);
            int y = Integer.parseInt(coordinates[1].split("=")[1]);
            int z = Integer.parseInt(coordinates[2].split("=")[1]);
            Moon newPlanet = new Moon();
            newPlanet.x = x;
            newPlanet.y = y;
            newPlanet.z = z;
            moons.add(newPlanet);
        }

        int[][] pairs = new int[][] {{0,1}, {0,2}, {0,3}, {1,2}, {1,3}, {2,3}};

        for (int timeStep = 0; timeStep < 1000; timeStep++) {
            for (int[] pair : pairs) {
                Moon firstMoon = moons.get(pair[0]);
                Moon secondMoon = moons.get(pair[1]);

                if (firstMoon.x > secondMoon.x) {
                    firstMoon.velX--;
                    secondMoon.velX++;
                } else if (firstMoon.x < secondMoon.x){
                    firstMoon.velX++;
                    secondMoon.velX--;
                }

                if (firstMoon.y > secondMoon.y) {
                    firstMoon.velY--;
                    secondMoon.velY++;
                } else if (firstMoon.y < secondMoon.y){
                    firstMoon.velY++;
                    secondMoon.velY--;
                }

                if (firstMoon.z > secondMoon.z) {
                    firstMoon.velZ--;
                    secondMoon.velZ++;
                } else if (firstMoon.z < secondMoon.z){
                    firstMoon.velZ++;
                    secondMoon.velZ--;
                }

            }

            for (Moon moon : moons) {
                moon.x += moon.velX;
                moon.y += moon.velY;
                moon.z += moon.velZ;
            }

        }

        int totalEnergy = 0;

        for (Moon moon : moons) {
            int potentialEnergy = Math.abs(moon.x) + Math.abs(moon.y) + Math.abs(moon.z);
            int kineticEnergy = Math.abs(moon.velX) + Math.abs(moon.velY) + Math.abs(moon.velZ);
            totalEnergy += potentialEnergy * kineticEnergy;

        }

        System.out.println(totalEnergy);
        System.out.println("Done!");
    }
}
