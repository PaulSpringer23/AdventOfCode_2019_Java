import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Part1 {

    public static void main(String[] args) throws IOException {
        String inputLine = Files.readAllLines(Paths.get(new File("Day02").getAbsolutePath() + File.separator + "src" + File.separator + "input.txt")).get(0);

        List<Integer> program = new ArrayList<>();

        for (String value : inputLine.split(",")) {
            program.add(Integer.parseInt(value));
        }

        program.set(1, 12);
        program.set(2, 2);

        Computer computer = new Computer(program);
        System.out.println(computer.run());

        System.out.println("Done!");
    }
}
