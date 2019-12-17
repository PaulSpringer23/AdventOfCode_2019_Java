import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Part1 {

    public static void main(String[] args) throws IOException {
        String inputLine = Files.readAllLines(Paths.get(new File("").getAbsolutePath() + File.separator + "src" + File.separator + "input.txt")).get(0);

        List<Long> program = new ArrayList<>();

        for (String value : inputLine.split(",")) {
            program.add(Long.parseLong(value));
        }

        int robotX = 0;
        int robotY = 0;

        Direction robotDirection = Direction.UP;

        HashMap <String, String> grid = new HashMap<>();
        grid.put("0,0", "0");

        Computer computer = new Computer(program);

        int panelsPainted = 1;

        while (computer.currentState != State.HALTED && computer.currentState != State.ERROR) {
            if (!grid.containsKey(robotX + "," + robotY)) {
                panelsPainted++;
                grid.put(robotX + "," + robotY, "0");
            }

            computer.input.add(grid.get(robotX + "," + robotY));
            ArrayDeque<String> output = computer.run();

            grid.put(robotX + "," + robotY, output.pop());
            if (output.pop().equals("1")) {
                switch (robotDirection) {
                    case UP:
                        robotDirection = Direction.RIGHT;
                        break;
                    case RIGHT:
                        robotDirection = Direction.DOWN;
                        break;
                    case DOWN:
                        robotDirection = Direction.LEFT;
                        break;
                    case LEFT:
                        robotDirection = Direction.UP;
                        break;
                }

            } else {
                switch (robotDirection) {
                    case UP:
                        robotDirection = Direction.LEFT;
                        break;
                    case LEFT:
                        robotDirection = Direction.DOWN;
                        break;
                    case DOWN:
                        robotDirection = Direction.RIGHT;
                        break;
                    case RIGHT:
                        robotDirection = Direction.UP;
                        break;
                }
            }

            switch (robotDirection) {
                case UP:
                    robotY--;
                    break;
                case LEFT:
                    robotX--;
                    break;
                case DOWN:
                    robotY++;
                    break;
                case RIGHT:
                    robotX++;
                    break;
            }
        }

        System.out.println(panelsPainted);
        System.out.println("Done!");
    }
}

