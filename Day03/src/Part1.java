import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.List;

public class Part1 {

    public static void main (String[] args) throws IOException {
        List<String> input = Files.readAllLines(Paths.get(new File("").getAbsolutePath() + File.separator + "src" + File.separator + "input.txt"));

        String[] rightWirePath = input.get(0).split(",");
        String[] leftWirePath = input.get(1).split(",");

        HashSet<String> rightWirePoints = markVisitedPoints(rightWirePath);
        HashSet<String> leftWirePoints = markVisitedPoints(leftWirePath);

        ArrayDeque<String> intersections = new ArrayDeque<>(0);

        for (String point : rightWirePoints) {
            if (leftWirePoints.contains(point)) {
                intersections.add(point);
            }
        }

        int closestIntersectionDistance = Integer.MAX_VALUE;
        while (intersections.size() > 0) {
            String currentIntersection = intersections.pop();
            String[] currentIntersectionSplit = currentIntersection.split(",");
            int x = Integer.parseInt(currentIntersectionSplit[0]);
            int y = Integer.parseInt(currentIntersectionSplit[1]);
            int intersectionDistance = Math.abs(x - y);

            if (intersectionDistance < closestIntersectionDistance) {
                closestIntersectionDistance = intersectionDistance;
            }
        }


        System.out.println(closestIntersectionDistance);
        System.out.println("Done!");

    }

    public static HashSet<String> markVisitedPoints(String[] wirePath) {
        int x = 0;
        int y = 0;

        HashSet<String> visitedPoints = new HashSet<>();

        for (String pathStep : wirePath) {
            char direction = pathStep.charAt(0);
            int distance = Integer.parseInt(pathStep.substring(1));

            switch (direction) {
                case 'R': {
                    while (distance > 0) {
                        x += 1;
                        distance--;
                        visitedPoints.add(x + "," + y);
                    }
                    break;
                }
                case 'L': {
                    while (distance > 0) {
                        x -= 1;
                        distance--;
                        visitedPoints.add(x + "," + y);
                    }
                    break;
                }
                case 'U': {
                    while (distance > 0) {
                        y += 1;
                        distance--;
                        visitedPoints.add(x + "," + y);
                    }
                    break;
                }
                case 'D': {
                    while (distance > 0) {
                        y -= 1;
                        distance--;
                        visitedPoints.add(x + "," + y);
                    }
                    break;
                }
            }

        }

        return visitedPoints;
    }
}
