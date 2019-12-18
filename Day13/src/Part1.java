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

        Computer computer = new Computer(program);
        ArrayDeque<String> output = computer.run();

        int blockCount = 0;
        while (!output.isEmpty()) {
            String[] chunk = new String[] {output.pop(), output.pop(), output.pop()};
            if (chunk[2].equals("2")) {
                blockCount++;
            }
        }

        System.out.println(blockCount);
        System.out.println("Done!");
    }
}

