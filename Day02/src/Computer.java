import java.util.ArrayList;
import java.util.List;

class Computer {

    private List<Integer> program;
    private int pointer = 0;

    Computer(List<Integer> program) {
        this.program = new ArrayList<>(program);
    }

    String run() {
        int opCode;

        while (true) {
            opCode = program.get(pointer);

            switch (opCode) {
                case 1:
                    int firstValue = program.get(program.get(pointer + 1));
                    int secondValue = program.get(program.get(pointer + 2));
                    int destinationPosition = program.get(pointer + 3);
                    program.set(destinationPosition, firstValue + secondValue);
                    pointer += 4;
                    break;
                case 2:
                    firstValue = program.get(program.get(pointer + 1));
                    secondValue = program.get(program.get(pointer + 2));
                    destinationPosition = program.get(pointer + 3);
                    program.set(destinationPosition, firstValue * secondValue);
                    pointer += 4;
                    break;
                case 99:
                    return String.valueOf(program.get(0));
                default:
                    return "OpCode Error!";
            }
        }
    }
}
