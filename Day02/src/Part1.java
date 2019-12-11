import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Part1 {

    public static void main(String[] args) throws IOException {
        String inputLine = Files.readAllLines(Paths.get(new File("").getAbsolutePath() + File.separator + "src" + File.separator + "input.txt")).get(0);

        List<Integer> program = new ArrayList<>();

        for (String value : inputLine.split(",")) {
            program.add(Integer.parseInt(value));
        }

        program.set(1, 12);
        program.set(2, 2);

        boolean error = false;
        int pointer = 0;
        int opCode;

        while (true) {
            opCode = program.get(pointer);
            if (opCode == 99) {
                break;
            } else if (opCode != 1 && opCode != 2) {
                error = true;
                break;
            }

            int firstValue = program.get(program.get(pointer + 1));
            int secondValue = program.get(program.get(pointer + 2));
            int destination = program.get(pointer + 3);

            if (opCode == 1) {
                program.set(destination, firstValue + secondValue);
            } else {
                program.set(destination, firstValue * secondValue);
            }

            pointer += 4;
        }


        if (error) {
            System.out.println("Error!");
        } else {
            System.out.println(program.get(0));
        }

        System.out.println("Done!");
    }
}
