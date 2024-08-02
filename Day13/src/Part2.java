import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Part2 {
    static int score = 0;

    public static void main(String[] args) throws IOException {
        String inputLine = Files.readAllLines(Paths.get(new File("Day13").getAbsolutePath() + File.separator + "src" + File.separator + "input.txt")).get(0);

        List<Long> program = new ArrayList<>();

        for (String value : inputLine.split(",")) {
            program.add(Long.parseLong(value));
        }

        List<Long> backupProgram = new ArrayList<>(program);

        int width = 0;
        int height = 0;

        Computer computer = new Computer(program);
        ArrayDeque<String> output = computer.run();


        while (!output.isEmpty()) {
            String[] chunk = new String[] {output.pop(), output.pop(), output.pop()};
            int x = Integer.parseInt(chunk[0]);
            int y = Integer.parseInt(chunk[1]);
            if (x > width) {
                width = x;
            }

            if (y > height) {
                height = y;
            }

        }


        int[][] grid = new int[height + 1][width + 1];

        int ballLocationX = 0;
        int paddleLocationX = 0;

        program = new ArrayList<>(backupProgram);
        program.set(0, 2L);
        computer = new Computer(program);


        while (computer.currentState != State.HALTED && computer.currentState != State.ERROR) {
            output = computer.run();
            HashMap<String, String> locations = getPaddleAndBallLocations(output);

            if (locations.containsKey("Ball")) {
                ballLocationX = Integer.parseInt(locations.get("Ball"));
            }
            if (locations.containsKey("Paddle")) {
                paddleLocationX = Integer.parseInt(locations.get("Paddle"));
            }

            if (ballLocationX > paddleLocationX) {
                computer.input.add("1");
            } else if (ballLocationX < paddleLocationX) {
                computer.input.add("-1");
            } else {
                computer.input.add("0");
            }

            buildAndRenderGrid(output, grid);
        }

        System.out.println("Done!");
    }

    private static HashMap<String, String> getPaddleAndBallLocations(ArrayDeque<String> output) {
        ArrayDeque<String> outputCopy = new ArrayDeque<>(output);
        HashMap<String, String> locations = new HashMap<>();
        while (!outputCopy.isEmpty()) {
            String[] chunk = new String[] {outputCopy.pop(), outputCopy.pop(), outputCopy.pop()};

            int value = Integer.parseInt(chunk[2]);

            if (value == 4) {
                locations.put("Ball", chunk[0]);
            } else if (value == 3) {
                locations.put("Paddle", chunk[0]);
            }

            if (locations.keySet().contains("Ball") && locations.keySet().contains("Paddle")) {
                break;
            }
        }
        return locations;
    }

    private static void buildAndRenderGrid(ArrayDeque<String> output, int[][] grid) {
        while (!output.isEmpty()) {
            String[] chunk = new String[] {output.pop(), output.pop(), output.pop()};

            int x = Integer.parseInt(chunk[0]);
            int y = Integer.parseInt(chunk[1]);
            int value = Integer.parseInt(chunk[2]);

            if (x == -1) {
                score = value;
                continue;
            }
            grid[y][x] = value;
        }

        StringBuilder mapRender = new StringBuilder();
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[0].length; x++) {
                switch (grid[y][x]) {
                    case 0:
                        mapRender.append(" ");
                        break;
                    case 1:
                        mapRender.append("|");
                        break;
                    case 2:
                        mapRender.append("-");
                        break;
                    case 3:
                        mapRender.append("I");
                        break;
                    case 4:
                        mapRender.append("*");
                        break;
                }
            }
            mapRender.append("\n");
        }

        mapRender.append("Score: ");
        mapRender.append(score);

        System.out.println(mapRender);
    }
}

