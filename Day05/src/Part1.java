import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Part1 {

    public static void main(String[] args) throws IOException {
        String inputLine = Files.readAllLines(Paths.get(new File("").getAbsolutePath() + File.separator + "src" + File.separator + "input.txt")).get(0);
        List<Integer> program = new ArrayList<>();
        for (String value : inputLine.split(",")) {
            program.add(Integer.parseInt(value));
        }

        boolean error = false;
        boolean complete = false;
        int pointer = 0;
        String opCode;

        while (!complete) {
            opCode = String.format("%05d", program.get(pointer));

            // 0: Position Mode
            // 1: Immediate Mode
            HashMap<String, Integer> parameterModes = new HashMap<>();

            parameterModes.put("A", Integer.parseInt(opCode.substring(0, 1)));
            parameterModes.put("B", Integer.parseInt(opCode.substring(1, 2)));
            parameterModes.put("C", Integer.parseInt(opCode.substring(2, 3)));

            opCode = opCode.substring(3);
            int pointerShiftAmount = 0;

            switch (opCode) {
                case "01":
                    int firstValue = parameterModes.get("C") == 0 ? program.get(program.get(pointer + 1)) : program.get(pointer + 1);
                    int secondValue = parameterModes.get("B") == 0 ? program.get(program.get(pointer + 2)) : program.get(pointer + 2);
                    int destination = program.get(pointer + 3);
                    program.set(destination, firstValue + secondValue);
                    pointerShiftAmount = 4;
                    break;
                case "02":
                    firstValue = parameterModes.get("C") == 0 ? program.get(program.get(pointer + 1)) : program.get(pointer + 1);
                    secondValue = parameterModes.get("B") == 0 ? program.get(program.get(pointer + 2)) : program.get(pointer + 2);
                    destination = program.get(pointer + 3);
                    program.set(destination, firstValue * secondValue);
                    pointerShiftAmount = 4;
                    break;
                case "03":
                    Scanner myObj = new Scanner(System.in);  // Create a Scanner object
                    System.out.println("Enter input");
                    String input = myObj.nextLine();  // Read user input
                    destination = program.get(pointer + 1);
                    program.set(destination, Integer.parseInt(input));
                    pointerShiftAmount = 2;
                    break;
                case "04":
                    firstValue = parameterModes.get("C") == 0 ? program.get(program.get(pointer + 1)) : program.get(pointer + 1);
                    System.out.println(firstValue);
                    pointerShiftAmount = 2;
                    break;
                case "99" :
                    complete = true;
                    break;
                default:
                    complete = true;
                    error = true;
                    break;
            }

            pointer += pointerShiftAmount;
        }


        if (error) {
            System.out.println("Error!");
        }

        System.out.println("Done!");
    }
}