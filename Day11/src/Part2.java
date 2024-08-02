import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Part2 {

    public static void main(String[] args) throws IOException {
        String inputLine = Files.readAllLines(Paths.get(new File("Day11").getAbsolutePath() + File.separator + "src" + File.separator + "input.txt")).get(0);

        List<Long> program = new ArrayList<>();

        for (String value : inputLine.split(",")) {
            program.add(Long.parseLong(value));
        }

        int robotX = 0;
        int robotY = 0;

        int ceilingX = 0;
        int ceilingY = 0;

        int floorX = 0;
        int floorY = 0;

        Direction robotDirection = Direction.UP;

        HashMap <String, String> grid = new HashMap<>();
        grid.put("0,0", "1");

        Computer computer = new Computer(program);
        while (computer.currentState != State.HALTED && computer.currentState != State.ERROR) {
            grid.putIfAbsent(robotX + "," + robotY, "0");
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

            if (robotX > ceilingX) {
                ceilingX = robotX;
            }

            if (robotX < floorX) {
                floorX = robotX;
            }

            if (robotY > ceilingY) {
                ceilingY = robotY;
            }

            if (robotY < floorY) {
                floorY = robotY;
            }
        }
        int xSize = Math.abs(ceilingX - floorX) + 1;
        int ySize = Math.abs(ceilingY - floorY) + 1;

        String[][] map = new String[ySize][xSize];

        for (Map.Entry<String, String> entry : grid.entrySet()) {
          String[] coordinates = entry.getKey().split(",");

          int x = Integer.parseInt(coordinates[0]);
          int y = Integer.parseInt(coordinates[1]);

            if (floorX < 0) {
                x += Math.abs(floorX);
            }

            if (floorY < 0) {
                y += Math.abs(floorY);
            }

            map[y][x] = entry.getValue();

        }

        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < ySize; y++) {
            for (int x = 0; x < xSize; x++) {
                String color = map[y][x];
                if (color == null || color.equals("0")) {
                    color = " ";
                } else {
                    color = "*";
                }
                sb.append(color);
            }
            sb.append("\n");
        }


        System.out.println(sb);
        System.out.println("Done!");
    }
}
