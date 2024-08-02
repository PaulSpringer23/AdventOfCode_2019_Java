import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class Part1 {

    public static void main(String[] args) throws IOException {
        String inputLine = Files.readAllLines(Paths.get(new File("Day05").getAbsolutePath() + File.separator + "src" + File.separator + "input.txt")).get(0);

        List<Integer> program = new ArrayList<>();

        for (String value : inputLine.split(",")) {
            program.add(Integer.parseInt(value));
        }

        Computer computer = new Computer(program);
        ArrayDeque<String> input = new ArrayDeque<>();
        input.add("1");

        ArrayDeque<String> output = computer.run(input);
        for (String entry : output) {
            System.out.println(entry);
        }

        System.out.println("Done!");
    }
}
