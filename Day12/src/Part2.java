import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Part2 {

    public static void main(String[] args) throws IOException {
        List<String> input = Files.readAllLines(Paths.get(new File("").getAbsolutePath() + File.separator + "src" + File.separator + "input.txt"));

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

        HashMap<String, Integer> xMoonStates = new HashMap<>();
        boolean xRepeated = false;
        int xRepeatStep = 0;

        HashMap<String, Integer> yMoonStates = new HashMap<>();
        boolean yRepeated = false;
        int yRepeatStep = 0;

        HashMap<String, Integer> zMoonStates = new HashMap<>();
        boolean zRepeated = false;
        int zRepeatStep = 0;

        int stepNumber = 0;

        while (!(xRepeated && yRepeated && zRepeated)) {

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

            StringBuilder xMoonState = new StringBuilder();
            StringBuilder yMoonState = new StringBuilder();
            StringBuilder zMoonState = new StringBuilder();

            for (Moon moon : moons) {
                moon.x += moon.velX;
                xMoonState.append(moon.x);
                xMoonState.append("|");
                xMoonState.append(moon.velX);
                xMoonState.append("|");

                moon.y += moon.velY;
                yMoonState.append(moon.y);
                yMoonState.append("|");
                yMoonState.append(moon.velY);
                yMoonState.append("|");

                moon.z += moon.velZ;
                zMoonState.append(moon.z);
                zMoonState.append("|");
                zMoonState.append(moon.velZ);
                zMoonState.append("|");

            }

            if (!xRepeated && xMoonStates.keySet().contains(xMoonState.toString())) {
                xRepeated = true;
                xRepeatStep = xMoonStates.get(xMoonState.toString()) == 0 ? stepNumber : xMoonStates.get(xMoonState.toString());
            } else {
                xMoonStates.put(xMoonState.toString(), stepNumber);
            }

            if (!yRepeated && yMoonStates.keySet().contains(yMoonState.toString())) {
                yRepeated = true;
                yRepeatStep = yMoonStates.get(yMoonState.toString()) == 0 ? stepNumber : yMoonStates.get(yMoonState.toString());
            } else {
                yMoonStates.put(yMoonState.toString(), stepNumber);
            }

            if (!zRepeated && zMoonStates.keySet().contains(zMoonState.toString())) {
                zRepeated = true;
                zRepeatStep = zMoonStates.get(zMoonState.toString()) == 0 ? stepNumber : zMoonStates.get(zMoonState.toString());
            } else {
                zMoonStates.put(zMoonState.toString(), stepNumber);
            }

            stepNumber++;
        }

        System.out.println(getLCM(getLCM(xRepeatStep, yRepeatStep), zRepeatStep));
        System.out.println("Done!");
    }

    private static long getLCM(long a, long b) {
        return (a * b) / getGCF(a, b);
    }

    private static long getGCF(long a, long b) {
        if (b == 0) {
            return a;
        } else {
            return (getGCF(b, a % b));
        }
    }

}
