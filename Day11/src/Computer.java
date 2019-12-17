import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;

class Computer {

    private List<Long> program;
    private int pointer = 0;
    private int relativeBase = 0;
    State currentState = State.OK;
    ArrayDeque<String> input = new ArrayDeque<>();

    Computer(List<Long> program) {
        this.program = new ArrayList<>(program);
    }

    ArrayDeque<String> run() {
        ArrayDeque<String> output = new ArrayDeque<>();
        String opCode;

        while (true) {
            opCode = String.format("%05d", program.get(pointer));

            HashMap<String, ParameterMode> parameterModes = new HashMap<>();

            switch (opCode.substring(0, 1)) {
                case "0":
                    parameterModes.put("A", ParameterMode.POSITION);
                    break;
                case "1":
                    parameterModes.put("A", ParameterMode.IMMEDIATE);
                    break;
                case "2":
                    parameterModes.put("A", ParameterMode.RELATIVE);
                    break;
            }

            switch (opCode.substring(1, 2)) {
                case "0":
                    parameterModes.put("B", ParameterMode.POSITION);
                    break;
                case "1":
                    parameterModes.put("B", ParameterMode.IMMEDIATE);
                    break;
                case "2":
                    parameterModes.put("B", ParameterMode.RELATIVE);
                    break;
            }

            switch (opCode.substring(2, 3)) {
                case "0":
                    parameterModes.put("C", ParameterMode.POSITION);
                    break;
                case "1":
                    parameterModes.put("C", ParameterMode.IMMEDIATE);
                    break;
                case "2":
                    parameterModes.put("C", ParameterMode.RELATIVE);
                    break;
            }

            opCode = opCode.substring(3);

            switch (opCode) {
                case "01":
                    long firstValue;
                    if (parameterModes.get("C") == ParameterMode.POSITION) {
                        int position = Math.toIntExact(program.get(pointer + 1));
                        if (position > program.size() - 1) {
                            expandMemory(position);
                        }
                        firstValue = program.get(position);
                    } else if (parameterModes.get("C") == ParameterMode.RELATIVE) {
                        int position = Math.toIntExact(program.get(pointer + 1) + relativeBase);
                        if (position > program.size() - 1) {
                            expandMemory(position);
                        }
                        firstValue = program.get(position);
                    } else {
                        firstValue = program.get(pointer + 1);
                    }

                    long secondValue;
                    if (parameterModes.get("B") == ParameterMode.POSITION) {
                        int position = Math.toIntExact(program.get(pointer + 2));
                        if (position > program.size() - 1) {
                            expandMemory(position);
                        }
                        secondValue = program.get(position);
                    } else if (parameterModes.get("B") == ParameterMode.RELATIVE) {
                        int position = Math.toIntExact(program.get(pointer + 2) + relativeBase);
                        if (position > program.size() - 1) {
                            expandMemory(position);
                        }
                        secondValue = program.get(position);
                    } else {
                        secondValue = program.get(pointer + 2);
                    }

                    int destinationPosition;
                    if (parameterModes.get("A") == ParameterMode.POSITION) {
                        destinationPosition = Math.toIntExact(program.get(pointer + 3));
                        if (destinationPosition > program.size() - 1) {
                            expandMemory(destinationPosition);
                        }
                    } else {
                        destinationPosition = Math.toIntExact(program.get(pointer + 3) + relativeBase);
                        if (destinationPosition > program.size() - 1) {
                            expandMemory(destinationPosition);
                        }
                    }

                    program.set(destinationPosition, firstValue + secondValue);
                    pointer += 4;
                    break;
                case "02":
                    if (parameterModes.get("C") == ParameterMode.POSITION) {
                        int position = Math.toIntExact(program.get(pointer + 1));
                        if (position > program.size() - 1) {
                            expandMemory(position);
                        }
                        firstValue = program.get(position);
                    } else if (parameterModes.get("C") == ParameterMode.RELATIVE) {
                        int position = Math.toIntExact(program.get(pointer + 1) + relativeBase);
                        if (position > program.size() - 1) {
                            expandMemory(position);
                        }
                        firstValue = program.get(position);
                    } else {
                        firstValue = program.get(pointer + 1);
                    }

                    if (parameterModes.get("B") == ParameterMode.POSITION) {
                        int position = Math.toIntExact(program.get(pointer + 2));
                        if (position > program.size() - 1) {
                            expandMemory(position);
                        }
                        secondValue = program.get(position);
                    } else if (parameterModes.get("B") == ParameterMode.RELATIVE) {
                        int position = Math.toIntExact(program.get(pointer + 2) + relativeBase);
                        if (position > program.size() - 1) {
                            expandMemory(position);
                        }
                        secondValue = program.get(position);
                    } else {
                        secondValue = program.get(pointer + 2);
                    }

                    if (parameterModes.get("A") == ParameterMode.POSITION) {
                        destinationPosition = Math.toIntExact(program.get(pointer + 3));
                        if (destinationPosition > program.size() - 1) {
                            expandMemory(destinationPosition);
                        }
                    } else {
                        destinationPosition = Math.toIntExact(program.get(pointer + 3) + relativeBase);
                        if (destinationPosition > program.size() - 1) {
                            expandMemory(destinationPosition);
                        }
                    }

                    program.set(destinationPosition, firstValue * secondValue);
                    pointer += 4;
                    break;
                case "03":
                    if (parameterModes.get("C") == ParameterMode.POSITION) {
                        destinationPosition = Math.toIntExact(program.get(pointer + 1));
                        if (destinationPosition > program.size() - 1) {
                            expandMemory(destinationPosition);
                        }
                    } else {
                        destinationPosition = Math.toIntExact(program.get(pointer + 1) + relativeBase);
                        if (destinationPosition > program.size() - 1) {
                            expandMemory(destinationPosition);
                        }
                    }

                    if (input.size() == 0) {
                        currentState = State.WAITING;
                        return output;
                    }

                    long valueToWrite = Long.parseLong(input.pop());
                    program.set(destinationPosition, valueToWrite);
                    pointer += 2;
                    break;
                case "04":
                    if (parameterModes.get("C") == ParameterMode.POSITION) {
                        int position = Math.toIntExact(program.get(pointer + 1));
                        if (position > program.size() - 1) {
                            expandMemory(position);
                        }
                        firstValue = program.get(position);
                    } else if (parameterModes.get("C") == ParameterMode.RELATIVE) {
                        int position = Math.toIntExact(program.get(pointer + 1) + relativeBase);
                        if (position > program.size() - 1) {
                            expandMemory(position);
                        }
                        firstValue = program.get(position);
                    } else {
                        firstValue = program.get(pointer + 1);
                    }

                    output.add(String.valueOf(firstValue));
                    pointer += 2;
                    break;
                case "05":
                    if (parameterModes.get("C") == ParameterMode.POSITION) {
                        int position = Math.toIntExact(program.get(pointer + 1));
                        if (position > program.size() - 1) {
                            expandMemory(position);
                        }
                        firstValue = program.get(position);
                    } else if (parameterModes.get("C") == ParameterMode.RELATIVE) {
                        int position = Math.toIntExact(program.get(pointer + 1) + relativeBase);
                        if (position > program.size() - 1) {
                            expandMemory(position);
                        }
                        firstValue = program.get(position);
                    } else {
                        firstValue = program.get(pointer + 1);
                    }

                    if (parameterModes.get("B") == ParameterMode.POSITION) {
                        int position = Math.toIntExact(program.get(pointer + 2));
                        if (position > program.size() - 1) {
                            expandMemory(position);
                        }
                        secondValue = program.get(position);
                    } else if (parameterModes.get("B") == ParameterMode.RELATIVE) {
                        int position = Math.toIntExact(program.get(pointer + 2) + relativeBase);
                        if (position > program.size() - 1) {
                            expandMemory(position);
                        }
                        secondValue = program.get(position);
                    } else {
                        secondValue = program.get(pointer + 2);
                    }

                    pointer = Math.toIntExact(firstValue != 0 ? secondValue : pointer + 3);
                    break;
                case "06":
                    if (parameterModes.get("C") == ParameterMode.POSITION) {
                        int position = Math.toIntExact(program.get(pointer + 1));
                        if (position > program.size() - 1) {
                            expandMemory(position);
                        }
                        firstValue = program.get(position);
                    } else if (parameterModes.get("C") == ParameterMode.RELATIVE) {
                        int position = Math.toIntExact(program.get(pointer + 1) + relativeBase);
                        if (position > program.size() - 1) {
                            expandMemory(position);
                        }
                        firstValue = program.get(position);
                    } else {
                        firstValue = program.get(pointer + 1);
                    }

                    if (parameterModes.get("B") == ParameterMode.POSITION) {
                        int position = Math.toIntExact(program.get(pointer + 2));
                        if (position > program.size() - 1) {
                            expandMemory(position);
                        }
                        secondValue = program.get(position);
                    } else if (parameterModes.get("B") == ParameterMode.RELATIVE) {
                        int position = Math.toIntExact(program.get(pointer + 2) + relativeBase);
                        if (position > program.size() - 1) {
                            expandMemory(position);
                        }
                        secondValue = program.get(position);
                    } else {
                        secondValue = program.get(pointer + 2);
                    }

                    pointer = Math.toIntExact(firstValue == 0 ? secondValue : pointer + 3);
                    break;
                case "07":
                    if (parameterModes.get("C") == ParameterMode.POSITION) {
                        int position = Math.toIntExact(program.get(pointer + 1));
                        if (position > program.size() - 1) {
                            expandMemory(position);
                        }
                        firstValue = program.get(position);
                    } else if (parameterModes.get("C") == ParameterMode.RELATIVE) {
                        int position = Math.toIntExact(program.get(pointer + 1) + relativeBase);
                        if (position > program.size() - 1) {
                            expandMemory(position);
                        }
                        firstValue = program.get(position);
                    } else {
                        firstValue = program.get(pointer + 1);
                    }

                    if (parameterModes.get("B") == ParameterMode.POSITION) {
                        int position = Math.toIntExact(program.get(pointer + 2));
                        if (position > program.size() - 1) {
                            expandMemory(position);
                        }
                        secondValue = program.get(position);
                    } else if (parameterModes.get("B") == ParameterMode.RELATIVE) {
                        int position = Math.toIntExact(program.get(pointer + 2) + relativeBase);
                        if (position > program.size() - 1) {
                            expandMemory(position);
                        }
                        secondValue = program.get(position);
                    } else {
                        secondValue = program.get(pointer + 2);
                    }

                    if (parameterModes.get("A") == ParameterMode.POSITION) {
                        destinationPosition = Math.toIntExact(program.get(pointer + 3));
                        if (destinationPosition > program.size() - 1) {
                            expandMemory(destinationPosition);
                        }
                    } else {
                        destinationPosition = Math.toIntExact(program.get(pointer + 3) + relativeBase);
                        if (destinationPosition > program.size() - 1) {
                            expandMemory(destinationPosition);
                        }
                    }

                    long testResult = firstValue < secondValue ? 1 : 0;
                    program.set(destinationPosition, testResult);
                    pointer += 4;
                    break;
                case "08":
                    if (parameterModes.get("C") == ParameterMode.POSITION) {
                        int position = Math.toIntExact(program.get(pointer + 1));
                        if (position > program.size() - 1) {
                            expandMemory(position);
                        }
                        firstValue = program.get(position);
                    } else if (parameterModes.get("C") == ParameterMode.RELATIVE) {
                        int position = Math.toIntExact(program.get(pointer + 1) + relativeBase);
                        if (position > program.size() - 1) {
                            expandMemory(position);
                        }
                        firstValue = program.get(position);
                    } else {
                        firstValue = program.get(pointer + 1);
                    }

                    if (parameterModes.get("B") == ParameterMode.POSITION) {
                        int position = Math.toIntExact(program.get(pointer + 2));
                        if (position > program.size() - 1) {
                            expandMemory(position);
                        }
                        secondValue = program.get(position);
                    } else if (parameterModes.get("B") == ParameterMode.RELATIVE) {
                        int position = Math.toIntExact(program.get(pointer + 2) + relativeBase);
                        if (position > program.size() - 1) {
                            expandMemory(position);
                        }
                        secondValue = program.get(position);
                    } else {
                        secondValue = program.get(pointer + 2);
                    }

                    if (parameterModes.get("A") == ParameterMode.POSITION) {
                        destinationPosition = Math.toIntExact(program.get(pointer + 3));
                        if (destinationPosition > program.size() - 1) {
                            expandMemory(destinationPosition);
                        }

                    } else {
                        destinationPosition = Math.toIntExact(program.get(pointer + 3) + relativeBase);
                        if (destinationPosition > program.size() - 1) {
                            expandMemory(destinationPosition);
                        }
                    }

                    testResult = firstValue == secondValue ? 1 : 0;
                    program.set(destinationPosition, testResult);
                    pointer += 4;
                    break;
                case "09":
                    if (parameterModes.get("C") == ParameterMode.POSITION) {
                        int position = Math.toIntExact(program.get(pointer + 1));
                        if (position > program.size() - 1) {
                            expandMemory(position);
                        }
                        firstValue = program.get(position);
                    } else if (parameterModes.get("C") == ParameterMode.RELATIVE) {
                        int position = Math.toIntExact(program.get(pointer + 1) + relativeBase);
                        if (position > program.size() - 1) {
                            expandMemory(position);
                        }
                        firstValue = program.get(position);
                    } else {
                        firstValue = program.get(pointer + 1);
                    }

                    relativeBase += firstValue;
                    pointer += 2;
                    break;
                case "99":
                    currentState = State.HALTED;
                    return output;
                default:
                    currentState = State.ERROR;
                    return output;
            }
        }
    }

    private void expandMemory(int position) {
        for (int i = program.size(); i <= position; i++) {
            program.add(0L);
        }
    }
}

enum State {
    OK,
    WAITING,
    HALTED,
    ERROR
}

enum ParameterMode {
    POSITION,
    IMMEDIATE,
    RELATIVE
}