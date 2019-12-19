import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Part2 {

    public static void main(String[] args) throws IOException {
        String inputLine = Files.readAllLines(Paths.get(new File("").getAbsolutePath() + File.separator + "src" + File.separator + "input.txt")).get(0);

        List<Long> program = new ArrayList<>();

        for (String value : inputLine.split(",")) {
            program.add(Long.parseLong(value));
        }

        int robotX = 0;
        int robotY = 0;
        int direction = 1;


        LinkedHashMap<String, String> grid = new LinkedHashMap<>();
        grid.put("0,0", ".");

        Computer computer = new Computer(program);
        while (!grid.values().contains("!")) {
            computer.input.add(String.valueOf(direction));

            ArrayDeque<String> output = computer.run();
            String result = output.pop();

            if (result.equals("0")) {
                switch (direction) {
                    case 1:
                        grid.put(robotX + "," + (robotY - 1), "#");
                        direction = 3;
                        break;
                    case 2:
                        grid.put(robotX + "," + (robotY + 1), "#");
                        direction = 4;
                        break;
                    case 3:
                        grid.put((robotX - 1) + "," + robotY, "#");
                        direction = 2;
                        break;
                    case 4:
                        grid.put((robotX + 1) + "," + (robotY), "#");
                        direction = 1;
                        break;
                }
            } else {
                grid.put(robotX + "," + robotY, ".");
                switch (direction) {
                    case 1:
                        robotY--;
                        direction = 4;
                        break;
                    case 2:
                        robotY++;
                        direction = 3;
                        break;
                    case 3:
                        robotX--;
                        direction = 1;
                        break;
                    case 4:
                        robotX++;
                        direction = 2;
                        break;
                }
                grid.put(robotX + "," + robotY, result.equals("1") ? "." : "!");
            }
        }

        String destination = "0,0";
        HashMap<String, Integer> distances = new HashMap<>();

        for (Map.Entry<String, String> entry : grid.entrySet()) {
            if (entry.getValue().equals("!")) {
                destination = entry.getKey();
            }
            distances.put(entry.getKey(), Integer.MAX_VALUE);
        }

        int distance;
        HashSet<String> visited = new HashSet<>();
        distances.put("0,0", 0);

        ArrayDeque<String> queue = new ArrayDeque<>();
        queue.add("0,0");

        while (!queue.isEmpty()) {
            String location = queue.pop();
            distance = distances.get(location);
            visited.add(location);
            String[] coordinates = location.split(",");

            int x = Integer.parseInt(coordinates[0]);
            int y = Integer.parseInt(coordinates[1]);

            String[] keys = new String[] {
                    (x + 1) + "," + y,
                    (x - 1) + "," + y,
                    x + "," + (y + 1),
                    x + "," + (y - 1)
            };

            for (String key : keys) {
                if (grid.containsKey(key) && !grid.get(key).equals("#")) {
                    if (distance + 1 < distances.get(key)) {
                        distances.put(key, distance + 1);
                    }

                    if (!visited.contains(key) && !queue.contains(key)) {
                        queue.add(key);
                    }
                }
            }
        }

        System.out.println(distances.get(destination));
        System.out.println("Done!");
    }

}

