import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Part2 {

    public static void main(String[] args) throws IOException {
        String inputLine = Files.readAllLines(Paths.get(new File("Day02").getAbsolutePath() + File.separator + "src" + File.separator + "input.txt")).get(0);

        List<Integer> program = new ArrayList<>();

        for (String value : inputLine.split(",")) {
            program.add(Integer.parseInt(value));
        }

        List<Integer> backupProgram = new ArrayList<>(program);

        String target = "19690720";
        int noun;
        int verb = 0;

        for (noun = 0; noun < 99; noun ++) {
            String result = "";
            for (verb = 0; verb < 99; verb++) {
                program = new ArrayList<>(backupProgram);
                program.set(1, noun);
                program.set(2, verb);
                Computer computer = new Computer(program);
                result = computer.run();
                if (result.equals(target)) {
                    break;
                }
            }
            if (result.equals(target)) {
                break;
            }
        }

        System.out.println("Noun: " + noun);
        System.out.println("Verb: " + verb);
        System.out.println("Answer: " + (100 * noun + verb));

        System.out.println("Done!");
    }
}
