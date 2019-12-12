import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;

class Computer {

    private List<Integer> program;
    private int pointer = 0;

    Computer(List<Integer> program) {
        this.program = new ArrayList<>(program);
    }

    ArrayDeque<String> run(ArrayDeque<String> input) {
        ArrayDeque<String> output = new ArrayDeque<>();
        String opCode;

        while (true) {
            opCode = String.format("%05d", program.get(pointer));

            // 0: Position Mode
            // 1: Immediate Mode
            HashMap<String, Integer> parameterModes = new HashMap<>();

            parameterModes.put("A", Integer.parseInt(opCode.substring(0, 1)));
            parameterModes.put("B", Integer.parseInt(opCode.substring(1, 2)));
            parameterModes.put("C", Integer.parseInt(opCode.substring(2, 3)));

            opCode = opCode.substring(3);

            switch (opCode) {
                case "01":
                    int firstValue = parameterModes.get("C") == 0 ? program.get(program.get(pointer + 1)) : program.get(pointer + 1);
                    int secondValue = parameterModes.get("B") == 0 ? program.get(program.get(pointer + 2)) : program.get(pointer + 2);
                    int destinationPosition = program.get(pointer + 3);
                    program.set(destinationPosition, firstValue + secondValue);
                    pointer += 4;
                    break;
                case "02":
                    firstValue = parameterModes.get("C") == 0 ? program.get(program.get(pointer + 1)) : program.get(pointer + 1);
                    secondValue = parameterModes.get("B") == 0 ? program.get(program.get(pointer + 2)) : program.get(pointer + 2);
                    destinationPosition = program.get(pointer + 3);
                    program.set(destinationPosition, firstValue * secondValue);
                    pointer += 4;
                    break;
                case "03":
                    destinationPosition = program.get(pointer + 1);
                    int valueToWrite = Integer.parseInt(input.pop());
                    program.set(destinationPosition, valueToWrite);
                    pointer += 2;
                    break;
                case "04":
                    firstValue = parameterModes.get("C") == 0 ? program.get(program.get(pointer + 1)) : program.get(pointer + 1);
                    output.add(String.valueOf(firstValue));
                    pointer += 2;
                    break;
                case "05":
                    firstValue = parameterModes.get("C") == 0 ? program.get(program.get(pointer + 1)) : program.get(pointer + 1);
                    secondValue = parameterModes.get("B") == 0 ? program.get(program.get(pointer + 2)) : program.get(pointer + 2);
                    pointer = firstValue != 0 ? secondValue : pointer + 3;
                    break;
                case "06":
                    firstValue = parameterModes.get("C") == 0 ? program.get(program.get(pointer + 1)) : program.get(pointer + 1);
                    secondValue = parameterModes.get("B") == 0 ? program.get(program.get(pointer + 2)) : program.get(pointer + 2);
                    pointer = firstValue == 0 ? secondValue : pointer + 3;
                    break;
                case "07":
                    firstValue = parameterModes.get("C") == 0 ? program.get(program.get(pointer + 1)) : program.get(pointer + 1);
                    secondValue = parameterModes.get("B") == 0 ? program.get(program.get(pointer + 2)) : program.get(pointer + 2);
                    destinationPosition = program.get(pointer + 3);
                    int testResult = firstValue < secondValue ? 1 : 0;
                    program.set(destinationPosition, testResult);
                    pointer += 4;
                    break;
                case "08":
                    firstValue = parameterModes.get("C") == 0 ? program.get(program.get(pointer + 1)) : program.get(pointer + 1);
                    secondValue = parameterModes.get("B") == 0 ? program.get(program.get(pointer + 2)) : program.get(pointer + 2);
                    destinationPosition = program.get(pointer + 3);
                    testResult = firstValue == secondValue ? 1 : 0;
                    program.set(destinationPosition, testResult);
                    pointer += 4;
                    break;
                case "99":
                    output.add("Halted");
                    return output;
                default:
                    output.add("Error!");
                    return output;
            }
        }
    }
}
