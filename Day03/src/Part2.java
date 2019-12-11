import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

public class Part2 {

    public static void main (String[] args) throws IOException {
        List<String> input = Files.readAllLines(Paths.get(new File("").getAbsolutePath() + File.separator + "src" + File.separator + "input.txt"));

        String[] rightWirePath = input.get(0).split(",");
        String[] leftWirePath = input.get(1).split(",");

        HashMap<String, Integer> rightWirePoints = markVisitedPoints(rightWirePath);
        HashMap<String, Integer> leftWirePoints = markVisitedPoints(leftWirePath);

        int fewestSteps = Integer.MAX_VALUE;

        for (String point : rightWirePoints.keySet()) {
            if (leftWirePoints.keySet().contains(point)) {
                int stepCount = leftWirePoints.get(point) + rightWirePoints.get(point);
                if (stepCount < fewestSteps) {
                    fewestSteps = stepCount;
                }
            }
        }


        System.out.println(fewestSteps);
        System.out.println("Done!");

    }

    public static HashMap<String, Integer> markVisitedPoints(String[] wirePath) {
        int steps = 0;
        int x = 0;
        int y = 0;

        HashMap<String, Integer> visitedPoints = new HashMap<>();

        for (String pathStep : wirePath) {
            char direction = pathStep.charAt(0);
            int distance = Integer.parseInt(pathStep.substring(1));

            switch (direction) {
                case 'R': {
                    while (distance > 0) {
                        x += 1;
                        distance--;
                        steps++;
                        visitedPoints.put(x + "," + y, steps);
                    }
                    break;
                }
                case 'L': {
                    while (distance > 0) {
                        x -= 1;
                        distance--;
                        steps++;
                        visitedPoints.put(x + "," + y, steps);
                    }
                    break;
                }
                case 'U': {
                    while (distance > 0) {
                        y += 1;
                        distance--;
                        steps++;
                        visitedPoints.put(x + "," + y, steps);
                    }
                    break;
                }
                case 'D': {
                    while (distance > 0) {
                        y -= 1;
                        distance--;
                        steps++;
                        visitedPoints.put(x + "," + y, steps);
                    }
                    break;
                }
            }

        }

        return visitedPoints;
    }
}
