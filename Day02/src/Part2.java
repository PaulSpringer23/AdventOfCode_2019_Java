import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Part2 {

    public static void main(String[] args) throws IOException {
        String inputLine = Files.readAllLines(Paths.get(new File("").getAbsolutePath() + File.separator + "src" + File.separator + "input.txt")).get(0);

        List<Integer> program = new ArrayList<>();

        for (String value : inputLine.split(",")) {
            program.add(Integer.parseInt(value));
        }

        List<Integer> backupProgram = new ArrayList<>(program);

        int noun = 0;
        int verb = 0;

        boolean error = false;

        while (true)
        {
            program.set(1, noun);
            program.set(2, verb);
            int pointer = 0;
            int opCode;

            while (true) {
                opCode = program.get(pointer);
                if (opCode != 1 && opCode != 2) {
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

            if (program.get(0) == 19690720) {
                break;
            } else {
                if (noun == 99) {
                    if (verb == 99) {
                        error = true;
                        break;
                    }
                    noun = 0;
                    verb++;
                } else {
                    noun++;
                }
                program = new ArrayList<>(backupProgram);
            }
        }


        if (error) {
            System.out.println("Error!");
        } else {
            System.out.println("Answer: " + ((100 * noun) + verb));
        }

        System.out.println("Done!");
    }
}
