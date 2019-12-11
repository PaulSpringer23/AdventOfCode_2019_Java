public class Part2 {

    public static void main(String[] args) {
        String input = "248345-746315";
        String[] splitInput = input.split("-");

        int passwordCount = 0;

        int min = Integer.parseInt(splitInput[0]);
        int max = Integer.parseInt(splitInput[1]);

        for (int i = min; i <= max; i++) {
            String valueAsString = String.valueOf(i);
            if (!containsTrueDouble(valueAsString)) {
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

    private static boolean containsTrueDouble(String input) {
        char[] inputArray = input.toCharArray();
        for (int i = 0; i <= inputArray.length - 2; i++) {
            if (inputArray[i] == inputArray[i + 1]) {
                if (i == inputArray.length - 2) {
                    return true;
                }

                int j = i + 2;

                while (j < inputArray.length) {
                    if (inputArray[j] != inputArray[i]) {
                        if (j - i == 2) {
                            return true;
                        }
                        i = j - 1;
                        break;
                    }
                    j++;
                    if (j > inputArray.length - 1) {
                        return false;
                    }
                }
            }
        }
        return false;
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
