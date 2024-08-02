import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Part1 {

    public static void main(String[] args) throws IOException {
        String inputLine = Files.readAllLines(Paths.get(new File("Day07").getAbsolutePath() + File.separator + "src" + File.separator + "input.txt")).get(0);

        List<Integer> program = new ArrayList<>();

        for (String value : inputLine.split(",")) {
            program.add(Integer.parseInt(value));
        }

        List<Integer> backupProgram = new ArrayList<>(program);

        HashSet<String> permutations = generatePermutations("01234");
        int maxSignal = Integer.MIN_VALUE;

        for (String permutation : permutations) {
            ArrayDeque<String> output = new ArrayDeque<>();
            output.add("0");

            for (int x = 0; x < 5; x++) {
                String computerNumber = permutation.substring(x, x + 1);
                program = new ArrayList<>(backupProgram);
                Computer computer = new Computer(program);
                ArrayDeque<String> input = new ArrayDeque<>();
                input.add(computerNumber);
                input.add(output.pop());
                computer.input = input;
                output = computer.run();

            }
            Integer signal = Integer.parseInt(output.pop());
            if (signal > maxSignal) {
                maxSignal = signal;
            }
        }

        System.out.println(maxSignal);
        System.out.println("Done!");
    }

    private static HashSet<String> generatePermutations(String input)
    {
        HashSet<String> set = new HashSet<>();
        if (input.equals(""))
            return set;

        char a = input.charAt(0);

        if (input.length() > 1)
        {
            input = input.substring(1);

            Set<String> permSet = generatePermutations(input);

            for (String x : permSet)
            {
                for (int i = 0; i <= x.length(); i++)
                {
                    set.add(x.substring(0, i) + a + x.substring(i));
                }
            }
        }
        else
        {
            set.add(a + "");
        }
        return set;
    }
}
