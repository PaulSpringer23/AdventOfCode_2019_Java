import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Part2 {

    public static void main(String[] args) throws IOException {
        String inputLine = Files.readAllLines(Paths.get(new File("Day07").getAbsolutePath() + File.separator + "src" + File.separator + "input.txt")).get(0);

        List<Integer> program = new ArrayList<>();

        for (String value : inputLine.split(",")) {
            program.add(Integer.parseInt(value));
        }

        int maxSignal = Integer.MIN_VALUE;
        HashSet<String> permutations = generatePermutations("56789");

        for (String permutation : permutations) {
            ArrayList<Computer> computers = new ArrayList<>();
            computers.add(new Computer(program));
            computers.get(0).input.add(permutation.substring(0,1));
            computers.add(new Computer(program));
            computers.get(1).input.add(permutation.substring(1,2));
            computers.add(new Computer(program));
            computers.get(2).input.add(permutation.substring(2,3));
            computers.add(new Computer(program));
            computers.get(3).input.add(permutation.substring(3,4));
            computers.add(new Computer(program));
            computers.get(4).input.add(permutation.substring(4));

            boolean allComputersHalted = false;
            int activeComputer = 0;
            ArrayDeque<String> output = new ArrayDeque<>();
            output.add("0");
            while (!allComputersHalted) {
                computers.get(activeComputer).input.add(output.pop());
                output = computers.get(activeComputer).run();

                allComputersHalted = true;
                for (Computer computer : computers) {
                    if (computer.currentState == State.OK || computer.currentState == State.WAITING) {
                        allComputersHalted = false;
                        break;
                    }
                }

                activeComputer = activeComputer == 4 ? 0 : activeComputer + 1;
            }

            int signal = Integer.parseInt(output.pop());
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
