import java.util.Collections;

public class Part1 {

    public static void main(String[] args) {
        String input = "248345-746315";
        String[] splitInput = input.split("-");

        int passwordCount = 0;

        int min = Integer.parseInt(splitInput[0]);
        int max = Integer.parseInt(splitInput[1]);

        for (int i = min; i <= max; i++) {
            String valueAsString = String.valueOf(i);
            if (!containsDouble(valueAsString)) {
                continue;
            }

            if (!continuallyIncreases(valueAsString)) {
                continue;
            }

            passwordCount++;

        }

        System.out.println(passwordCount);
        System.out.println("Done!");

    }

    static boolean containsDouble(String input) {
        boolean containsDouble = false;
        char[] inputArray = input.toCharArray();
        for (int i = 0; i < input.length() - 1; i++) {
            if (inputArray[i] == inputArray[i+1]) {
                containsDouble = true;
                break;
            }
        }
        return containsDouble;

    }

    static boolean continuallyIncreases(String input) {
        boolean continuallyIncreases = true;
        char[] inputArray = input.toCharArray();
        for (int i = 0; i < input.length() - 1; i++) {
            if (inputArray[i] > inputArray[i+1]) {
                continuallyIncreases = false;
                break;
            }
        }
        return continuallyIncreases;
    }
}
