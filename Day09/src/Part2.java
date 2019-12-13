import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class Part2 {

    public static void main(String[] args) throws IOException {
        String inputLine = Files.readAllLines(Paths.get(new File("").getAbsolutePath() + File.separator + "src" + File.separator + "input.txt")).get(0);

        List<Long> program = new ArrayList<>();

        for (String value : inputLine.split(",")) {
            program.add(Long.parseLong(value));
        }

        Computer computer = new Computer(program);
        computer.input.add("2");
        ArrayDeque<String> output = computer.run();

        System.out.println(output);
        System.out.println("Done!");
    }
}
